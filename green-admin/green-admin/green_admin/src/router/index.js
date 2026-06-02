import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const routes = [
    // 1. 登录页：独立路由（无布局）
    {
        path: '/LoginBox',
        name: 'LoginBox',
        component: () => import('@/login/LoginBox.vue'),
        meta: { noLayout: true } // 登录页不使用 HeadWeb 布局
    },
    // 2. 主布局路由（包含 HeadWeb + 子页面）
    {
        path: '/',
        component: () => import('@/components/HeadWeb.vue'),
        children: [
            { path: '', redirect: 'home' }, // 根路径重定向到首页
            { path: 'home', component: () => import('@/home/HomeBox.vue') },
            { path: 'blocks', component: () => import('@/home/BlockBox.vue') },
            { path: 'plants', component: () => import('@/home/PlantsBox.vue') },
            { path: 'users', component: () => import('@/home/UserBox.vue') },
            { path: 'plantrecord', component: () => import('@/home/PlantsRecord.vue') },
            { path: 'Maintenancerecord', component: () => import('@/home/MaintenanceRecord.vue') }
        ]
    },
    // 3. 404 重定向到登录页
    { path: '*', redirect: '/LoginBox' }
];

const router = new VueRouter({
    mode: 'hash', // 改用 hash 模式（无需后端配置，避免跳转失效）
    base: process.env.BASE_URL,
    routes
});

// 全局重写 push，避免重复跳转报错
const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => {
        if (err.name !== 'NavigationDuplicated') throw err;
    });
};

export default router;