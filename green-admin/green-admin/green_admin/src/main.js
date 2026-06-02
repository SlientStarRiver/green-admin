import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import router from './router'//路由切换页面
// import HomeBox from '@/home/HomeBox.vue'

Vue.use(ElementUI)
// Vue.use(router)
// 全局注册 homebox，HeadWeb.vue 只需在模板使用 <homebox />
// Vue.component('HomeBox', HomeBox)

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
