<template>
    <div class="main-content">
        <div class="main-inner">
            <div class="page-header">
                <div class="title">个人中心</div>
            </div>

            <el-card class="info-card">
                <div slot="header"><span>基本信息</span></div>
                <el-table :data="[userInfo]" border style="width: 100%">
                    <el-table-column prop="id" label="用户ID" width="120" />
                    <el-table-column prop="username" label="用户名" />
                    <el-table-column prop="phone" label="手机号" />
                    <el-table-column prop="role" label="角色" width="120">
                        <template slot-scope="scope">
                            <el-tag :type="scope.row.role === 'ADMIN' ? 'danger' : 'success'">
                                {{ scope.row.role === 'ADMIN' ? '管理员' : '普通用户' }}
                            </el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="registerTime" label="注册时间" />
                    <el-table-column prop="maintenanceCount" label="养护次数" width="100" />
                    <el-table-column prop="plantingCount" label="种植次数" width="100" />
                </el-table>
            </el-card>

            <el-card class="password-card" style="margin-top: 20px;">
                <div slot="header"><span>修改密码</span></div>
                <el-form :model="passwordForm" :rules="passwordRules" ref="passwordFormRef" label-width="100px" style="max-width: 400px;">
                    <el-form-item label="原密码" prop="oldPassword">
                        <el-input v-model="passwordForm.oldPassword" type="password" show-password />
                    </el-form-item>
                    <el-form-item label="新密码" prop="newPassword">
                        <el-input v-model="passwordForm.newPassword" type="password" show-password />
                    </el-form-item>
                    <el-form-item label="确认密码" prop="confirmPassword">
                        <el-input v-model="passwordForm.confirmPassword" type="password" show-password />
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="handleChangePassword">修改密码</el-button>
                    </el-form-item>
                </el-form>
            </el-card>
        </div>
    </div>
</template>

<script>
import { getUserInfo, changePassword } from '@/api/api/login'

export default {
    name: 'UserProfile',
    data() {
        const validateConfirm = (rule, value, callback) => {
            if (value !== this.passwordForm.newPassword) {
                callback(new Error('两次输入密码不一致'))
            } else {
                callback()
            }
        }
        return {
            userInfo: {},
            passwordForm: {
                oldPassword: '',
                newPassword: '',
                confirmPassword: ''
            },
            passwordRules: {
                oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
                newPassword: [
                    { required: true, message: '请输入新密码', trigger: 'blur' },
                    { min: 6, message: '密码不少于6位', trigger: 'blur' }
                ],
                confirmPassword: [
                    { required: true, message: '请确认新密码', trigger: 'blur' },
                    { validator: validateConfirm, trigger: 'blur' }
                ]
            }
        }
    },
    created() {
        this.loadUserInfo()
    },
    methods: {
        async loadUserInfo() {
            const userId = localStorage.getItem('id')
            if (!userId) return
            const res = await getUserInfo(userId)
            if (res.code === 200) {
                this.userInfo = res.data
            }
        },
        handleChangePassword() {
            this.$refs.passwordFormRef.validate(async valid => {
                if (!valid) return
                const userId = localStorage.getItem('id')
                const res = await changePassword({
                    userId,
                    oldPassword: this.passwordForm.oldPassword,
                    newPassword: this.passwordForm.newPassword
                })
                if (res.code === 200) {
                    this.$message.success('密码修改成功')
                    this.passwordForm = { oldPassword: '', newPassword: '', confirmPassword: '' }
                } else {
                    this.$message.error(res.message)
                }
            })
        }
    }
}
</script>

<style scoped>
.main-content { flex: 1; padding: 18px; height: 100%; overflow: auto; box-sizing: border-box; background-color: #F3F4F6 !important; }
.page-header { display: flex; justify-content: flex-start; align-items: center; margin-bottom: 12px; }
.title { font-size: 22px; font-weight: 700; color: #2b6b48; }
.info-card, .password-card { border-radius: 8px; }
</style>
