<template>
    <div class="login-container">
        <div class="login-box">
            <h2 class="login-title">绿化区块管理系统</h2>
            <el-form ref="loginFormRef" :model="loginForm" :rules="rules" label-width="80px" class="login-form">
                <el-form-item label="身份" prop="identity">
                    <el-select v-model="loginForm.identity" placeholder="请选择登录身份" style="width: 100%;">
                        <el-option label="管理员" value="管理员"></el-option>
                        <el-option label="用户" value="用户"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="loginForm.username" placeholder="请输入用户名" prefix-icon="el-icon-user"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"
                        prefix-icon="el-icon-lock"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" class="login-btn" @click="handleLogin" style="width: 80%">
                        登录
                    </el-button>
                </el-form-item>
                <div class="login-link">
                    还没有账号？<router-link to="/RegisterBox">去注册</router-link>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
import { userLogin, adminLogin } from '@/api/api/login.js';
import { setToken } from '@/utils/setToken';
export default {
    name: 'LoginBox',
    data() {
        return {
            loginForm: {
                identity: '管理员',
                username: '',
                password: ''
            },
            rules: {
                identity: [
                    { required: true, message: '请选择登录身份', trigger: 'change' }
                ],
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' }
                ]
            }
        };
    },
    methods: {
        async handleLogin() {
            try {
                await this.$refs.loginFormRef.validate();
            } catch (err) {
                return;
            }

            const loginParams = {
                username: this.loginForm.username,
                password: this.loginForm.password
            };

            try {
                let res;
                if (this.loginForm.identity === '管理员') {
                    res = await adminLogin(loginParams);
                } else {
                    res = await userLogin(loginParams);
                }

                if (res.code === 200) {
                    const token = 'Bearer ' + res.data.token;
                    setToken(token);
                    localStorage.setItem('id', res.data.userId);
                    this.$message.success(res.message || '登录成功');

                    if (this.loginForm.identity === '管理员') {
                        this.$router.push('/HeadWeb');
                    } else {
                        this.$router.push('/UserMain');
                    }
                } else {
                    this.$message.error(res.message || '登录失败');
                }
            } catch (err) {
                this.$message.error('网络异常，登录失败');
            }
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
