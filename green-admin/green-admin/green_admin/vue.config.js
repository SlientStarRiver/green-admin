const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    host: '0.0.0.0',
    port: 8081,       // 前端开发服务器端口
    open: true,       // 启动时自动打开浏览器
    hot: true,
    // 本地开发时把指定前缀的请求代理到后端（避免 CORS）
     client: {
      webSocketURL: 'ws://localhost:8081/ws',
    },
    proxy: {
      // 直接把 /auth/* 转发到后端（保留 /auth 前缀）
      '/auth': {
        target: 'http://localhost:8080', // 后端服务地址（本地启动）
        changeOrigin: true,
        secure: false,
        ws: true
        // 若后端期望去掉前缀可加 pathRewrite: {'^/auth': ''}
      },

      // 如果后端其它接口都在 /api 前缀下，可类似配置：
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
        ws: true
      }
    }
  }
})
