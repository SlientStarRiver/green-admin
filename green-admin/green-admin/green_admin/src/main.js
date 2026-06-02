import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import router from './router'

Vue.use(ElementUI)

Vue.config.productionTip = false

// 全局抑制 Vue Router NavigationRedirected 错误
Vue.config.errorHandler = (err) => {
    if (err.name === 'NavigationRedirected') return;
    console.error(err);
};

window.addEventListener('unhandledrejection', (event) => {
    if (event.reason && event.reason.name === 'NavigationRedirected') {
        event.preventDefault();
    }
});

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
