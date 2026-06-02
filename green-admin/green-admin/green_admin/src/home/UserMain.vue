<template>
  <el-container class="layout-container">
    <el-header class="el-header">
      <div class="top-left">
        <div class="logo-icon"><img src="../assets/img/logo1.0.png" alt=""></div>
        <div class="top-title">绿化区块系统</div>
      </div>
      <div class="top-right">
        <el-dropdown trigger="hover">
          <div class="hand" role="button">
            <img :src="avatarSrc" alt="用户头像" class="avatar-img">
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

    <div class="content-wrapper">
      <el-aside class="el-aside">
        <div class="sidebar-menu-wrapper">
          <el-menu class="el-menu-vertical-demo" :default-active="activeMenu" unique-opened>
            <el-menu-item index="dashboard" @click="showView('home')">
              <span class="menu-icon"></span>
              <span class="menu-text">首页</span>
            </el-menu-item>
            <el-menu-item index="records-1" @click="showView('plantrecord')">
              <span class="menu-icon"></span>
              <span class="menu-text">我要种植</span>
            </el-menu-item>
            <el-menu-item index="records-2" @click="showView('Maintenancerecord')">
              <span class="menu-icon"></span>
              <span class="menu-text">我要养护</span>
            </el-menu-item>
          </el-menu>
        </div>
      </el-aside>

      <el-main class="main-content">
        <UserHomeBox v-if="activeView === 'UserHomeBox'" />
        <MaintenanceRecord v-if="activeView === 'Maintenancerecord'" />
        <PlantsRecord v-if="activeView === 'plantrecord'" />
        <Profile v-if="activeView === 'profile'" />
      </el-main>
    </div>
  </el-container>
</template>

<script>
import { getToken, removeToken } from '@/utils/setToken'
import { userLogout } from '@/api/api/login'
import UserHomeBox from '@/home/UserHomeBox.vue'
import PlantsRecord from '@/home/PlantsRecord.vue'
import MaintenanceRecord from '@/home/MaintenanceRecord.vue'
import Profile from '@/home/Profile.vue'
import img1 from '@/assets/img/白底头像.jpg'
import img2 from '@/assets/img/img4.png'

export default {
  components: { PlantsRecord, MaintenanceRecord, UserHomeBox, Profile },
  data() {
    return {
      activeView: 'UserHomeBox',
      activeMenu: 'dashboard',
      img1: img1,
      img2: img2,
      dynamicToken: getToken(),
    }
  },
  computed: {
    avatarSrc() {
      return this.dynamicToken ? this.img2 : this.img1;
    }
  },
  methods: {
    showView(name) {
      if (name === 'home' || name === 'dashboard') {
        this.activeView = 'UserHomeBox'
        this.activeMenu = 'dashboard'
      } else if (name === 'plantrecord') {
        this.activeView = 'plantrecord'
        this.activeMenu = 'records-1'
      } else if (name === 'Maintenancerecord') {
        this.activeView = 'Maintenancerecord'
        this.activeMenu = 'records-2'
      }
    },
    goProfile() {
      this.activeView = 'profile'
      this.activeMenu = ''
    },
    async handleLogout() {
      try {
        await userLogout()
        removeToken()
        this.$message.success('退出登录成功')
        this.$router.push('/LoginBox')
      } catch (error) {
        removeToken()
        this.$router.push('/LoginBox')
      }
    }
  }
}
</script>

<style scoped>
.el-header {
  position: fixed; top: 0; left: 0; right: 0; height: 80px !important;
  padding: 0 20px; display: flex; align-items: center; justify-content: space-between;
  background: linear-gradient(90deg, #0C642B, #006B3E); color: #fff; z-index: 1000;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08); box-sizing: border-box;
}
.top-left { display: flex; align-items: center; gap: 20px; margin-left: 20px; }
.logo-icon { width: 36px; height: 36px; flex: 0 0 36px; }
.logo-icon img { width: 100%; height: 100%; object-fit: contain; display: block; }
.hand { position: relative; cursor: pointer; margin-right: 50px; width: 60px; height: 50px; display: flex; align-items: center; justify-content: flex-end; color: white; padding: 0 10px; }
.avatar-img { width: 40px; height: 40px; border-radius: 50%; object-fit: cover; }
.top-title { font-size: 38px; font-weight: 700; color: #fff; }
.content-wrapper { display: flex; margin-top: 80px; height: calc(100vh - 80px); overflow: hidden; }
.el-aside { width: 220px; background: #fff; height: calc(100vh - 80px); box-sizing: border-box; padding: 16px; border-right: 1px solid #e9eef0; overflow: hidden; }
.sidebar-menu-wrapper { height: 100%; overflow: auto; padding-top: 6px; }
.el-menu-vertical-demo { background: transparent; border-right: none; width: 100%; }
.el-menu-vertical-demo .el-menu-item { display: flex; align-items: center; gap: 12px; padding: 10px 12px; border-radius: 8px; margin-bottom: 6px; transition: background .18s, color .18s; color: #333; }
.menu-icon { width: 36px; height: 36px; display: flex; align-items: center; justify-content: center; flex: 0 0 36px; }
.menu-text { font-size: 15px; font-weight: 700; }
.el-menu-vertical-demo .el-menu-item:hover { background: #f0faf4; color: #006B3E; }
.el-menu-vertical-demo .el-menu-item.is-active { background: #006B3E; color: #fff; }
.main-content { flex: 1; background: transparent; padding: 18px; height: 100%; overflow: auto; box-sizing: border-box; }
</style>
