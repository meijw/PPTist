# 全局配置规则

## 语言设置

**始终使用中文回复**

所有 AI 助手在与用户交互时，必须使用中文进行所有回复和沟通。

### 具体要求

- **代码注释**: 使用中文编写
- **变量命名**: 如果有意义，优先使用英文命名，但注释必须用中文
- **提交信息**: 使用中文
- **文档说明**: 使用中文
- **错误信息**: 使用中文（除非技术术语要求英文）
- **用户交互**: 所有面向用户的文本必须使用中文

### 例外情况

- 技术术语（如 Spring, Java, Maven 等）保持原文
- API 方法名、类名、变量名等代码标识符使用英文
- 第三方库文档和示例代码保持原文语言

## 项目说明

这是一个 Spring Boot + OpenAI 流式集成的后端项目，主要功能是提供 OpenAI API 的流式响应接口和 AI PPT 生成服务。

### 技术栈

- Java 17+
- Spring Boot 3.3.5
- Spring WebFlux（响应式编程）
- Spring AI（OpenAI 集成）
- Maven

### 主要组件

- `OpenAiStreamingApplication`: Spring Boot 应用入口
- `PPTistController`: REST 控制器，处理流式响应端点和 AI PPT 生成
- `AipptOutlineRequest`: AI PPT 生成请求的数据模型

### 配置

需要在 `src/main/resources/application.properties` 中配置 OpenAI API 密钥。

## 构建与测试

### Maven 命令

```bash
# 编译项目
mvn compile

# 打包项目
mvn package

# 跳过测试打包
mvn package -DskipTests

# 运行应用
./mvnw spring-boot:run
# 或
mvn spring-boot:run

# 运行所有测试
mvn test

# 运行单个测试类
mvn test -Dtest=TestClassName

# 运行单个测试方法
mvn test -Dtest=TestClassName#methodName

# 清理构建产物
mvn clean
```

### 项目结构

```
src/
├── main/
│   ├── java/com/speedstudio/pptist/
│   │   ├── OpenAiStreamingApplication.java  # 应用入口
│   │   ├── bean/                             # 数据模型
│   │   └── controller/                      # 控制器（如新增）
│   └── resources/
│       └── application.properties           # 配置文件
```

## 代码风格指南

### 导入顺序

1. JDK 标准库（java.*）
2. 第三方库（org.springframework, reactor, com.fasterxml 等）
3. 项目内部包

**示例**:
```java
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.RestController;

import com.speedstudio.pptist.bean.AipptOutlineRequest;
```

### 命名约定

- **类名**: PascalCase，描述性强（如 `PPTistController`, `AipptOutlineRequest`）
- **方法名**: camelCase，动词开头（如 `generateStream`, `processString`）
- **变量名**: camelCase，语义清晰（如 `chatModel`, `systemMessage`）
- **常量名**: UPPER_SNAKE_CASE（如有）
- **包名**: 全小写，使用点分隔（如 `com.speedstudio.pptist`）

### 类定义

- 使用 `@RestController` 标注 REST 控制器
- 使用 `@RequestMapping` 定义基础路径
- 如需跨域支持，添加 `@CrossOrigin(origins = "*")`
- 使用 `@SpringBootApplication` 标注应用入口类

**示例**:
```java
@RestController
@RequestMapping("/tools")
@CrossOrigin(origins = "*")
class PPTistController {
    // 实现
}
```

### 数据模型

- 使用 `@JsonProperty` 指定 JSON 字段映射
- 使用 `@JsonInclude(JsonInclude.Include.NON_NULL)` 忽略 null 值
- 为必需字段设置 `required = true`
- 生成标准的 getter/setter 方法

**示例**:
```java
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AipptOutlineRequest {
    @JsonProperty(required = true, value = "content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
```

### 响应式编程

- 返回类型使用 `Flux<T>` 表示响应式流
- 使用 `MediaType.TEXT_EVENT_STREAM_VALUE` 标注流式响应端点
- 使用 `.map()` 进行数据转换
- Spring AI 集成使用 `ChatResponse`, `Prompt`, `Message` 等类型

**示例**:
```java
@GetMapping(value = "/generateStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<ChatResponse> generateStream(
        @RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
    return chatModel.stream(new Prompt(new UserMessage(message)));
}
```

### 字符串处理

- 使用 Text Blocks (`"""..."""`) 定义多行字符串（特别是提示词）
- 使用 `StringBuilder` 进行字符串拼接
- 避免在循环中进行字符串拼接

**示例**:
```java
String systemText = """
    # Role: PPT大纲生成专家

    ## Profile
    - language: 中文
    - description: 专业为用户生成结构清晰、逻辑严谨的PPT大纲
    """;
```

### 错误处理

- 响应式编程中使用 `.onErrorResume()` 处理异常
- 返回有意义的错误消息
- 如需要，使用特定异常类型

### 调试

- 使用 `System.out.println()` 进行临时调试（项目当前风格）
- 生产代码中建议使用日志框架（如 SLF4J + Logback）

### 注释

- 类级别注释说明类的用途
- 复杂方法添加注释说明逻辑
- 中文注释，保持简洁明了
- 避免注释显而易见的代码

### AI 集成模式

- 使用 `SystemMessage` 定义系统提示词
- 使用 `UserMessage` 定义用户输入
- 使用 `Prompt(List<Message>)` 组合多条消息
- 流式响应使用 `.stream()` 方法
- 处理流输出时注意 JSON 解析和内容过滤

### 文件编码

- 所有源文件使用 UTF-8 编码
- 确保中文字符正确显示

### 依赖注入

- 使用构造函数注入（推荐）
- 使用 `@Autowired` 标注构造函数

**示例**:
```java
private final OpenAiChatModel chatModel;

@Autowired
public PPTistController(OpenAiChatModel chatModel) {
    this.chatModel = chatModel;
}
```

## 注意事项

1. 本项目使用 Spring Boot 3.x，注意 Java 版本要求（Java 17+）
2. Spring AI 目前使用 Milestone 仓库，确保正确配置
3. 响应式编程（WebFlux）与传统 Web 编程不同，注意异步处理
4. 流式响应需要正确处理 JSON 数据的分片
5. 跨域配置已在控制器级别添加，注意生产环境安全性
