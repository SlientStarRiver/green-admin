<template>
  <el-container class="layout-container">
    <!-- 固定头部 -->
    <el-header class="el-header">
      <div class="top-left">
        <div class="logo-icon"><img src="../assets/img/logo1.0.png" alt=""></div>
        <div class="top-title">绿化区块管理系统</div>
      </div>
      <div class="top-right">
        <el-dropdown trigger="hover">
          <!-- 阻止冒泡，确保点击一定触发 goLogin -->
          <div class="hand" role="button" @click="goLogin">
            <div class="hand-im">管理员</div><img :src="avatarSrc" alt="用户头像" class="avatar-img">
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click.native="goProfile">个人中心</el-dropdown-item>
              <el-dropdown-item @click.native="handleLogout" divided>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>

      </div>
    </el-header>

    <!-- 内容区：header 固定，content 在下方，左侧 aside，右侧 main -->
    <div class="content-wrapper">
      <el-aside class="el-aside">
        <div class="sidebar-menu-wrapper">
          <el-menu class="el-menu-vertical-demo" :default-active="activeMenu" unique-opened>
            <el-menu-item index="dashboard" @click="showView('home')">
              <span class="menu-icon"><img src="../assets/img/home.png" alt=""></span>
              <span class="menu-text">首页</span>
            </el-menu-item>

            <el-menu-item index="blocks" @click="showView('blocks')">
              <span class="menu-icon"><img src="../assets/img/区块.png" alt=""></span>
              <span class="menu-text">区块管理</span>
            </el-menu-item>

            <el-menu-item index="plants" @click="showView('plants')">
              <span class="menu-icon"><img src="../assets/img/植物.png" alt=""></span>
              <span class="menu-text">植物管理</span>
            </el-menu-item>

            <el-submenu index="records">
              <template slot="title">
                <div class="submenu-title-row">
                  <span class="menu-icon"><img src="../assets/img/行为.png" alt=""></span>
                  <span class="menu-text">行为管理</span>
                </div>
              </template>
              <el-menu-item index="records-1" @click="showView('plantrecord')">种植记录</el-menu-item>
              <el-menu-item index="records-2" @click="showView('Maintenancerecord')">养护记录</el-menu-item>
            </el-submenu>

            <el-menu-item index="users" @click="showView('users')">
              <span class="menu-icon"><img src="../assets/img/用户.png" alt=""></span>
              <span class="menu-text">用户管理</span>
            </el-menu-item>
          </el-menu>
        </div>
      </el-aside>

      <el-main class="main-content">
        <router-view />
      </el-main>
    </div>
  </el-container>
</template>

<script>
import { getToken, removeToken } from '@/utils/setToken'
import img1 from '@/assets/img/白底头像.jpg';
import img2 from '@/assets/img/img4.png';
export default {
  data() {
    return {
      logoSrc: '/green_admin/img/logo1.0.png',
      img1: img1,
      img2: img2,
      dynamicToken: getToken(),
    }
  },
  computed: {
    avatarSrc() {
      return this.dynamicToken ? this.img2 : this.img1;
    },
    activeMenu() {
      const menuMap = {
        '/home': 'dashboard',
        '/blocks': 'blocks',
        '/plants': 'plants',
        '/users': 'users',
        '/plantrecord': 'records-1',
        '/Maintenancerecord': 'records-2'
      };
      return menuMap[this.$route.path] || 'dashboard';
    }
  },
  methods: {
    showView(path) {
      this.$router.push('/HeadWeb/' + path);
    },
    // 辅助加载图片（保留回退）
    img(filename) {
      try { return require(`@/assets/${filename}`) } catch (e) { return `/green_admin/img/${filename}` }
    },
    // 点击头像：无条件跳转到登录页（忽略当前登录状态）
    goLogin() {
      this.$router.push('/LoginBox');
    },
    goProfile() {
      this.$router.push('/HeadWeb/profile');
    },
    handleLogout() {
      removeToken();
      this.$message.success('已退出登录');
      this.$router.push('/LoginBox');
    }
  }
}
</script>






