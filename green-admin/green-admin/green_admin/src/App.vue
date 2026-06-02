<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
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
