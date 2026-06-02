<template>
    <div class="main-content">
        <div class="main-inner">
            <div class="page-header">
                <div class="title">用户信息管理</div>
            </div>

            <div class="table-card">
                <el-table :data="tableData" stripe border style="width:100%" v-loading="loading"
                    empty-text="暂无数据" element-loading-text="加载中...">
                    <el-table-column prop="id" label="用户ID" width="80" sortable />
                    <el-table-column prop="username" label="用户名" sortable />
                    <el-table-column prop="phone" label="手机号" />
                    <el-table-column prop="role" label="角色" width="100" sortable>
                        <template slot-scope="scope">
                            <el-tag :type="scope.row.role === 'ADMIN' ? 'danger' : 'success'" size="small">
                                {{ scope.row.role === 'ADMIN' ? '管理员' : '用户' }}
                            </el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="registerTime" label="注册时间" sortable />
                    <el-table-column prop="maintenanceCount" label="养护次数" width="100" sortable />
                    <el-table-column prop="plantingCount" label="种植次数" width="100" sortable />
                    <el-table-column label="操作" width="160" fixed="right">
                        <template slot-scope="scope">
                            <el-button size="mini" type="primary" icon="el-icon-edit" @click="handleEdit(scope.row)">编辑</el-button>
                            <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)" style="margin-left:8px;">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <div class="table-footer" v-if="total > 0">
                    <el-pagination background layout="total, prev, pager, next, jumper" :total="total" :page-size="pageSize" :current-page="currentPage" @current-change="handlePageChange" />
                </div>
                <div v-else class="empty-tip">
                    <i class="el-icon-user" style="font-size: 48px; color: #c0c4cc;"></i>
                    <p>暂无用户数据</p>
                </div>
            </div>

            <el-dialog title="编辑用户" :visible.sync="dialogVisible" width="500px" :close-on-click-modal="false">
                <el-form :model="editForm" :rules="formRules" ref="editFormRef" label-width="80px">
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="editForm.username" disabled />
                    </el-form-item>
                    <el-form-item label="手机号" prop="phone">
                        <el-input v-model="editForm.phone" placeholder="请输入手机号" />
                    </el-form-item>
                    <el-form-item label="角色" prop="role">
                        <el-select v-model="editForm.role" style="width: 100%;">
                            <el-option label="管理员" value="ADMIN" />
                            <el-option label="用户" value="USER" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="养护次数">
                        <el-input-number v-model="editForm.maintenanceCount" :min="0" />
                    </el-form-item>
                    <el-form-item label="种植次数">
                        <el-input-number v-model="editForm.plantingCount" :min="0" />
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
import { getUserList, updateUser, deleteUser } from '@/api/api/user'

export default {
    name: 'UserBox',
    data() {
        return {
            tableData: [],
            total: 0,
            currentPage: 1,
            pageSize: 10,
            loading: false,
            saving: false,
            dialogVisible: false,
            editForm: {},
            formRules: {
                phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
                role: [{ required: true, message: '请选择角色', trigger: 'change' }]
            }
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
        },
        handleEdit(row) {
            this.editForm = { ...row }
            this.dialogVisible = true
            this.$nextTick(() => this.$refs.editFormRef && this.$refs.editFormRef.clearValidate())
        },
        async handleDelete(row) {
            try {
                await this.$confirm(`确定删除用户 "${row.username}" 吗？`, '删除确认', {
                    confirmButtonText: '删除', cancelButtonText: '取消', type: 'warning'
                })
                const res = await deleteUser(row.id)
                if (res.code === 200) {
                    this.$message.success('删除成功')
                    this.loadData()
                } else {
                    this.$message.error(res.message)
                }
            } catch (e) {
                if (e !== 'cancel') this.$message.error('删除失败')
            }
        },
        async saveItem() {
            try {
                await this.$refs.editFormRef.validate()
            } catch (e) {
                return
            }
            this.saving = true
            try {
                const res = await updateUser(this.editForm.id, this.editForm)
                if (res.code === 200) {
                    this.$message.success('更新成功')
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
.main-content { flex: 1; padding: 18px; height: 100%; overflow: auto; box-sizing: border-box; background-color: #F3F4F6 !important; }
.page-header { display: flex; justify-content: flex-start; align-items: center; margin-bottom: 8px; }
.title { font-size: 22px; font-weight: 700; color: #2b6b48; }
.table-card { margin-top: 8px; padding: 12px; background: #fff; border-radius: 8px; width: 100%; }
.table-footer { display: flex; justify-content: center; padding-top: 12px; }
.dialog-footer { text-align: right; }
.empty-tip { text-align: center; padding: 40px 0; color: #909399; }
.empty-tip p { margin-top: 10px; font-size: 14px; }
</style>
