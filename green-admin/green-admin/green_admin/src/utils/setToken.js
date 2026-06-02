//设置token
export function setToken(token) {
    return localStorage.setItem('token', token)
}
//存储token
export function getToken() {
    return localStorage.getItem('token')
}
//移除token
export function removeToken() {
    return localStorage.removeItem('token')
}