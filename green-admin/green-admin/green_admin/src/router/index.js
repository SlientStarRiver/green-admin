import Vue from 'vue';
import VueRouter from 'vue-router';
import { getToken } from '@/utils/setToken';

Vue.use(VueRouter);

const routes = [
    {
        path: '/LoginBox',
        name: 'LoginBox',
        component: () => import('@/login/LoginBox.vue'),
        meta: { noLayout: true }
    },
    {
        path: '/RegisterBox',
        name: 'RegisterBox',
        component: () => import('@/login/RegisterBox.vue'),
        meta: { noLayout: true }
    },
    {
        path: '/',
        component: () => import('@/components/HeadWeb.vue'),
        children: [
            { path: '', redirect: 'home' },
            { path: 'home', component: () => import('@/home/HomeBox.vue') },
            { path: 'blocks', component: () => import('@/home/BlockBox.vue') },
            { path: 'plants', component: () => import('@/home/PlantsBox.vue') },
            { path: 'users', component: () => import('@/home/UserBox.vue') },
            { path: 'plantrecord', component: () => import('@/home/PlantsRecord.vue') },
            { path: 'Maintenancerecord', component: () => import('@/home/MaintenanceRecord.vue') }
        ]
    },
    { path: '*', redirect: '/LoginBox' }
];

const router = new VueRouter({
    mode: 'hash',
    base: process.env.BASE_URL,
    routes
});

// 重写 push 和 replace，抑制 NavigationRedirected 和 NavigationDuplicated 错误
const originalPush = VueRouter.prototype.push;
const originalReplace = VueRouter.prototype.replace;

VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => {
        if (!['NavigationDuplicated', 'NavigationRedirected'].includes(err.name)) throw err;
    });
};

VueRouter.prototype.replace = function replace(location) {
    return originalReplace.call(this, location).catch(err => {
        if (!['NavigationDuplicated', 'NavigationRedirected'].includes(err.name)) throw err;
    });
};

// 路由守卫：未登录跳转登录页
router.beforeEach((to, from, next) => {
    const token = getToken();
    const isPublic = to.path === '/LoginBox' || to.path === '/RegisterBox';

    if (!token && !isPublic) {
        next('/LoginBox');
    } else if (isPublic && token) {
        next('/');
    } else {
        next();
    }
});

export default router;
