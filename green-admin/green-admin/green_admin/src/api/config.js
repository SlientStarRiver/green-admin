import axios from 'axios'
import nprogress from 'nprogress'
import 'nprogress/nprogress.css'
import { Message } from 'element-ui'
import router from '@/router'
import { getToken, removeToken } from '@/utils/setToken';

// 创建可配置实例
const createService = (baseURL = '') => {
    const service = axios.create({
        baseURL,
        timeout: 10000,
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            'X-Requested-With': 'XMLHttpRequest'
        },
        withCredentials: true
    })

    // 请求拦截器
    service.interceptors.request.use(
        config => {
            nprogress.start()
            // 自动携带 Token
            if (getToken()) {
                config.headers.Authorization = getToken()
            }
            return config
        },
        error => {
            nprogress.done()
            return Promise.reject(error)
        }
    )









    // 响应拦截器
    service.interceptors.response.use(
        response => {
            nprogress.done()
            return handleResponse(response)
        },
        error => {
            nprogress.done()
            return handleError(error)
        }
    )

    /**
     * 统一处理正常响应
     * @param {AxiosResponse} response 
     */
    const handleResponse = (response) => {
        const { status, data } = response

        // 处理特殊状态码
        if ([200, 201, 204].includes(status)) {

            return data
        }

        const errorMsg = data?.message || data?.msg || `请求异常 [${status}]`
        return Promise.reject(new Error(errorMsg))
    }

    /**
     * 统一错误处理
     * @param {AxiosError} error 
     */
    const handleError = (error) => {






        // HTTP 状态码错误处理
        if (error.response) {
            const { status, data } = error.response
            const errorMessage = data?.msg || `请求失败 [${status}]`

            // 状态码映射处理
            const statusHandlers = {
                400: () => Message.error('请求参数错误'),
                401: () => handleAuthError('登录状态已过期，请重新登录'),
                403: () => handleAuthError('没有操作权限'),
                404: () => Message.error('资源不存在'),
                500: () => Message.error('服务器开小差了，请稍后重试'),
                502: () => Message.error('网关错误'),
                503: () => Message.error('服务不可用'),
                504: () => Message.error('网关超时')
            }

            // 执行对应处理
            statusHandlers[status] ? statusHandlers[status]() : Message.error(errorMessage)
            return Promise.reject(new Error(errorMessage))
        }

        // 非响应错误处理
        if (error.message.includes('timeout')) {
            Message.error('请求超时，请检查网络')
        } else if (error.message.includes('Network Error')) {
            Message.error('网络连接异常')
        } else {
            Message.error('请求发送失败')
        }
        return Promise.reject(error)
    }

    // 认证异常处理
    const handleAuthError = (msg) => {
        Message.error(msg)
        removeToken()
        // 避免路由重复跳转
        if (router.currentRoute.path !== '/login') {
            router.replace({
                path: '/loginPage',
            })
        }
    }

    return service
}

// 创建默认实例
const service = createService()

export default service
