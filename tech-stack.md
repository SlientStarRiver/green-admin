# tech-stack.md

> 本文件记录项目技术选型及配置基准。

## 核心技术栈

| 层级 | 技术 | 版本 | 用途 |
|------|------|------|------|
| 后端框架 | Spring Boot | 3.0.5 | REST API 服务 |
| ORM | MyBatis-Plus | 3.5.3 | 数据库操作 |
| 连接池 | Druid | 1.2.18 | 数据库连接管理 |
| 认证 | JWT (jjwt) | 0.9.1 | 无状态认证 |
| 数据库 | MySQL | 9.7 | 数据持久化 |
| 前端框架 | Vue | 2.6.14 | SPA 前端 |
| UI组件库 | Element UI | 2.15.14 | 后台管理界面 |
| HTTP客户端 | Axios | 0.27.2 | API 请求 |
| 图表库 | ECharts | latest | 数据可视化 |

## 开发环境要求
- JDK：17（Eclipse Temurin）
- Maven：3.9.x
- Node.js：>= 18.x
- MySQL：9.7

## 环境配置
```yaml
# 后端 application.yml
server.port: 8080
spring.datasource.url: jdbc:mysql://localhost:3306/gms
spring.datasource.username: root
spring.datasource.password: root
jwt.token.tokenExpiration: 120
jwt.token.tokenSignKey: gms123456

# 前端 vue.config.js
devServer.port: 8081
proxy: /auth, /admin, /areas, /plants, /planting, /maintenance, /stats
```

## 初始化命令
```bash
# 后端
cd GMS/GMS
JAVA_HOME="C:/Program Files/Eclipse Adoptium/jdk-17.0.19.10-hotspot" mvn spring-boot:run

# 前端
cd green-admin/green-admin/green_admin
npm install
npm run serve

# 数据库
mysql -u root -proot gms < gms.sql
```

## 测试账号
| 账号 | 密码 | 角色 |
|------|------|------|
| admin | admin123 | ADMIN |
| 123456 | 123456 | ADMIN |
| 张三/李四/王五 | 123456 | USER |
