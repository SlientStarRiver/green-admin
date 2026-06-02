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