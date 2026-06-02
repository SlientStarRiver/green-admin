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
                        <el-input v-model="filters.name" placeholder="输入名称查询" clearable @input="debounceSearch" />
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
                    <el-table-column prop="speciesId" label="品种编号" width="160" sortable />
                    <el-table-column prop="speciesName" label="品种名" sortable />
                    <el-table-column prop="createdTime" label="创建时间" sortable />
                </el-table>

                <div class="table-footer" v-if="total > 0">
                    <el-pagination background layout="total, prev, pager, next, jumper" :total="total" :page-size="pageSize" :current-page="currentPage" @current-change="handlePageChange" />
                </div>
                <div v-else class="empty-tip">
                    <i class="el-icon-folder-opened" style="font-size: 48px; color: #c0c4cc;"></i>
                    <p>暂无植物数据</p>
                </div>
            </div>

            <el-dialog title="新增植物品种" :visible.sync="dialogVisible" width="500px" :close-on-click-modal="false">
                <el-form :model="editForm" :rules="formRules" ref="editFormRef" label-width="80px">
                    <el-form-item label="品种编号" prop="speciesId">
                        <el-input v-model="editForm.speciesId" placeholder="如 ZW008" />
                    </el-form-item>
                    <el-form-item label="品种名称" prop="speciesName">
                        <el-input v-model="editForm.speciesName" placeholder="请输入品种名称" />
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" :loading="saving" @click="saveItem">保存</el-button>
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
            saving: false,
            dialogVisible: false,
            editForm: { speciesId: '', speciesName: '' },
            formRules: {
                speciesId: [{ required: true, message: '请输入品种编号', trigger: 'blur' }],
                speciesName: [{ required: true, message: '请输入品种名称', trigger: 'blur' }]
            },
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
            this.$nextTick(() => this.$refs.editFormRef && this.$refs.editFormRef.clearValidate())
        },
        async saveItem() {
            try {
                await this.$refs.editFormRef.validate()
            } catch (e) {
                return
            }
            this.saving = true
            try {
                const res = await createSpecies(this.editForm)
                if (res.code === 200) {
                    this.$message.success('创建成功')
                    this.dialogVisible = false
                    this.loadData()
                } else {
                    this.$message.error(res.message)
                }
            } catch (e) {
                this.$message.error('操作失败')
            }
            this.saving = false
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
.empty-tip { text-align: center; padding: 40px 0; color: #909399; }
.empty-tip p { margin-top: 10px; font-size: 14px; }
</style>
