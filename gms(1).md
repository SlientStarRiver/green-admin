# 绿化管理系统接口文档

## 1. 认证接口

### 1.1 用户登录
- **URL**: `/auth/user/login`
- **方法**: POST
- **请求参数**:
```json
{
  "username": "string",
  "password": "string"
}
```
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": {
    "token": "string"
  }
}
```
### 1.2 管理员登录
- **URL**: `/auth/admin/login`
- **方法**: POST
- **请求参数**:
```json
{
  "username": "string",
  "password": "string"
}
```
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": {
    "token": "string"
  }
}
```

### 1.3 用户注册
- **URL**: `/auth/register`
- **方法**: POST
- **请求参数**:
```json
{
  "username": "string",
  "password": "string",
  "phone": "string"
}
```
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": null
}
```

### 1.4 用户登出
- **URL**: `/auth/logout`
- **方法**: POST
- **请求参数**: 无
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": null
}
```

## 2. 用户管理接口

### 2.1 获取用户列表（分页）
- **URL**: `/admin/users`
- **方法**: GET
- **请求参数**:
  - `page`: 页码，默认1
  - `size`: 每页数量，默认10

- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": {
    "records": [
      {
        "id": 0,
        "username": "string",
        "phone": "string",
        "role": "string",
        "registerTime": "2025-01-01T00:00:00",
        "maintenanceCount": 0,
        "plantingCount": 0
      }
    ],
    "totalPages": 0,
    "hasPrevious": false,
    "currentPage": 0,
    "hasNext": false
  }
}
```

### 2.2 获取用户详情
- **URL**: `/admin/users/{id}`
- **方法**: GET
- **请求参数**: 无
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": {
    "id": 0,
    "username": "string",
    "phone": "string",
    "role": "string",
    "registerTime": "2025-01-01T00:00:00",
    "maintenanceCount": 0,
    "plantingCount": 0
  }
}
```

### 2.3 更新用户信息
- **URL**: `/admin/users/{id}`
- **方法**: PUT
- **请求参数**:
```json
{
  "username": "string",
  "phone": "string",
  "role": "string",
  "maintenanceCount": 0,
  "plantingCount": 0
}
```
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": null
}
```

### 2.4 删除用户
- **URL**: `/admin/users/{id}`
- **方法**: DELETE
- **请求参数**: 无
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": null
}
```

## 3. 区块管理接口

### 3.1 获取区块列表（分页）
- **URL**: `/areas`
- **方法**: GET
- **请求参数**:
  - `page`: 页码，默认1
  - `size`: 每页数量，默认10
  - `areaName`: 区块名称（可选，模糊搜索）
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": {
    "records": [
      {
        "areaId": "string",
        "areaName": "string",
        "areaSize": 0,
        "locationDescription": "string",
        "createdTime": "2025-01-01T00:00:00",
        "updatedTime": "2025-01-01T00:00:00"
      }
    ],
    "totalPages": 0,
    "hasPrevious": false,
    "currentPage": 0,
    "hasNext": false
  }
}
```

### 3.2 获取区块详情
- **URL**: `/areas/{areaId}`
- **方法**: GET0
- **请求参数**: 无
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": {
    "areaId": "string",
    "areaName": "string",
    "areaSize": 0,
    "locationDescription": "string",
    "createdTime": "2025-01-01T00:00:00",
    "updatedTime": "2025-01-01T00:00:00"
  }
}
```

### 3.3 新增区块
- **URL**: `/admin/areas`
- **方法**: POST
- **请求参数**:
```json
{
  "areaId": "string",
  "areaName": "string",
  "areaSize": 0,
  "locationDescription": "string"
}
```
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": null
}
```

### 3.4 更新区块信息
- **URL**: `/admin/areas/{areaId}`
- **方法**: PUT
- **请求参数**:
```json
{
  "areaName": "string",
  "areaSize": 0,
  "locationDescription": "string"
}
```
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": null
}
```

