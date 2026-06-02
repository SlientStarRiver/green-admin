# rules.md

> 本文件由项目启动架构师生成，供 AI Coder 在整个开发过程中遵守。

## 项目概述
绿化管理系统（GMS），管理绿化区块、植物品种、种植记录和养护记录，支持管理员和普通用户双角色。

## 技术栈约束
- 后端框架：Spring Boot 3.0.5
- ORM：MyBatis-Plus 3.5.3
- 连接池：Druid 1.2.18
- 认证：JWT（jjwt 0.9.1）
- 数据库：MySQL 9.7
- 前端框架：Vue 2.6.14
- UI 组件库：Element UI 2.15.14
- 包管理：npm

## 代码风格
- 后端：Spring Boot 标准分层（Controller → Service → Mapper）
- 前端：Vue 2 Options API + Element UI 组件
- 命名：后端 camelCase，数据库 snake_case
- 注释：公共接口必须有 JSDoc/JavaDoc 注释

## 文件结构约定
```
GMS/
├── src/main/java/com/gms/
│   ├── Config/           # 配置类（CORS、MyMetaObjectHandler）
│   ├── controller/       # 控制器层
│   ├── service/          # 服务接口
│   │   └── impl/         # 服务实现
│   ├── mapper/           # MyBatis-Plus Mapper接口
│   ├── pojo/             # 实体类
│   ├── utils/            # 工具类（JwtHelper、Result）
│   └── exception/        # 全局异常处理
├── src/main/resources/
│   ├── application.yml   # 配置文件
│   └── com/gms/mapper/   # MyBatis XML映射文件
green-admin/
├── src/
│   ├── api/              # API请求封装
│   ├── components/       # 公共组件
│   ├── home/             # 首页模块
│   ├── login/            # 登录模块
│   ├── router/           # 路由配置
│   ├── utils/            # 工具函数
│   ├── assets/           # 静态资源
│   ├── App.vue           # 根组件
│   └── main.js           # 入口文件
```

## 命名规范
- RESTful URL：小写复数（`/areas`、`/planting/records`）
- 管理端接口：`/admin/` 前缀
- 数据库表名：snake_case（`planting_record`）
- 实体类：PascalCase（`PlantingRecord`）
- 前端组件：PascalCase

## Git 提交规范
格式：`<type>(<scope>): <subject>`
类型：feat / fix / docs / style / refactor / test / chore

## 禁止行为
- 禁止在 Controller 层直接操作数据库
- 禁止硬编码密钥和数据库密码
- 禁止跳过异常处理直接返回前端错误堆栈
- 禁止删除外键约束关联的数据（需先检查引用）
