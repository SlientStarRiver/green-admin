<template>
    <div class="main-content">
        <div class="main-inner">
            <div class="page-header">
                <div class="title">区块管理</div>
                <el-button type="success" class="btn-new" @click="handleAdd">新建区块</el-button>
            </div>

            <div class="search-card">
                <el-form :inline="true" :model="filters" class="search-form">
                    <el-form-item label="区块名称">
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
                    <el-table-column prop="areaId" label="区块编号" width="160" sortable />
                    <el-table-column prop="areaName" label="区块名" sortable />
                    <el-table-column prop="areaSize" label="面积/m²" width="120" sortable />
                    <el-table-column prop="locationDescription" label="位置描述" show-overflow-tooltip />
                    <el-table-column label="操作" width="160" fixed="right">
                        <template slot-scope="scope">
                            <el-button size="mini" type="primary" icon="el-icon-edit" :loading="scope.row._editing" @click="handleEdit(scope.row)">编辑</el-button>
                            <el-button size="mini" type="danger" icon="el-icon-delete" :loading="scope.row._deleting" @click="handleDelete(scope.row)" style="margin-left:8px;">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <div class="table-footer" v-if="total > 0">
                    <el-pagination background layout="total, prev, pager, next, jumper" :total="total" :page-size="pageSize" :current-page="currentPage" @current-change="handlePageChange" />
                </div>
                <div v-else class="empty-tip">
                    <i class="el-icon-folder-opened" style="font-size: 48px; color: #c0c4cc;"></i>
                    <p>暂无区块数据</p>
                </div>
            </div>

            <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="600px" :close-on-click-modal="false">
                <el-form :model="editForm" :rules="formRules" ref="editFormRef" label-width="80px">
                    <el-form-item label="区块号" prop="areaId">
                        <el-input v-model="editForm.areaId" :disabled="isEdit" placeholder="如 QK006" />
                    </el-form-item>
                    <el-form-item label="区块名" prop="areaName">
                        <el-input v-model="editForm.areaName" placeholder="请输入区块名" />
                    </el-form-item>
                    <el-form-item label="面积" prop="areaSize">
                        <el-input-number v-model="editForm.areaSize" :min="1" :precision="2" style="width: 200px;" />
                    </el-form-item>
                    <el-form-item label="位置描述">
                        <el-input v-model="editForm.locationDescription" type="textarea" :rows="2" placeholder="请输入位置描述" />
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
import { getAreaList, createArea, updateArea, deleteArea } from '@/api/api/area'

export default {
    name: 'BlockBox',
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
            isEdit: false,
            editForm: { areaId: '', areaName: '', areaSize: '', locationDescription: '' },
            formRules: {
                areaId: [{ required: true, message: '请输入区块号', trigger: 'blur' }],
                areaName: [{ required: true, message: '请输入区块名', trigger: 'blur' }],
                areaSize: [{ required: true, message: '请输入面积', trigger: 'blur' }]
            },
            searchTimer: null
        }
    },
    computed: {
        dialogTitle() {
            return this.isEdit ? '编辑区块' : '新建区块'
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
                if (this.filters.name) params.areaName = this.filters.name
                const res = await getAreaList(params)
                if (res.code === 200) {
                    this.tableData = res.data.records.map(r => ({ ...r, _editing: false, _deleting: false }))
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
            this.isEdit = false
            this.editForm = { areaId: '', areaName: '', areaSize: '', locationDescription: '' }
            this.dialogVisible = true
            this.$nextTick(() => this.$refs.editFormRef && this.$refs.editFormRef.clearValidate())
        },
        handleEdit(row) {
            this.isEdit = true
            this.editForm = { ...row }
            this.dialogVisible = true
            this.$nextTick(() => this.$refs.editFormRef && this.$refs.editFormRef.clearValidate())
        },
        async handleDelete(row) {
            try {
                await this.$confirm(`确定删除区块 "${row.areaName}" 吗？`, '删除确认', {
                    confirmButtonText: '删除', cancelButtonText: '取消', type: 'warning'
                })
                this.$set(row, '_deleting', true)
                const res = await deleteArea(row.areaId)
                if (res.code === 200) {
                    this.$message.success('删除成功')
                    this.loadData()
                } else {
                    this.$message.error(res.message)
                }
            } catch (e) {
                if (e !== 'cancel') this.$message.error('删除失败')
            }
            this.$set(row, '_deleting', false)
        },
        async saveItem() {
            try {
                await this.$refs.editFormRef.validate()
            } catch (e) {
                return
            }
            this.saving = true
            try {
                let res
                if (this.isEdit) {
                    res = await updateArea(this.editForm.areaId, this.editForm)
                } else {
                    res = await createArea(this.editForm)
                }
                if (res.code === 200) {
                    this.$message.success(this.isEdit ? '更新成功' : '创建成功')
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