<style scoped>
/* 顶部（固定） */
.el-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 80px !important;
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: linear-gradient(90deg, #0C642B, #006B3E);
  color: #fff;
  z-index: 1000;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08);
  box-sizing: border-box;
}

/* 头部内容 */
.top-left {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-left: 20px;
}

.logo-icon {
  width: 36px;
  height: 36px;
  flex: 0 0 36px;
}

.logo-icon img {
  width: 100%;
  height: 100%;
  object-fit: contain;
  display: block;
}

.hand {
  position: relative;
  cursor: pointer;
  margin-right: 30px;
  width: 120px;
  height: 50px;
  display: flex;
  flex-direction: row;
  color: white;
  padding-right: 0px;
}

.hand-im {
  margin-right: 20px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  font-size: 15px;
}

.hand img {
  /* width: 100%;
  height: 100%; */

}

.top-title {
  font-size: 38px;
  font-weight: 700;
  color: #fff;
}

/* 整体内容，放在 header 之下 */
.content-wrapper {
  display: flex;
  margin-top: 80px;
  /* 与 header 高度一致，防止遮挡 */
  height: calc(100vh - 80px);
  overflow: hidden;
}

/* 侧栏 */
.el-aside {
  width: 220px;
  background: #fff;
  height: calc(100vh - 80px);
  /* 减去 header 高度，避免滚动被遮挡 */
  box-sizing: border-box;
  padding: 16px;
  border-right: 1px solid #e9eef0;
  overflow: hidden;
}

/* 菜单容器 */
.sidebar-menu-wrapper {
  height: 100%;
  overflow: auto;
  padding-top: 6px;
}

/* 菜单项样式 */
.el-menu-vertical-demo {
  background: transparent;
  border-right: none;
  width: 100%;
}

.el-menu-vertical-demo .el-menu-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 10px 12px;
  border-radius: 8px;
  margin-bottom: 6px;
  transition: background .18s, color .18s;
  color: #333;
}

.menu-icon {
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex: 0 0 36px;
}

.menu-icon img {
  width: 24px;
  height: 24px;
  object-fit: contain;
  display: block;
}

.menu-text {
  font-size: 15px;
  font-weight: 700;
}

.menu-text-1 {
  font-size: 20px;
  font-weight: 400;
  margin-left: 50px;
}

/* 悬停与激活 */
.el-menu-vertical-demo .el-menu-item:hover {
  background: #f0faf4;
  color: #006B3E;
}

.el-menu-vertical-demo .el-menu-item.is-active {
  background: #006B3E;
  color: #fff;
  box-shadow: 0 2px 8px rgba(3, 83, 39, 0.08);
}

.el-menu-vertical-demo .el-menu-item.is-active .menu-icon {
  background: rgba(255, 255, 255, 0.12);
}

/* 主区 */
.main-content {
  flex: 1;
  background: transparent;
  padding: 18px;
  height: 100%;
  overflow: auto;
  box-sizing: border-box;
}

/* 保证子菜单标题（图标 + 文本）在同一行 */
.submenu-title-row {
  display: flex;
  align-items: center;
  gap: 12px;
}

/* scoped 下穿透 element 子组件，额外保障 subtitle 区域样式 */
.el-menu-vertical-demo ::v-deep .el-submenu__title {
  display: flex;
  align-items: center;
  gap: 12px;
}

/* 响应式：窄屏隐藏文字并收缩侧栏 */
@media (max-width:900px) {
  .el-aside {
    width: 72px;
    padding: 10px;
  }

  .menu-text {
    display: none;
  }

  .top-title {
    font-size: 18px;
  }
}

/* 保证图片在不同 DPI 下清晰 */
img {
  image-rendering: -webkit-optimize-contrast;
}

/* 小调整避免 element 内部默认样式遮挡 */
.el-menu-vertical-demo .el-menu-item .el-icon {
  margin-right: 0;
}
</style>