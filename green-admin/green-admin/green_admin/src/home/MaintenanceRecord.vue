<template>
    <div class="main-content">
        <div class="main-inner">
            <div class="page-header">
                <div class="title">养护管理</div>
            </div>

            <div class="search-card">
                <el-form :inline="true" :model="filters" class="search-form">
                    <el-form-item label="养护人">
                        <el-input v-model="filters.owner" placeholder="输入姓名查询" clearable />
                    </el-form-item>

                    <el-form-item label="植物品种">
                        <el-input v-model="filters.species" placeholder="输入植物品种查询" clearable />
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="applySearch">查询</el-button>
                        <el-button @click="resetSearch">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>

            <div class="table-card">
                <el-table :data="pagedData" stripe border style="width:100%" :empty-text="'暂无数据'">
                    <el-table-column prop="owner" label="养护人" />
                    <el-table-column prop="block" label="区块名" />
                    <el-table-column prop="species" label="植物品种" />
                    <el-table-column prop="maintainDate" label="养护时间" />
                    <el-table-column prop="count" label="株树" width="100" />
                    <el-table-column prop="note" label="操作描述" />
                </el-table>

                <div class="table-footer" v-if="total > 0">
                    <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize"
                        :current-page="currentPage" @current-change="handlePageChange" />
                </div>

                <div v-else class="no-data-tip">无匹配结果</div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'MaintenanceRecord',
    data() {
        return {
            filters: { owner: '', species: '' },
            records: [
                { id: 1, owner: '王明', block: 'qk01', species: '黄杨', maintainDate: '2025-08-03', count: 5, note: '无' },
                { id: 2, owner: '王明', block: 'qk01', species: '黄杨', maintainDate: '2025-08-03', count: 5, note: '无' },
                { id: 3, owner: '王明', block: 'qk01', species: '黄杨', maintainDate: '2025-08-03', count: 5, note: '无' },
                { id: 4, owner: '王明', block: 'qk01', species: '黄杨', maintainDate: '2025-08-03', count: 5, note: '无' },
                { id: 5, owner: '王明', block: 'qk01', species: '黄杨', maintainDate: '2025-08-03', count: 5, note: '无' },
                { id: 6, owner: '王明', block: 'qk01', species: '黄杨', maintainDate: '2025-08-03', count: 5, note: '无' },
                { id: 7, owner: '王明', block: 'qk01', species: '黄杨', maintainDate: '2025-08-03', count: 5, note: '无' }
            ],
            currentPage: 1,
            pageSize: 6
        }
    },
    computed: {
        filtered() {
            const owner = (this.filters.owner || '').trim().toLowerCase()
            const species = (this.filters.species || '').trim().toLowerCase()
            return this.records.filter(r => {
                return (!owner || (r.owner || '').toLowerCase().includes(owner)) &&
                    (!species || (r.species || '').toLowerCase().includes(species))
            })
        },
        total() { return this.filtered.length },
        pagedData() {
            const start = (this.currentPage - 1) * this.pageSize
            return this.filtered.slice(start, start + this.pageSize)
        }
    },
    methods: {
        applySearch() { this.currentPage = 1 },
        resetSearch() { this.filters.owner = ''; this.filters.species = ''; this.currentPage = 1 },
        handlePageChange(page) { this.currentPage = page }
    }
}
</script>

<style scoped>
.main-content {
    flex: 1;
    padding: 18px;
    height: 100%;
    overflow: auto;
    box-sizing: border-box;
    background-color: #F3F4F6 !important;
}

.main-inner {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.page-header {
    display: flex;
    justify-content: flex-start;
    align-items: center;
}

.title {
    font-size: 22px;
    font-weight: 700;
    color: #2b6b48;
    margin-bottom: 8px;
}

.search-card {
    padding: 14px;
    background: #fff;
    border-radius: 8px;
}

.search-form .el-form-item {
    margin-right: 12px;
}

.table-card {
    margin-top: 8px;
    padding: 12px;
    background: #fff;
    border-radius: 8px;
    box-sizing: border-box;
    width: 100%;
}

/* 表格撑满并平分列宽 */
.main-content ::v-deep .el-table {
    width: 100% !important;
    table-layout: fixed !important;
}

/* 单元格样式 */
.main-content ::v-deep .el-table th,
.main-content ::v-deep .el-table td,
.main-content ::v-deep .el-table .cell {
    padding: 12px 16px !important;
    color: #333 !important;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    height: 48px;
    line-height: 24px;

    /* 横向居中 */
    text-align: center !important;
}

/* 使用 flex 保证单元格内部内容（如按钮、图标等）也居中 */
.main-content ::v-deep .el-table th .cell,
.main-content ::v-deep .el-table td .cell {
  display: flex !important;
  justify-content: center !important;
  align-items: center !important;
}

/* 翻页居中 */
.table-footer {
    display: flex;
    justify-content: center;
    padding-top: 12px;
}

/* 响应式 */
@media (max-width: 900px) {
    .title {
        font-size: 18px;
    }

    .search-card {
        padding: 10px;
    }
}
</style>