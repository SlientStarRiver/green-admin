import service from "../config";
export function login(data) {
    return service({
        url: "/auth/login",
        method: "post",
        data
    });
}

// export function register(data) {
//     return service({
//         url: "/user/register/",
//         method: "post",
//         data
//     });
// }