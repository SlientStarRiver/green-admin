<template>
    <div class="main-content">
        <div class="main-inner">
            <div class="page-header">
                <div class="title">操作日志</div>
            </div>

            <div class="search-card">
                <el-form :inline="true" :model="filters" class="search-form">
                    <el-form-item label="操作人">
                        <el-input v-model="filters.username" placeholder="输入操作人" clearable @input="debounceSearch" />
                    </el-form-item>
                    <el-form-item label="模块">
                        <el-select v-model="filters.module" placeholder="全部" clearable @change="loadData">
                            <el-option label="区块管理" value="区块管理" />
                            <el-option label="植物管理" value="植物管理" />
                            <el-option label="用户管理" value="用户管理" />
                            <el-option label="种植记录" value="种植记录" />
                            <el-option label="养护记录" value="养护记录" />
                            <el-option label="系统" value="系统" />
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" :loading="loading" @click="loadData">查询</el-button>
                        <el-button @click="resetSearch">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>

            <div class="table-card" style="margin-top: 16px;">
                <el-table :data="tableData" stripe border style="width: 100%;" v-loading="loading"
                    empty-text="暂无数据" element-loading-text="加载中...">
                    <el-table-column prop="id" label="ID" width="80" sortable />
                    <el-table-column prop="username" label="操作人" width="120" />
                    <el-table-column prop="action" label="操作" width="120" />
                    <el-table-column prop="module" label="模块" width="120">
                        <template slot-scope="scope">
                            <el-tag size="small">{{ scope.row.module }}</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="detail" label="详情" show-overflow-tooltip />
                    <el-table-column prop="createTime" label="操作时间" width="180" sortable />
                </el-table>

                <div class="table-footer" v-if="total > 0">
                    <el-pagination background layout="total, prev, pager, next, jumper" :total="total" :page-size="pageSize" :current-page="currentPage" @current-change="handlePageChange" />
                </div>
                <div v-else class="empty-tip">
                    <i class="el-icon-document" style="font-size: 48px; color: #c0c4cc;"></i>
                    <p>暂无操作日志</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { getLogList } from '@/api/api/log'

export default {
    name: 'OperationLogPage',
    data() {
        return {
            filters: { username: '', module: '' },
            tableData: [],
            total: 0,
            currentPage: 1,
            pageSize: 15,
            loading: false,
            searchTimer: null
        }
    },
    created() {
        this.loadData()
    },
    beforeDestroy() {
        if (this.searchTimer) clearTimeout(this.searchTimer)
    },
    methods: {
        debounceSearch() {
            if (this.searchTimer) clearTimeout(this.searchTimer)
            this.searchTimer = setTimeout(() => {
                this.currentPage = 1
                this.loadData()
            }, 300)
        },
        async loadData() {
            this.loading = true
            try {
                const params = { page: this.currentPage, size: this.pageSize }
                if (this.filters.username) params.username = this.filters.username
                if (this.filters.module) params.module = this.filters.module
                const res = await getLogList(params)
                if (res.code === 200) {
                    this.tableData = res.data.records
                    this.total = res.data.totalPages * this.pageSize
                }
            } catch (e) {
                this.$message.error('加载失败')
            }
            this.loading = false
        },
        resetSearch() {
            this.filters = { username: '', module: '' }
            this.currentPage = 1
            this.loadData()
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
.page-header { display: flex; justify-content: flex-start; align-items: center; margin-bottom: 12px; }
.title { font-size: 22px; font-weight: 700; color: #2b6b48; }
.search-card { padding: 14px; background: #fff; border-radius: 8px; }
.table-card { padding: 12px; background: #fff; border-radius: 8px; }
.table-footer { display: flex; justify-content: center; padding-top: 14px; }
.empty-tip { text-align: center; padding: 40px 0; color: #909399; }
.empty-tip p { margin-top: 10px; font-size: 14px; }
</style>
