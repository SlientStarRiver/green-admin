import service from '../config'

// 获取首页统计数据
export function getDashboard() {
    return service({ url: '/stats/dashboard', method: 'get' })
}

// 获取近日活动
export function getRecentActivities() {
    return service({ url: '/stats/recent-activities', method: 'get' })
}

// 获取植物分布
export function getPlantDistribution() {
    return service({ url: '/stats/plant-distribution', method: 'get' })
}
