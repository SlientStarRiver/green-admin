import service from '../config'

// 获取种植记录列表
export function getPlantingList(params) {
    return service({ url: '/planting/records', method: 'get', params })
}

// 新增种植记录
export function createPlanting(data) {
    return service({ url: '/planting/records', method: 'post', data })
}

// 更新种植记录
export function updatePlanting(id, data) {
    return service({ url: `/planting/records/${id}`, method: 'put', data })
}

// 删除种植记录
export function deletePlanting(id) {
    return service({ url: `/planting/records/${id}`, method: 'delete' })
}
