<template>
  <div id="app">
    <!-- 有些路由（如 LoginBox）不需要 layout（meta.noLayout = true） -->
    <!-- <HeadWeb />
    - <router-view /> -->
    <HeadWeb v-if="!$route.meta || !$route.meta.noLayout" />
    <router-view />
  </div>
</template>

<script>

import HeadWeb from '@/components/HeadWeb.vue'  // 确保正确导入组件


export default {
  name: 'App',
  components: {
    HeadWeb
  }
}
</script>

<style>
* {
  margin: 0;
}

#app {
  /* 创建新的BFC防止布局错乱 */
  display: flow-root;
}

/* 头部固定定位时的处理方案 */
.head {
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 1000;
}

/* 主内容区间距修正 */
.router-view-container {
  padding-top: 90px;
  /* 与头部高度一致 */
  min-height: calc(100vh - 90px);
}
</style>
