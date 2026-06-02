import service from '../config'

// 获取区块列表
export function getAreaList(params) {
    return service({ url: '/areas', method: 'get', params })
}

// 获取区块详情
export function getAreaDetail(areaId) {
    return service({ url: `/areas/${areaId}`, method: 'get' })
}

// 新增区块
export function createArea(data) {
    return service({ url: '/admin/areas', method: 'post', data })
}

// 更新区块
export function updateArea(areaId, data) {
    return service({ url: `/admin/areas/${areaId}`, method: 'put', data })
}

// 删除区块
export function deleteArea(areaId) {
    return service({ url: `/admin/areas/${areaId}`, method: 'delete' })
}
