<template>
    <div class="login-container">
        <div class="login-box">
            <h2 class="login-title">绿化区块管理系统</h2>
            <!-- 登录表单 + 验证 -->
            <el-form ref="loginFormRef" :model="loginForm" :rules="rules" label-width="80px" class="login-form">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="loginForm.username" placeholder="请输入用户名" prefix-icon="el-icon-user"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"
                        prefix-icon="el-icon-lock"></el-input>
                </el-form-item>
                
                <el-form-item>
                    <el-button type="primary" class="login-btn" @click="handleLogin" style="width: 100%">
                        登录
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import {login} from '@/api/api/login.js';
import { setToken } from '@/utils/setToken';
import { EventBus } from '@/utils/eventBus'
export default {
    name: 'LoginBox',
    // 核心：必须在 data 中定义模板引用的变量
    data() {
        return {
            // 登录表单数据（模板中用到的 loginForm）
            loginForm: {
                username: '', // 用户名
                password: '', // 密码
                code: ''      // 验证码
            },
            // 表单验证规则（模板中用到的 rules）
            rules: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { min: 3, max: 20, message: '用户名长度在 3 到 20 个字符', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 6, message: '密码长度不少于 6 个字符', trigger: 'blur' }
                ],
                code: [
                    { required: true, message: '请输入验证码', trigger: 'blur' },
                    { length: 4, message: '验证码长度为 4 个字符', trigger: 'blur' }
                ]
            }
        };
    },
    methods: {
       handleLogin() {
  if (!this.loginForm.username) {
    this.$message.error('请输入用户名');
  } else if (!this.loginForm.password) {
    this.$message.error('请输入密码');
  } else {
    console.log('日志测试1');
    login(this.loginForm)
      .then(res => {
        console.log('后端完整响应：', res); 
        // 修正：直接解构 res（因为 res 就是后端的 {code, message, data}）
        const { code, message, data } = res; 
        if (code === 200) { 
          const token = 'Bearer' + ' ' + data.access_token
          setToken(token)
          localStorage.setItem('id', data.id)
          this.$message.success(message || '登录成功'); 
          this.$router.push('/HeadBox');
          EventBus.$emit('token-changed', token);
        } else {
          this.$message.error(message || '登录失败，请检查账号密码');
        }
      })
      .catch(err => {
        console.log(err);
        this.$message.error('网络异常，登录失败');
      })
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

.code-img {
    text-align: center;
    height: 40px;
    line-height: 40px;
    background: #f5f5f5;
    border-radius: 4px;
    overflow: hidden;
}
</style>