# 绿化管理系统 (GMS)

Green Management System — 管理绿化区块、植物品种、种植记录和养护记录的 Web 应用。

## 技术栈

| 层级 | 技术 |
|------|------|
| 后端 | Spring Boot 3.0.5 + MyBatis-Plus 3.5.3 |
| 数据库 | MySQL 9.7 |
| 认证 | JWT (jjwt 0.9.1) |
| 前端 | Vue 2.6.14 + Element UI 2.15.14 |

## 功能模块

- **认证** — 用户注册/登录、管理员登录、JWT Token 认证
- **区块管理** — 绿化区块 CRUD、模糊搜索、分页
- **植物品种** — 品种列表、管理员新增
- **种植记录** — 记录种植信息（区块、品种、负责人、数量）
- **养护记录** — 记录养护信息（区块、品种、养护人、数量）
- **统计仪表盘** — 首页概览、近日活动、植物分布
- **用户管理** — 管理员对用户的增删改查

## 快速启动

### 环境要求

- JDK 17
- Maven 3.9+
- MySQL 9.7
- Node.js 18+

### 后端

```bash
# 1. 创建数据库
mysql -u root -proot < gms.sql

# 2. 修改数据库密码（如不同）
# 编辑 GMS/GMS/src/main/resources/application.yml

# 3. 启动
cd GMS/GMS
mvn spring-boot:run
# 访问 http://localhost:8080
```

### 前端

```bash
cd green-admin/green-admin/green_admin
npm install
npm run serve
# 访问 http://localhost:8081
```

## 项目结构

```
zwj/
├── GMS/                        # 后端
│   └── src/main/java/com/gms/
│       ├── controller/         # 接口层
│       ├── service/            # 业务层
│       ├── mapper/             # 数据访问层
│       ├── pojo/               # 实体类
│       └── utils/              # 工具类
├── green-admin/                # 前端
│   └── src/
│       ├── api/                # API 请求
│       ├── home/               # 首页模块
│       ├── login/              # 登录模块
│       ├── components/         # 公共组件
│       └── router/             # 路由
├── gms.sql                     # 数据库脚本
└── api-docs/openapi.yaml       # 接口文档
```

## 角色权限

| 角色 | 权限 |
|------|------|
| ADMIN | 全部模块增删改查 |
| USER | 只读 + 记录增改 |
