<template>
    <div class="login-container">
        <div class="login-box">
            <h2 class="login-title">注册账号</h2>
            <el-form ref="registerFormRef" :model="registerForm" :rules="rules" label-width="80px" class="login-form">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="registerForm.username" placeholder="请输入用户名" prefix-icon="el-icon-user"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="registerForm.password" type="password" placeholder="请输入密码"
                        prefix-icon="el-icon-lock"></el-input>
                </el-form-item>
                <el-form-item label="手机号" prop="phone">
                    <el-input v-model="registerForm.phone" placeholder="请输入手机号" prefix-icon="el-icon-phone"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" class="login-btn" @click="handleRegister" style="width: 100%">
                        注册
                    </el-button>
                </el-form-item>
                <div class="login-link">
                    已有账号？<router-link to="/LoginBox">去登录</router-link>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
import { register } from '@/api/api/login.js';
export default {
    name: 'RegisterBox',
    data() {
        return {
            registerForm: {
                username: '',
                password: '',
                phone: ''
            },
            rules: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { min: 3, max: 20, message: '用户名长度在 3 到 20 个字符', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 6, message: '密码长度不少于 6 个字符', trigger: 'blur' }
                ],
                phone: [
                    { required: true, message: '请输入手机号', trigger: 'blur' },
                    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
                ]
            }
        };
    },
    methods: {
        handleRegister() {
            this.$refs.registerFormRef.validate(valid => {
                if (!valid) return;
                register(this.registerForm)
                    .then(res => {
                        if (res.code === 200) {
                            this.$message.success('注册成功，请登录');
                            this.$router.push('/LoginBox');
                        } else {
                            this.$message.error(res.message || '注册失败');
                        }
                    })
                    .catch(() => {
                        this.$message.error('网络异常，注册失败');
                    });
            });
        }
    }
};
</script>

<style scoped>
.login-container {
    width: 100vw;
    height: 100vh;
    background-image: url(../assets/img/background.png);
    display: flex;
    align-items: center;
    justify-content: center;
    background-size: cover;
    background-repeat: no-repeat;
}

.login-box {
    width: 400px;
    padding: 30px;
    background: #fff;
    border-radius: 10px;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
}

.login-title {
    text-align: center;
    color: #006B3E;
    margin-bottom: 20px;
    font-size: 24px;
    font-weight: 700;
}

.login-form {
    margin-top: 20px;
}

.login-btn {
    height: 40px;
    font-size: 16px;
}

.login-link {
    text-align: center;
    margin-top: 10px;
    font-size: 14px;
    color: #666;
}

.login-link a {
    color: #006B3E;
    text-decoration: none;
}

.login-link a:hover {
    text-decoration: underline;
}
</style>
