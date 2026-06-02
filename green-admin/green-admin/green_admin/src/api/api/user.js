import service from '../config'

// 获取用户列表
export function getUserList(params) {
    return service({ url: '/admin/users', method: 'get', params })
}

// 获取用户详情
export function getUserDetail(id) {
    return service({ url: `/admin/users/${id}`, method: 'get' })
}

// 更新用户
export function updateUser(id, data) {
    return service({ url: `/admin/users/${id}`, method: 'put', data })
}

// 删除用户
export function deleteUser(id) {
    return service({ url: `/admin/users/${id}`, method: 'delete' })
}
