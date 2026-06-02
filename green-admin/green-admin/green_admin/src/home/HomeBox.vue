<template>
    <div>
        <div class="main-content">
            <el-main>
                <div class="dashboard">
                    <el-row :gutter="20">
                        <el-col :span="6">
                            <el-card class="stat-card">
                                <div class="stat-content">
                                    <h3>区块数</h3>
                                    <p class="stat-number">{{ stats.areaCount || 0 }}</p>
                                </div>
                            </el-card>
                        </el-col>
                        <el-col :span="6">
                            <el-card class="stat-card">
                                <div class="stat-content">
                                    <h3>植物品种</h3>
                                    <p class="stat-number">{{ stats.plantSpeciesCount || 0 }}</p>
                                </div>
                            </el-card>
                        </el-col>
                        <el-col :span="6">
                            <el-card class="stat-card">
                                <div class="stat-content">
                                    <h3>养护人员</h3>
                                    <p class="stat-number">{{ stats.maintainerCount || 0 }}</p>
                                </div>
                            </el-card>
                        </el-col>
                        <el-col :span="6">
                            <el-card class="stat-card">
                                <div class="stat-content">
                                    <h3>种植人员</h3>
                                    <p class="stat-number">{{ stats.planterCount || 0 }}</p>
                                </div>
                            </el-card>
                        </el-col>
                    </el-row>

                    <el-row :gutter="20" style="margin-top: 20px;">
                        <el-col :span="12">
                            <el-card class="bigbox">
                                <template #header>
                                    <div class="card-header"><span>近日活动列表</span></div>
                                </template>
                                <div>
                                    <div class="card-header11">种植</div>
                                    <div class="green">
                                        <ul>
                                            <li v-for="item in activities.plantingActivities" :key="'p' + item.id">
                                                {{ item.responsiblePerson }}在{{ item.areaName }}种植了{{ item.speciesName }}|{{ item.plantingTime }}
                                            </li>
                                            <li v-if="!activities.plantingActivities || activities.plantingActivities.length === 0">暂无活动</li>
                                        </ul>
                                    </div>
                                </div>
                                <div>
                                    <div class="card-header11">养护</div>
                                    <div class="green">
                                        <ul>
                                            <li v-for="item in activities.maintenanceActivities" :key="'m' + item.id">
                                                {{ item.maintainer }}在{{ item.areaName }}为{{ item.speciesName }}进行了养护|{{ item.maintenanceTime }}
                                            </li>
                                            <li v-if="!activities.maintenanceActivities || activities.maintenanceActivities.length === 0">暂无活动</li>
                                        </ul>
                                    </div>
                                </div>
                            </el-card>
                        </el-col>
                        <el-col :span="12">
                            <el-card class="smellbox">
                                <template #header>
                                    <div class="card-header"><span>种植/养护人</span></div>
                                </template>
                                <div class="chart-container">
                                    <div class="people-card">
                                        <div class="people-content">
                                            <div class="pie-placeholder">{{ stats.planterPercentage || 0 }}%</div>
                                            <div>
                                                <div class="card-header1">
                                                    <p><span class="legend-dot plant"></span> 养护人（{{ stats.maintainerPercentage || 0 }}%）</p>
                                                </div>
                                                <div class="card-header1">
                                                    <p><span class="legend-dot maintain"></span> 种植人（{{ stats.planterPercentage || 0 }}%）</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
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
    name: 'HomeBox',
    data() {
        return {
            stats: {},
            activities: {}
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        async loadData() {
            try {
                const [dashRes, actRes] = await Promise.all([getDashboard(), getRecentActivities()])
                if (dashRes.code === 200) this.stats = dashRes.data
                if (actRes.code === 200) this.activities = actRes.data
            } catch (e) {
                console.error('加载首页数据失败', e)
            }
        }
    }
}
</script>

<style scoped>
.main-content { flex: 1; padding: 18px; height: 100%; overflow: auto; box-sizing: border-box; background-color: #F3F4F6 !important; }
.dashboard { padding: 0; }
.stat-card { text-align: center; border-radius: 8px; box-shadow: 0 1px 4px rgba(16, 36, 19, 0.06); padding: 50px 5px; }
.card-header { color: #7f8b8a; font-size: 24px; font-weight: 700; }
.card-header11 { color: #006B3E; font-size: 24px; font-weight: 700; margin-bottom: 20px; }
.card-header1 { color: #006B3E; font-size: 23px; font-weight: 700; margin-bottom: 5px; }
.bigbox { width: 100%; max-width: 100%; box-sizing: border-box; }
.smellbox { width: 100%; max-width: 100%; box-sizing: border-box; margin-left: 0; height: 100%; }
.green { background-color: rgba(240, 250, 244, 0.81); padding: 8px; border-radius: 8px; margin-bottom: 16px; box-sizing: border-box; }
.green ul { margin: 0; padding-left: 20px; font-size: 18px; color: #3D3D3D; line-height: 1.6; }
.green ul li { margin: 6px; }
.stat-content h3 { color: #7f8b8a; font-size: 24px; margin-bottom: 8px; font-weight: 700; }
.stat-number { font-size: 24px; font-weight: 700; color: #0C642B; }
.el-card { border-radius: 8px; }
.people-card { border-radius: 8px; box-shadow: 0 1px 4px rgba(16, 36, 19, 0.06); padding: 16px; background-color: #fff; margin-bottom: 20px; }
.people-card .people-content { display: flex; align-items: center; gap: 18px; padding: 8px 4px; }
.people-card .pie-placeholder { width: 300px; height: 300px; border-radius: 50%; background: conic-gradient(#0C642B 0 75%, #e6f0e9 0 100%); box-shadow: 0 1px 3px rgba(0, 0, 0, 0.06); display: flex; align-items: center; justify-content: center; font-weight: 700; color: #ffffff; font-size: 40px; }
.legend-dot { width: 12px; height: 12px; border-radius: 50%; display: inline-block; }
.legend-dot.plant { background: #0C642B; }
.legend-dot.maintain { background: #8ec18b; }
</style>
