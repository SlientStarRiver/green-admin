import service from '../config'

// 获取养护记录列表
export function getMaintenanceList(params) {
    return service({ url: '/maintenance/records', method: 'get', params })
}

// 新增养护记录
export function createMaintenance(data) {
    return service({ url: '/maintenance/records', method: 'post', data })
}

// 更新养护记录
export function updateMaintenance(id, data) {
    return service({ url: `/maintenance/records/${id}`, method: 'put', data })
}

// 删除养护记录
export function deleteMaintenance(id) {
    return service({ url: `/maintenance/records/${id}`, method: 'delete' })
}
