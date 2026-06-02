const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    host: '0.0.0.0',
    port: 8081,
    open: true,
    hot: true,
    client: {
      webSocketURL: 'ws://localhost:8081/ws',
    },
    proxy: {
      '/auth': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/admin': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/areas': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/plants': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/planting': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/maintenance': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/stats': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      }
    }
  }
})
