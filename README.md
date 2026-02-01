<p align="center">
    <img src='pptist-frontend/public/logo.png' width="120" />
</p>


# 🎨 PPTist

> PowerPoint-ist（/'pauəpɔintist/），一个基于 Web 的演示文稿（幻灯片）应用程序。本应用复刻了 Microsoft Office PowerPoint 的大部分常用功能。支持文本、图片、形状、线条、图表、表格、视频、音频、公式等多种基础元素类型。您可以直接在 Web 浏览器中编辑和演示幻灯片，并支持 AI 生成 PPT。

## ✨ 项目亮点

1. **易于开发**：使用 Vue 3.x 和 TypeScript 构建，不依赖 UI 组件库，尽量避免使用第三方组件。这使得样式自定义更容易，功能扩展更方便。
2. **用户友好**：提供随处可用的右键菜单、数十个快捷键和无数个编辑细节优化，力求复现桌面应用级别的体验。
3. **功能丰富**：支持 PowerPoint 中大部分常用的元素和功能，支持 AI 生成 PPT，支持多种格式导出，并提供移动端基础编辑和预览功能。
4. **AI 集成**：内置 AI 写作助手和 AI PPT 生成功能，支持流式响应，让创作更高效。

## 🚀 快速开始

### 前置要求

- **前端**：
  - Node.js 18+
  - npm 或 yarn 或 pnpm

- **后端**：
  - Java 17+
  - Maven 3.6+
  - OpenAI API 密钥或兼容的 API 密钥

### 安装步骤

1. **克隆项目**

   ```bash
   git clone https://github.com/pipipi-pikachu/PPTist.git
   cd PPTist
   ```

2. **启动后端服务**

   ```bash
   cd pptist-backend

   # 配置 API 密钥（编辑 src/main/resources/application.properties）
   # spring.ai.openai.base-url=<你的API地址>
   # spring.ai.openai.api-key=<你的API密钥>
   # spring.ai.openai.chat.options.model=<模型名称>

   # 运行后端
   mvn spring-boot:run
   ```

   后端服务将运行在：`http://localhost:9999`

3. **启动前端服务**

   ```bash
   cd pptist-frontend

   # 安装依赖
   npm install

   # 运行前端
   npm run dev
   ```

   前端服务将运行在：`http://127.0.0.1:5173`

## 📚 技术栈

### 前端
| 技术 | 版本 | 说明 |
|------|------|------|
| Vue | 3.5+ | 渐进式 JavaScript 框架 |
| TypeScript | 5.3+ | JavaScript 的超集 |
| Vite | 5.3+ | 下一代前端构建工具 |
| Pinia | 3.0+ | Vue 状态管理库 |
| ProseMirror | 1.x | 富文本编辑器框架 |
| PPTXGenJS | 3.12+ | PPTX 文件生成库 |
| ECharts | 6.0+ | 可视化图表库 |

### 后端
| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 17+ | 开发语言 |
| Spring Boot | 3.3.5 | 应用框架 |
| Spring WebFlux | - | 响应式编程 |
| Spring AI | 1.0.0-M5 | OpenAI 集成 |
| Maven | - | 项目构建 |

## 🎯 功能特性

### 基础功能
- 历史记录（撤销、重做）
- 快捷键
- 右键菜单
- 导出本地文件（PPTX、JSON、图片、PDF）
- 导入/导出 pptist 文件
- 打印
- AI PPT 生成

### 幻灯片编辑
- 添加/删除页面
- 复制/粘贴页面
- 调整页面顺序
- 创建分区
- 背景设置（纯色、渐变、图片）
- 设置画布大小
- 网格线、标尺
- 画布缩放和移动
- 主题设置
- 提取幻灯片样式
- 演讲者备注（富文本）
- 幻灯片模板
- 切换动画
- 元素动画（进入、退出、强调）
- 选择面板（隐藏元素、图层排序、元素命名）
- 查找/替换
- 批注

### 元素编辑
- 文本：富文本编辑（颜色、高亮、字体、字号、加粗、斜体、下划线、删除线、下标、行内代码、引用、超链接、对齐、编号、项目符号、段落缩进、清除格式）
- 图片：裁剪、圆角、滤镜、着色、翻转、边框、阴影、替换图片、重置图片、设为背景
- 形状：绘制任意多边形、任意线条、替换形状、填充（纯色、渐变、图片）、边框、阴影、透明度、翻转、形状格式刷、编辑文本
- 线条：直线、折线、曲线；颜色、宽度、样式（实线、虚线、点线）、端点样式
- 图表：柱状图、折线图、饼图、雷达图等；数据编辑、主题颜色、坐标轴、网格颜色等
- 表格：添加/删除行列、主题设置、合并单元格、单元格样式、边框
- 视频：预览封面设置、自动播放
- 音频：图标颜色、自动播放、循环播放
- 公式：LaTeX 编辑、颜色设置、公式线粗设置

