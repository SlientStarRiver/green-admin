import service from '../config'

// 获取植物品种列表
export function getSpeciesList(params) {
    return service({ url: '/plants/species', method: 'get', params })
}

// 新增植物品种
export function createSpecies(data) {
    return service({ url: '/admin/plants/species', method: 'post', data })
}
