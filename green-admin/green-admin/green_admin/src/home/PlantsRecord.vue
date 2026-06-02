<template>
    <div class="main-content">
        <div class="main-inner">
            <div class="page-header">
                <div class="title">种植管理</div>
                <el-button type="success" class="btn-new" @click="handleAdd">新增记录</el-button>
            </div>

            <div class="search-card">
                <el-form :inline="true" :model="filters" class="search-form">
                    <el-form-item label="负责人">
                        <el-input v-model="filters.owner" placeholder="输入姓名查询" clearable />
                    </el-form-item>
                    <el-form-item label="植物品种">
                        <el-input v-model="filters.species" placeholder="输入品种查询" clearable />
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="loadData">查询</el-button>
                        <el-button @click="resetSearch">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>

            <div class="table-card">
                <el-table :data="tableData" stripe border style="width:100%" :empty-text="'暂无数据'" v-loading="loading">
                    <el-table-column prop="id" label="ID" width="60" />
                    <el-table-column prop="areaName" label="区块名" />
                    <el-table-column prop="speciesName" label="植物品种" />
                    <el-table-column prop="responsiblePerson" label="负责人" />
                    <el-table-column prop="plantingTime" label="种植时间" />
                    <el-table-column prop="plantCount" label="株数" width="80" />
                    <el-table-column prop="description" label="描述" show-overflow-tooltip />
                    <el-table-column label="操作" width="160" fixed="right">
                        <template slot-scope="scope">
                            <el-button size="mini" type="primary" icon="el-icon-edit" @click="handleEdit(scope.row)">编辑</el-button>
                            <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)" style="margin-left:8px;">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <div class="table-footer" v-if="total > 0">
                    <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page="currentPage" @current-change="handlePageChange" />
                </div>
            </div>

            <el-dialog :title="isEdit ? '编辑种植记录' : '新增种植记录'" :visible.sync="dialogVisible" width="600px">
                <el-form :model="editForm" label-width="100px">
                    <el-form-item label="区块编号">
                        <el-input v-model="editForm.areaId" placeholder="如 QK001" />
                    </el-form-item>
                    <el-form-item label="品种编号">
                        <el-input v-model="editForm.speciesId" placeholder="如 ZW001" />
                    </el-form-item>
                    <el-form-item label="种植时间">
                        <el-date-picker v-model="editForm.plantingTime" type="datetime" placeholder="选择时间" style="width:100%" />
                    </el-form-item>
                    <el-form-item label="株数">
                        <el-input-number v-model="editForm.plantCount" :min="1" />
                    </el-form-item>
                    <el-form-item label="负责人">
                        <el-input v-model="editForm.responsiblePerson" />
                    </el-form-item>
                    <el-form-item label="描述">
                        <el-input type="textarea" v-model="editForm.description" rows="3" />
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
import { getPlantingList, createPlanting, updatePlanting, deletePlanting } from '@/api/api/planting'

export default {
    name: 'PlantsRecord',
    data() {
        return {
            filters: { owner: '', species: '' },
            tableData: [],
            total: 0,
            currentPage: 1,
            pageSize: 10,
            loading: false,
            dialogVisible: false,
            isEdit: false,
            editForm: {}
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
                const res = await getPlantingList(params)
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
            this.filters = { owner: '', species: '' }
            this.currentPage = 1
            this.loadData()
        },
        handlePageChange(page) {
            this.currentPage = page
            this.loadData()
        },
        handleAdd() {
            this.isEdit = false
            this.editForm = { areaId: '', speciesId: '', plantingTime: '', plantCount: 1, responsiblePerson: '', description: '' }
            this.dialogVisible = true
        },
        handleEdit(row) {
            this.isEdit = true
            this.editForm = { ...row }
            this.dialogVisible = true
        },
        handleDelete(row) {
            this.$confirm('确定删除该记录吗？', '删除确认', { type: 'warning' }).then(async () => {
                const res = await deletePlanting(row.id)
                if (res.code === 200) {
                    this.$message.success('删除成功')
                    this.loadData()
                } else {
                    this.$message.error(res.message)
                }
            }).catch(() => {})
        },
        async saveItem() {
            let res
            if (this.isEdit) {
                res = await updatePlanting(this.editForm.id, this.editForm)
            } else {
                res = await createPlanting(this.editForm)
            }
            if (res.code === 200) {
                this.$message.success(this.isEdit ? '更新成功' : '创建成功')
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
.main-content { flex: 1; padding: 18px; height: 100%; overflow: auto; box-sizing: border-box; background-color: #F3F4F6 !important; }
.page-header { display: flex; justify-content: flex-start; align-items: center; }
.title { font-size: 22px; font-weight: 700; color: #2b6b48; margin-bottom: 8px; }
.btn-new { border-radius: 6px; background-color: #0C642B; margin-left: auto; }
.search-card { padding: 14px; background: #fff; border-radius: 8px; }
.table-card { margin-top: 8px; padding: 12px; background: #fff; border-radius: 8px; width: 100%; }
.table-footer { display: flex; justify-content: center; padding-top: 12px; }
.dialog-footer { text-align: right; }
</style>
