# rules.md

> 本文件供 AI Coder 在整个开发过程中遵守。

## 项目概述
绿化管理系统（GMS），管理绿化区块、植物品种、种植记录和养护记录，支持管理员和普通用户双角色。

## 技术栈约束
- 后端：Spring Boot 3.0.5 + MyBatis-Plus 3.5.3 + JWT
- 前端：Vue 2.6.14 + Element UI 2.15.14 + ECharts
- 数据库：MySQL 9.7
- 包管理：npm

## 代码风格
- 后端：Spring Boot 分层（Controller → Service → Mapper）
- 前端：Vue 2 Options API + Element UI 组件
- 命名：后端 camelCase，数据库 snake_case

## 文件结构约定
```
GMS/src/main/java/com/gms/
├── controller/       # 控制器层
├── service/impl/     # 服务实现
├── mapper/           # Mapper接口
├── pojo/             # 实体类
└── utils/            # 工具类

green-admin/src/
├── api/api/          # API封装
├── components/       # 公共组件
├── home/             # 页面组件
├── login/            # 登录/注册
└── router/           # 路由配置
```

## 命名规范
- RESTful URL：小写复数
- 管理端接口：`/admin/` 前缀
- 数据库表名：snake_case
- 实体类：PascalCase
- 前端组件：PascalCase

## Git 提交规范
格式：`<type>(<scope>): <subject>`
类型：feat / fix / docs / style / refactor / test / chore

## 功能模块
- 认证：登录/注册/登出/修改密码
- 区块管理：CRUD + 搜索
- 植物管理：CRUD + 搜索
- 种植/养护记录：CRUD + 关联查询
- 用户管理：列表/编辑/删除
- 操作日志：查询/筛选
- 统计图表：ECharts 柱状图/饼图