### 3.5 删除区块
- **URL**: `/admin/areas/{areaId}`
- **方法**: DELETE
- **请求参数**: 无
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": null
}
```

## 4. 植物品种管理接口

### 4.1 获取植物品种列表（分页）
- **URL**: `/plants/species`
- **方法**: GET
- **请求参数**:
  - `page`: 页码，默认1
  - `size`: 每页数量，默认10
  - `speciesName`: 品种名称（可选，模糊搜索）
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": {
    "records": [
      {
        "speciesId": "string",
        "speciesName": "string",
        "createdTime": "2025-01-01T00:00:00"
      }
    ],
    "totalPages": 0,
    "hasPrevious": false,
    "currentPage": 0,
    "hasNext": false
  }
}
```
### 4.2 新增植物品种
- **URL**: `/admin/plants/species`
- **方法**: POST
- **请求参数**:
```json
{
  "speciesId": "string",
  "speciesName": "string"
}
```
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": null
}
```
## 5. 种植记录接口

### 5.1 获取种植记录列表（分页）
- **URL**: `/planting/records`
- **方法**: GET
- **请求参数**:
  - `page`: 页码，默认1
  - `size`: 每页数量，默认10
  - `speciesId`: 品种编号（可选）
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": {
    "records": [
      {
        "id": 0,
        "areaId": "string",
        "speciesId": "string",
        "plantingTime": "2025-01-01T00:00:00",
        "plantCount": 0,
        "responsiblePerson": "string",
        "description": "string",
        "createdTime": "2025-01-01T00:00:00",
        "areaName": "string",
        "speciesName": "string"
      }
    ],
    "totalPages": 0,
    "hasPrevious": false,
    "currentPage": 0,
    "hasNext": false
  }
}
```
### 5.2 新增种植记录
- **URL**: `/planting/records`
- **方法**: POST
- **请求参数**:
```json
{
  "areaId": "string",
  "speciesId": "string",
  "plantingTime": "2025-01-01T00:00:00",
  "plantCount": 0,
  "responsiblePerson": "string",
  "description": "string"
}
```
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": null
}
```

### 5.3 更新种植记录
- **URL**: `/planting/records/{id}`
- **方法**: PUT
- **请求参数**:
```json
{
  "areaId": "string",
  "speciesId": "string",
  "plantingTime": "2025-01-01T00:00:00",
  "plantCount": 0,
  "responsiblePerson": "string",
  "description": "string"
}
```
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": null
}
```

### 5.4 删除种植记录
- **URL**: `/planting/records/{id}`
- **方法**: DELETE
- **请求参数**: 无
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": null
}
```

## 6. 养护记录接口

### 6.1 获取养护记录列表（分页）
- **URL**: `/maintenance/records`
- **方法**: GET
- **请求参数**:
  - `page`: 页码，默认1
  - `size`: 每页数量，默认10
  - `speciesId`: 品种编号（可选）
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": {
    "records": [
      {
        "id": 0,
        "areaId": "string",
        "speciesId": "string",
        "maintenanceTime": "2025-01-01T00:00:00",
        "plantCount": 0,
        "maintainer": "string",
        "description": "string",
        "createdTime": "2025-01-01T00:00:00",
        "areaName": "string",
        "speciesName": "string"
      }
    ],
    "totalPages": 0,
    "hasPrevious": false,
    "currentPage": 0,
    "hasNext": false
  }
}
```
### 6.2 新增养护记录
- **URL**: `/maintenance/records`
- **方法**: POST
- **请求参数**:
```json
{
  "areaId": "string",
  "speciesId": "string",
  "maintenanceTime": "2025-01-01T00:00:00",
  "plantCount": 0,
  "maintainer": "string",
  "description": "string"
}
```
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": null
}
```

### 6.3 更新养护记录
- **URL**: `/maintenance/records/{id}`
- **方法**: PUT
- **请求参数**:
```json
{
  "areaId": "string",
  "speciesId": "string",
  "maintenanceTime": "2025-01-01T00:00:00",
  "plantCount": 0,
  "maintainer": "string",
  "description": "string"
}
```
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": null
}
```

### 6.4 删除养护记录
- **URL**: `/maintenance/records/{id}`
- **方法**: DELETE
- **请求参数**: 无
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": null
}
```

## 7. 统计接口

### 7.1 获取首页统计数据
- **URL**: `/stats/dashboard`
- **方法**: GET
- **请求参数**: 无
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": {
        "areaCount": 0,
        "monthlyMaintenanceCount": 0,
        "maintainerCount": 0,
        "planterPercentage": 0,
        "maintainerPercentage": 0,
        "plantSpeciesCount": 0,
        "planterCount": 0,
        "monthlyPlantingCount": 0
  }
}
```

### 7.2 获取近日活动
- **URL**: `/stats/recent-activities`
- **方法**: GET
- **请求参数**: 无
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": {
    "plantingActivities": [
      {
        "id": 0,
        "areaId": "string",
        "areaName": "string",
        "speciesId": "string",
        "speciesName": "string",
        "responsiblePerson": "string",
        "plantingTime": "2025-01-01T00:00:00",
        "type": "string"
      }
    ],
    "maintenanceActivities": [
      {
        "id": 0,
        "areaId": "string",
        "areaName": "string",
        "speciesId": "string",
        "speciesName": "string",
        "maintainer": "string",
        "maintenanceTime": "2025-01-01T00:00:00",
        "type": "string"
      }
    ]
  }
}
```

### 7.3 获取植物分布数据
- **URL**: `/stats/plant-distribution`
- **方法**: GET
- **请求参数**: 无
- **响应参数**:
```json
{
  "code": 200,
  "message": "string",
  "data": [
    {
      "areaId": "string",
      "areaName": "string",
      "speciesCount": 0,
      "plantTotalCount": 0
    }
  ]
}
```

## 8. 错误响应格式

所有接口的错误响应格式统一为：
```json
{
  "code": 500,
  "message": "错误描述信息",
  "data": null
}
```

## 9. 权限说明
接口为/admin/ 开头是管理端接口
其余是用户端接口

## 10. 状态码说明

- `200`: 请求成功
- `400`: 请求参数错误
- `401`: 未认证
- `403`: 权限不足
- `404`: 资源不存在
- `500`: 服务器内部错误

