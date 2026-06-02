import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import router from './router'

Vue.use(ElementUI)

Vue.config.productionTip = false

// 抑制 Vue Router 守卫重定向产生的 NavigationRedirected 错误
Vue.config.errorHandler = (err) => {
    if (err.name === 'NavigationRedirected') return;
    console.error(err);
};

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
