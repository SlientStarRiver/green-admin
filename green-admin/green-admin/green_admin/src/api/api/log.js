import service from '../config'

export function getLogList(params) {
    return service({ url: '/admin/logs', method: 'get', params })
}
