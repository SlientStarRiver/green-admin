<template>
  <div class="root-container">
    <div class="main-content">
      <el-main class="full-height-main">
        <div class="dashboard full-height-dashboard">
          <el-row :gutter="20" class="stat-row">
            <el-col :span="6">
              <el-card class="stat-card">
                <div class="stat-content">
                  <h3>区块数</h3>
                  <p class="stat-number">{{ dashboardData.areaCount || 0 }}</p>
                </div>
              </el-card>
            </el-col>
            <el-col :span="6">
              <el-card class="stat-card">
                <div class="stat-content">
                  <h3>植物种类数</h3>
                  <p class="stat-number">{{ dashboardData.plantSpeciesCount || 0 }}</p>
                </div>
              </el-card>
            </el-col>
            <el-col :span="6">
              <el-card class="stat-card">
                <div class="stat-content">
                  <h3>本月养护</h3>
                  <p class="stat-number">{{ dashboardData.monthlyMaintenanceCount || 0 }}</p>
                </div>
              </el-card>
            </el-col>
            <el-col :span="6">
              <el-card class="stat-card">
                <div class="stat-content">
                  <h3>本月种植</h3>
                  <p class="stat-number">{{ dashboardData.monthlyPlantingCount || 0 }}</p>
                </div>
              </el-card>
            </el-col>
          </el-row>

          <el-row :gutter="20" class="activity-row">
            <el-col :span="24">
              <el-card class="activity-card">
                <template #header>
                  <div class="card-header"><span>近日活动列表</span></div>
                </template>
                <el-row :gutter="20" class="activity-inner-row">
                  <el-col :span="12">
                    <div class="activity-column">
                      <div class="card-header11">种植</div>
                      <div class="green">
                        <ul v-if="recentActivities.plantingActivities && recentActivities.plantingActivities.length">
                          <li v-for="item in recentActivities.plantingActivities" :key="item.id">
                            {{ item.responsiblePerson }}在{{ item.areaName }}种植了{{ item.speciesName }}|{{ formatTime(item.plantingTime) }}
                          </li>
                        </ul>
                        <ul v-else><li>暂无种植活动数据</li></ul>
                      </div>
                    </div>
                  </el-col>
                  <el-col :span="12">
                    <div class="activity-column">
                      <div class="card-header11">养护</div>
                      <div class="green">
                        <ul v-if="recentActivities.maintenanceActivities && recentActivities.maintenanceActivities.length">
                          <li v-for="item in recentActivities.maintenanceActivities" :key="item.id">
                            {{ item.maintainer }}在{{ item.areaName }}为{{ item.speciesName }}进行了养护|{{ formatTime(item.maintenanceTime) }}
                          </li>
                        </ul>
                        <ul v-else><li>暂无养护活动数据</li></ul>
                      </div>
                    </div>
                  </el-col>
                </el-row>
              </el-card>
            </el-col>
          </el-row>
        </div>
      </el-main>
    </div>
  </div>
</template>

<script>
import { getDashboard, getRecentActivities } from '@/api/api/stats'

export default {
  name: 'UserHomeBox',
  data() {
    return {
      dashboardData: {},
      recentActivities: {}
    }
  },
  mounted() {
    this.loadData()
  },
  methods: {
    async loadData() {
      try {
        const [dashRes, actRes] = await Promise.all([getDashboard(), getRecentActivities()])
        if (dashRes.code === 200) this.dashboardData = dashRes.data
        if (actRes.code === 200) this.recentActivities = actRes.data
      } catch (e) {
        console.error('加载数据失败', e)
      }
    },
    formatTime(timeStr) {
      if (!timeStr) return ''
      const date = new Date(timeStr)
      const year = date.getFullYear()
      const month = (date.getMonth() + 1).toString().padStart(2, '0')
      const day = date.getDate().toString().padStart(2, '0')
      return `${year}-${month}-${day}`
    }
  }
}
</script>

<style scoped>
.root-container { width: 100%; height: 100vh; overflow: hidden; }
.main-content { width: 100%; height: 100%; padding: 18px; box-sizing: border-box; background-color: #F3F4F6 !important; overflow: auto; }
.full-height-main { width: 100%; height: 100%; padding: 0 !important; margin: 0 !important; }
.full-height-dashboard { width: 100%; height: 100%; display: flex; flex-direction: column; gap: 20px; padding: 0; }
.stat-row { width: 100%; }
.activity-row { width: 100%; flex: 1; margin: 0 !important; }
.stat-card { text-align: center; border-radius: 8px; box-shadow: 0 1px 4px rgba(16, 36, 19, 0.06); padding: 50px 5px; height: 100%; }
.card-header { color: #7f8b8a; font-size: 24px; font-weight: 700; }
.card-header11 { color: #006B3E; font-size: 24px; font-weight: 700; margin-bottom: 20px; }
.activity-card { width: 100%; height: 100%; max-width: 100%; box-sizing: border-box; display: flex; flex-direction: column; }
.activity-inner-row { width: 100%; flex: 1; }
.activity-column { height: 100%; box-sizing: border-box; display: flex; flex-direction: column; }
.green { background-color: rgba(240, 250, 244, 0.81); padding: 8px; border-radius: 8px; box-sizing: border-box; flex: 1; min-height: 200px; }
.green ul { margin: 0; padding-left: 20px; font-size: 18px; color: #3D3D3D; line-height: 1.6; height: 100%; list-style: none; }
.green ul li { margin: 6px 0; padding: 4px 0; border-bottom: 1px solid #e9eef0; }
.green ul li:last-child { border-bottom: none; }
.stat-content h3 { color: #7f8b8a; font-size: 24px; margin-bottom: 8px; font-weight: 700; }
.stat-number { font-size: 24px; font-weight: 700; color: #0C642B; }
.el-card { border-radius: 8px; height: 100%; }
</style>
