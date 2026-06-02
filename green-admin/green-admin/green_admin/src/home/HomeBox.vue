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

                    <el-row :gutter="20" style="margin-top: 20px; align-items: stretch;">
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
                                                {{ item.responsiblePerson }}在{{ item.areaName }}种植了{{ item.speciesName }}
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
                                                {{ item.maintainer }}在{{ item.areaName }}为{{ item.speciesName }}进行了养护
                                            </li>
                                            <li v-if="!activities.maintenanceActivities || activities.maintenanceActivities.length === 0">暂无活动</li>
                                        </ul>
                                    </div>
                                </div>
                            </el-card>
                        </el-col>
                        <el-col :span="12">
                            <el-card class="bigbox">
                                <template #header>
                                    <div class="card-header"><span>种植/养护统计</span></div>
                                </template>
                                <div ref="barChart" style="width: 100%; height: 100%; min-height: 300px;"></div>
                            </el-card>
                        </el-col>
                    </el-row>

                    <el-row :gutter="20" style="margin-top: 20px;">
                        <el-col :span="12">
                            <el-card class="chart-card">
                                <template #header>
                                    <div class="card-header"><span>植物品种分布</span></div>
                                </template>
                                <div ref="pieChart" style="width: 100%; height: 300px;"></div>
                            </el-card>
                        </el-col>
                        <el-col :span="12">
                            <el-card class="chart-card">
                                <template #header>
                                    <div class="card-header"><span>种植/养护人比例</span></div>
                                </template>
                                <div ref="ratioChart" style="width: 100%; height: 300px;"></div>
                            </el-card>
                        </el-col>
                    </el-row>
                </div>
            </el-main>
        </div>
    </div>
</template>

<script>
import * as echarts from 'echarts'
import { getDashboard, getRecentActivities, getPlantDistribution } from '@/api/api/stats'

export default {
    name: 'HomeBox',
    data() {
        return {
            stats: {},
            activities: {},
            distribution: []
        }
    },
    mounted() {
        this.loadData()
    },
    methods: {
        async loadData() {
            try {
                const [dashRes, actRes, distRes] = await Promise.all([
                    getDashboard(), getRecentActivities(), getPlantDistribution()
                ])
                if (dashRes.code === 200) this.stats = dashRes.data
                if (actRes.code === 200) this.activities = actRes.data
                if (distRes.code === 200) this.distribution = distRes.data

                this.$nextTick(() => {
                    this.initBarChart()
                    this.initPieChart()
                    this.initRatioChart()
                })
            } catch (e) {
                console.error('加载首页数据失败', e)
            }
        },
        initBarChart() {
            const chart = echarts.init(this.$refs.barChart)
            const planting = this.activities.plantingActivities || []
            const maintenance = this.activities.maintenanceActivities || []

            // 按人员统计种植次数
            const planterMap = {}
            planting.forEach(r => {
                planterMap[r.responsiblePerson] = (planterMap[r.responsiblePerson] || 0) + 1
            })
            const maintainerMap = {}
            maintenance.forEach(r => {
                maintainerMap[r.maintainer] = (maintainerMap[r.maintainer] || 0) + 1
            })

            const allNames = [...new Set([...Object.keys(planterMap), ...Object.keys(maintainerMap)])]

            chart.setOption({
                tooltip: { trigger: 'axis' },
                legend: { data: ['种植', '养护'] },
                xAxis: { type: 'category', data: allNames },
                yAxis: { type: 'value', minInterval: 1 },
                series: [
                    { name: '种植', type: 'bar', data: allNames.map(n => planterMap[n] || 0), itemStyle: { color: '#0C642B' } },
                    { name: '养护', type: 'bar', data: allNames.map(n => maintainerMap[n] || 0), itemStyle: { color: '#8ec18b' } }
                ]
            })
            window.addEventListener('resize', () => chart.resize())
        },
        initPieChart() {
            const chart = echarts.init(this.$refs.pieChart)
            const data = this.distribution.map(d => ({
                name: d.areaName,
                value: d.speciesCount
            })).filter(d => d.value > 0)

            chart.setOption({
                tooltip: { trigger: 'item', formatter: '{b}: {c}种 ({d}%)' },
                series: [{
                    type: 'pie',
                    radius: ['40%', '70%'],
                    data: data.length ? data : [{ name: '暂无数据', value: 1 }],
                    emphasis: { itemStyle: { shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)' } },
                    color: ['#0C642B', '#2E8B57', '#3CB371', '#66CDAA', '#8FBC8F', '#98FB98']
                }]
            })
            window.addEventListener('resize', () => chart.resize())
        },
        initRatioChart() {
            const chart = echarts.init(this.$refs.ratioChart)
            chart.setOption({
                tooltip: { trigger: 'item', formatter: '{b}: {c}人 ({d}%)' },
                series: [{
                    type: 'pie',
                    radius: ['40%', '70%'],
                    data: [
                        { name: '种植人员', value: this.stats.planterCount || 0 },
                        { name: '养护人员', value: this.stats.maintainerCount || 0 }
                    ],
                    emphasis: { itemStyle: { shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)' } },
                    color: ['#0C642B', '#8ec18b']
                }]
            })
            window.addEventListener('resize', () => chart.resize())
        }
    }
}
</script>

<style scoped>
.main-content { flex: 1; padding: 18px; height: 100%; overflow: auto; box-sizing: border-box; background-color: #F3F4F6 !important; }
.dashboard { padding: 0; }
.stat-card { text-align: center; border-radius: 8px; box-shadow: 0 1px 4px rgba(16, 36, 19, 0.06); padding: 50px 5px; }
.card-header { color: #7f8b8a; font-size: 20px; font-weight: 700; }
.card-header11 { color: #006B3E; font-size: 20px; font-weight: 700; margin-bottom: 12px; }
.bigbox { width: 100%; box-sizing: border-box; height: 100%; }
.chart-card { width: 100%; box-sizing: border-box; height: 100%; }
.green { background-color: rgba(240, 250, 244, 0.81); padding: 8px; border-radius: 8px; margin-bottom: 12px; }
.green ul { margin: 0; padding-left: 20px; font-size: 14px; color: #3D3D3D; line-height: 1.6; }
.green ul li { margin: 4px 0; }
.stat-content h3 { color: #7f8b8a; font-size: 20px; margin-bottom: 8px; font-weight: 700; }
.stat-number { font-size: 28px; font-weight: 700; color: #0C642B; }
.el-card { border-radius: 8px; }
</style>
