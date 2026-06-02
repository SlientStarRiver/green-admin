import service from "../config";

export function userLogin(data) {
    return service({
        url: "/auth/user/login",
        method: "post",
        data
    });
}

export function adminLogin(data) {
    return service({
        url: "/auth/admin/login",
        method: "post",
        data
    });
}

export function register(data) {
    return service({
        url: "/auth/register",
        method: "post",
        data
    });
}

export function userLogout() {
    return service({
        url: "/auth/logout",
        method: "post"
    });
}

export function getUserInfo(userId) {
    return service({
        url: "/auth/user/info",
        method: "get",
        params: { userId }
    });
}

export function changePassword(data) {
    return service({
        url: "/auth/change-password",
        method: "post",
        data
    });
}