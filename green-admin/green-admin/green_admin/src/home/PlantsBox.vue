<template>
    <div class="main-content">
        <div class="main-inner">
            <div class="page-header">
                <div class="title">植物管理</div>
                <el-button type="success" class="btn-new" @click="handleAdd">新增植物</el-button>
            </div>

            <div class="search-card">
                <el-form :inline="true" :model="filters" class="search-form">
                    <el-form-item label="植物名称">
                        <el-input v-model="filters.name" placeholder="输入名称查询" clearable />
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="loadData">查询</el-button>
                        <el-button @click="resetSearch">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>

            <div class="table-card" style="margin-top: 16px;">
                <el-table :data="tableData" stripe border style="width: 100%;" :empty-text="'暂无数据'" v-loading="loading">
                    <el-table-column prop="speciesId" label="品种编号" width="160" />
                    <el-table-column prop="speciesName" label="品种名" />
                    <el-table-column prop="createdTime" label="创建时间" />
                </el-table>

                <div class="table-footer" v-if="total > 0">
                    <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page="currentPage" @current-change="handlePageChange" />
                </div>
            </div>

            <el-dialog title="新增植物品种" :visible.sync="dialogVisible" width="500px">
                <el-form :model="editForm" label-width="80px">
                    <el-form-item label="品种编号">
                        <el-input v-model="editForm.speciesId" placeholder="如 ZW008" />
                    </el-form-item>
                    <el-form-item label="品种名称">
                        <el-input v-model="editForm.speciesName" placeholder="请输入品种名称" />
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="saveItem">保存</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import { getSpeciesList, createSpecies } from '@/api/api/species'

export default {
    name: 'PlantsBox',
    data() {
        return {
            filters: { name: '' },
            tableData: [],
            total: 0,
            currentPage: 1,
            pageSize: 10,
            loading: false,
            dialogVisible: false,
            editForm: { speciesId: '', speciesName: '' }
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        async loadData() {
            this.loading = true
            try {
                const params = { page: this.currentPage, size: this.pageSize }
                if (this.filters.name) params.speciesName = this.filters.name
                const res = await getSpeciesList(params)
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
            this.filters.name = ''
            this.currentPage = 1
            this.loadData()
        },
        handlePageChange(page) {
            this.currentPage = page
            this.loadData()
        },
        handleAdd() {
            this.editForm = { speciesId: '', speciesName: '' }
            this.dialogVisible = true
        },
        async saveItem() {
            if (!this.editForm.speciesId || !this.editForm.speciesName) {
                this.$message.warning('请填写必填项')
                return
            }
            const res = await createSpecies(this.editForm)
            if (res.code === 200) {
                this.$message.success('创建成功')
                this.dialogVisible = false
                this.loadData()
            } else {
                this.$message.error(res.message)
            }
        }
    }
}
</script>

<style scoped>
.main-content { flex: 1; padding: 18px; height: 100%; overflow: auto; box-sizing: border-box; }
.page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px; }
.title { font-size: 22px; font-weight: 700; color: #2b6b48; }
.btn-new { border-radius: 6px; background-color: #0C642B; }
.search-card { padding: 14px; background: #fff; border-radius: 8px; }
.table-card { padding: 12px; background: #fff; border-radius: 8px; }
.table-footer { display: flex; justify-content: center; padding-top: 14px; }
.dialog-footer { text-align: right; }
</style>
