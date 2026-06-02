<template>
    <div class="main-content">
        <div class="main-inner">
            <div class="page-header">
                <div class="title">用户信息管理</div>
            </div>

            <div class="table-card">
                <el-table :data="tableData" stripe border style="width:100%" :empty-text="'暂无数据'" v-loading="loading">
                    <el-table-column prop="id" label="用户ID" />
                    <el-table-column prop="username" label="用户名" />
                    <el-table-column prop="phone" label="手机号" />
                    <el-table-column prop="role" label="角色" />
                    <el-table-column prop="registerTime" label="注册时间" />
                    <el-table-column prop="maintenanceCount" label="养护次数" />
                    <el-table-column prop="plantingCount" label="种植次数" />
                </el-table>

                <div class="table-footer" v-if="total > 0">
                    <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page="currentPage" @current-change="handlePageChange" />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { getUserList } from '@/api/api/user'

export default {
    name: 'UserBox',
    data() {
        return {
            tableData: [],
            total: 0,
            currentPage: 1,
            pageSize: 10,
            loading: false
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        async loadData() {
            this.loading = true
            try {
                const res = await getUserList({ page: this.currentPage, size: this.pageSize })
                if (res.code === 200) {
                    this.tableData = res.data.records
                    this.total = res.data.totalPages * this.pageSize
                }
            } catch (e) {
                this.$message.error('加载失败')
            }
            this.loading = false
        },
        handlePageChange(page) {
            this.currentPage = page
            this.loadData()
        }
    }
}
</script>

<style scoped>
.main-content { flex: 1; padding: 18px; height: 100%; overflow: auto; box-sizing: border-box; background-color: #F3F4F6 !important; }
.page-header { display: flex; justify-content: flex-start; align-items: center; margin-bottom: 8px; }
.title { font-size: 22px; font-weight: 700; color: #2b6b48; }
.table-card { margin-top: 8px; padding: 12px; background: #fff; border-radius: 8px; width: 100%; }
.table-footer { display: flex; justify-content: center; padding-top: 12px; }
</style>
