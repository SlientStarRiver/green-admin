# tech-stack.md

> 本文件由项目启动架构师生成，记录项目技术选型及配置基准。

## 核心技术栈

| 层级 | 技术 | 版本 | 用途 | 选用理由 |
|------|------|------|------|----------|
| 后端框架 | Spring Boot | 3.0.5 | REST API 服务 | Java 生态主流，生态完善 |
| ORM | MyBatis-Plus | 3.5.3 | 数据库操作 | 简化 CRUD，支持分页插件 |
| 连接池 | Druid | 1.2.18 | 数据库连接管理 | 阿里开源，监控能力强 |
| 认证 | JWT (jjwt) | 0.9.1 | 无状态认证 | 前后端分离标准方案 |
| 数据库 | MySQL | 9.7 | 数据持久化 | 成熟稳定，社区活跃 |
| 前端框架 | Vue | 2.6.14 | SPA 前端 | 轻量、学习曲线低 |
| UI组件库 | Element UI | 2.15.14 | 后台管理界面 | 组件丰富，后台场景适配 |
| HTTP客户端 | Axios | 0.27.2 | API 请求 | 拦截器机制完善 |

## 开发环境要求
- JDK：17（Eclipse Temurin）
- Maven：3.9.x
- Node.js：>= 18.x
- MySQL：9.7
- npm：>= 9.x

## 关键依赖说明
- **Druid**：数据库连接池 + SQL 监控面板
- **MyMetaObjectHandler**：自动填充 `createdTime`、`updatedTime`
- **CorsConfig**：跨域配置，开发环境前端 8081 → 后端 8080
- **Testcontainers**：集成测试使用 Docker MySQL 容器

## 环境变量说明
```yaml
# application.yml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/gms
    username: root
    password: root

jwt:
  token:
    tokenExpiration: 120  # 分钟
    tokenSignKey: gms123456
```

## 初始化命令
```bash
# 后端
cd GMS
mvn spring-boot:run

# 前端
cd green-admin
npm install
npm run serve
```

## 后端服务说明
- 端口：8080
- 认证方式：JWT Token（登录获取，Header 携带）
- 数据库：gms（MySQL 9.7）
- 初始管理员：需通过注册接口创建，role 设为 ADMIN
