# PPTist Backend

基于 Spring Boot + OpenAI 流式集成的后端项目，提供 AI PPT 生成和 AI 写作服务。

## 功能特性

### AI PPT 生成
- ✅ **PPT 生成** (`/tools/aippt`) - 生成 JSON 格式的 PPT 幻灯片
- ✅ **PPT 大纲生成** (`/tools/aippt_outline`) - 生成 Markdown 格式的 PPT 大纲

### AI 写作助手
- ✅ **文本美化** - 优化表达，提升可读性和专业度
- ✅ **文本扩写** - 丰富内容，增加细节和深度
- ✅ **文本精简** - 压缩内容，提炼核心信息

### 流式响应
- 所有端点均支持流式输出，实时返回 AI 生成内容
- 自动过滤 AI 思考标签，仅返回有效输出

## 技术栈

| 组件 | 版本 | 说明 |
|-------|------|------|
| Java | 17+ | 开发语言 |
| Spring Boot | 3.3.5 | 应用框架 |
| Spring WebFlux | - | 响应式编程 |
| Spring AI | 1.0.0-M5 | OpenAI 集成 |
| Maven | - | 项目构建 |

## 快速开始

### 前置要求

- Java 17 或更高版本
- Maven 3.6+
- OpenAI API 密钥或兼容的 API 密钥（如 DeepSeek、Qwen 等）

### 安装步骤

1. **克隆项目**
   ```bash
   git clone <repository-url>
   cd pptist-backend
   ```

2. **配置 API 密钥**
   
   编辑 `src/main/resources/application.properties`：
   ```properties
   spring.ai.openai.base-url=<你的API地址>
   spring.ai.openai.api-key=<你的API密钥>
   spring.ai.openai.chat.options.model=<模型名称>
   ```
   
   支持的配置示例：
   ```properties
   spring.ai.openai.base-url=https://api-inference.modelscope.cn
   spring.ai.openai.api-key=xxxxx
   spring.ai.openai.chat.options.model=Qwen/Qwen2.5-72B-Instruct
   ```

3. **编译并运行**
   ```bash
   # 编译项目
   mvn clean install
   
   # 运行应用
   mvn spring-boot:run
   ```
   
   或使用 Maven Wrapper：
   ```bash
   ./mvnw spring-boot:run
   ```

4. **验证服务**
   
   访问：`http://localhost:9999/tools/generateStream`

## API 文档

### 1. AI PPT 生成

**端点**：`POST /tools/aippt`

**请求体**：
```json
{
  "content": "项目技术方案介绍",
  "language": "中文",
  "model": "Qwen/Qwen2.5-72B-Instruct",
  "stream": true
}
```

**响应格式**：流式返回独立的 JSON 对象

### 2. AI 写作助手

**端点**：`POST /tools/ai_writing`

**请求体**：
```json
{
  "content": "这是一段需要处理的文本",
  "command": "美化",
  "model": "ark-doubao-seed-1.6-flash",
  "stream": true
}
```

**支持的命令**：
- `美化` - 优化表达，提升可读性和专业度
- `扩写` - 丰富内容，增加细节和深度
- `精简` - 压缩内容，提炼核心信息

**示例请求**：
```bash
curl -X POST http://localhost:9999/tools/ai_writing \
  -H "Content-Type: application/json" \
  -d '{
    "content": "这个项目很重要",
    "command": "美化",
    "model": "deepseek-chat",
    "stream": true
  }'
```

## 项目结构

```
pptist-backend/
├── src/
│   ├── main/
│   │   ├── java/com/speedstudio/pptist/
│   │   │   ├── OpenAiStreamingApplication.java  # 应用入口
│   │   │   ├── bean/                           # 数据模型
│   │   │   │   ├── AipptOutlineRequest.java  # PPT 生成请求
│   │   │   │   └── AIWritingRequest.java      # AI 写作请求
│   │   │   └── controller/                    # 控制器
│   │   │       └── PPTistController.java     # REST API
│   │   └── resources/
│   │       ├── application.properties              # 应用配置
│   │       └── prompts/                       # AI 提示词
│   │           ├── aippt-prompt.txt            # PPT 生成提示词
│   │           ├── aippt-outline-prompt.txt   # PPT 大纲提示词
│   │           └── ai-writing-prompt.txt       # AI 写作提示词
│   └── test/                                  # 测试代码
├── pom.xml                                      # Maven 配置
├── AGENTS.md                                    # AI 开发指南
└── README.md                                    # 项目说明
```

## 开发指南

### 构建命令

```bash
# 清理构建产物
mvn clean

# 编译项目
mvn compile

# 打包项目
mvn package

# 跳过测试打包
mvn package -DskipTests

# 运行应用
mvn spring-boot:run

# 运行所有测试
mvn test

# 运行单个测试类
mvn test -Dtest=TestClassName
```

### 代码风格

项目遵循 Spring Boot 最佳实践和 Java 编码规范：

- 使用构造函数注入
- 响应式编程（WebFlux）
- 资源文件管理提示词
- 预编译正则表达式优化性能

详细规范请参考 [AGENTS.md](./AGENTS.md)。

### 添加新的 Prompt

1. 在 `src/main/resources/prompts/` 下创建新的提示词文件
2. 在 `PPTistController` 中注入资源：
   ```java
   @Value("classpath:prompts/your-prompt.txt")
   private Resource yourPromptResource;
   ```
3. 使用 `readResourceAsString()` 方法读取内容

### 添加新的端点

1. 创建对应的数据模型（如 `YourRequest.java`）
2. 在 `PPTistController` 中添加方法：
   ```java
   @PostMapping(value = "/your_endpoint")
   public Flux<String> yourMethod(@RequestBody YourRequest request) {
       // 实现逻辑
   }
   ```

## 配置说明

### 应用配置

`src/main/resources/application.properties`：

```properties
# 应用端口
server.port=9999

# OpenAI 配置
spring.ai.openai.base-url=<API地址>
spring.ai.openai.api-key=<API密钥>
spring.ai.openai.chat.options.model=<模型名称>
```

### 支持的 AI 模型

- ✅ OpenAI (GPT-4, GPT-3.5)
- ✅ DeepSeek (deepseek-chat)
- ✅ 通义千问 (Qwen/Qwen2.5-72B-Instruct)
- ✅ 其他兼容 OpenAI API 的模型

## 故障排查

### 端口被占用

修改 `application.properties` 中的端口号：
```properties
server.port=9999
```

### API 连接失败

检查以下配置：
- API 地址是否正确
- API 密钥是否有效
- 网络连接是否正常
- 模型名称是否正确

### 流式响应中断

确保客户端支持流式响应（SSE）：
- 使用 `EventSource` 或类似的客户端库
- 处理流式数据片段

## 许可证

本项目仅供学习和研究使用。

## 贡献

欢迎提交 Issue 和 Pull Request。

## 联系方式

如有问题，请提交 Issue。