### AI 功能
- **AI PPT 生成**：根据输入内容自动生成 PPT 幻灯片
- **AI 写作助手**：
  - 文本美化：优化表达，提升可读性和专业度
  - 文本扩写：丰富内容，增加细节和深度
  - 文本精简：压缩内容，提炼核心信息

### 演示播放
- 画笔工具（笔/形状/箭头/高亮批注、橡皮擦、黑板模式）
- 预览所有幻灯片
- 底部缩略图导航
- 计时器工具
- 激光笔
- 自动播放
- 演讲者视图

### 移动端
- 基础编辑：添加/删除/复制/备注/撤销重做页面、插入文本/图片/矩形/圆形、元素操作（移动、缩放、旋转、复制、删除、图层调整、对齐）、元素样式设置
- 基础预览和播放预览

## 📂 项目结构

```
PPTist/
├── pptist-frontend/          # 前端项目
│   ├── src/
│   │   ├── assets/          # 静态资源
│   │   ├── components/      # 公共组件
│   │   ├── hooks/           # 自定义 Hooks
│   │   ├── main/            # 主逻辑
│   │   ├── state/           # 状态管理
│   │   ├── utils/           # 工具函数
│   │   └── types/           # TypeScript 类型定义
│   ├── public/              # 公共资源
│   ├── package.json
│   └── vite.config.ts
├── pptist-backend/          # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/speedstudio/pptist/
│   │   │   │   ├── bean/               # 数据模型
│   │   │   │   ├── controller/        # REST API 控制器
│   │   │   │   └── OpenAiStreamingApplication.java
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── prompts/           # AI 提示词
│   │   └── test/
│   └── pom.xml
├── .github/                 # GitHub 配置
│   ├── workflows/           # GitHub Actions
│   └── ISSUE_TEMPLATE/      # Issue 模板
├── doc/                     # 项目文档
│   ├── Q&A.md              # 常见问题
│   ├── DirectoryAndData.md # 目录结构和数据
│   ├── Canvas.md           # 画布和元素基础
│   ├── CustomElement.md    # 自定义元素
│   └── AIPPT.md            # AI PPT 说明
└── README.md               # 项目说明（本文件）
```

## 🌐 在线体验

👉 **在线体验**：[https://pipipi-pikachu.github.io/PPTist/](https://pipipi-pikachu.github.io/PPTist/)


## 🔧 开发指南

### 前端开发

```bash
cd pptist-frontend

# 安装依赖
npm install

# 开发模式
npm run dev

# 构建生产版本
npm run build

# 类型检查
npm run type-check

# 代码检查
npm run lint
```

### 后端开发

```bash
cd pptist-backend

# 编译项目
mvn compile

# 运行应用
mvn spring-boot:run

# 打包项目
mvn package

# 运行测试
mvn test
```

更多开发细节请参考：
- 前端开发指南：[pptist-frontend/README.md](pptist-frontend/README.md)
- 后端开发指南：[pptist-backend/README.md](pptist-backend/README.md)

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

提交 Issue 或 PR 前，请先阅读 [常见问题](doc/Q&A.md)。

## 📄 许可证

[AGPL-3.0 License](LICENSE) | Copyright © 2020-PRESENT [pipipi-pikachu](https://github.com/pipipi-pikachu)

### 商业用途

本项目禁止闭源商用。如果您希望将 PPTist 用于商业项目盈利，请尊重开源，严格遵循 [AGPL-3.0 协议](https://www.gnu.org/licenses/agpl-3.0.html)，回馈开源社区。

如果您需要独立的商业授权，请[邮件联系作者](mailto:pipipi_pikachu@163.com)。

## 🔗 相关链接

- [原始项目 (pipipi-pikachu/PPTist)](https://github.com/pipipi-pikachu/PPTist)
- [Gitee 镜像](https://gitee.com/pptist/PPTist)
- [pptxtojson - PPTX 导入参考](https://github.com/pipipi-pikachu/pptxtojson)
- [svgPathCreator - 形状绘制工具](https://github.com/pipipi-pikachu/svgPathCreator)

---

## 👀 重要提示

1. 本项目是一个"Web 演示文稿应用程序"，而非"低代码平台"、"H5 编辑器"、"图片编辑器"、"白板应用"等类似工具。
2. 本项目的目标受众是**有 Web 演示文稿开发需求的开发者**，需要具备基础的 Web 开发经验。提供的链接仅为演示地址，不提供任何在线服务。您不应直接将本项目作为工具使用，它也不支持开箱即用。
3. 如果您只是需要一个演示工具或服务，可以选择更优秀和成熟的产品，如：[Slidev](https://sli.dev/)、[revealjs](https://revealjs.com/) 等。

## 📧 联系方式

如有问题，请在 [Issues](https://github.com/pipipi-pikachu/PPTist/issues) 中提出。
