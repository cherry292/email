<template>
  <div>
    <el-form ref="registerForm" :model="form" :rules="rules" label-width="8epx" class="login-box">
      <h3 class="register-title">欢迎登录</h3>
      <el-form-item label="账号" prop="email">
        <el-input type="text" placeholder="请输入账号" v-model="form.email"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" placeholder="请输入密码" v-model="form.password"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" v-on:click="onSubmit('registerForm')">登录</el-button>
        <el-button type="text" @click="goToRegister">注册</el-button>
      </el-form-item>
    </el-form>


  </div>
</template>

<script>
import axios from "axios";
import router from "../../router";

export default {
  name: "Login",
  data() {
    return {
      form: {
        username: '',
        password: ''
      },
      //表单验证，需要在el-form-item元素中增加 prop 属性
      rules: {
        email: [
          {required: true, message: '账号不可为空', trigger: 'blur'},
          {type: 'email', message: '请输入正确的邮箱格式', trigger: ['blur', 'change']}
        ],
        password: [
          {required: true, message: '密码不可为空', trigger: 'blur'},
          {min: 8, message: '密码长度至少为8位', trigger: ['blur', 'change']}
        ]
      },

      //对话框显示和隐藏
      dialogVisible: false
    }
  },
  methods: {
    onSubmit(formName) {
      //为表单绑定验证功能
      axios({
        method: 'post',
        url: '/user/register',
        data: JSON.stringify(this.form),
        headers: {
          'Content-Type': 'application/json;charset=UTF-8'
        }
      }).then(res=>{
        console.log(res);
        if (res.data.flag) {
          this.$message({
            message: '注册成功！',
            type: 'success',
          });
          router.push('/')

        } else {
          this.$message.error('登录失败，请重试。');
        }
      }).catch(err=>{
        console.error('Error:', err);
      })
    },
    goToRegister() {
      router.push('/'); // Update to the actual URL of the registration page
    },
  }
}
</script>
<style scoped>
.login-box {
  border: 1px solid #DCDFE6;
  width: 350px;
  margin: 180px auto;
  padding: 35px 35px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #909399;
}

.login-title {
  text-align: center;
  margin: 0 auto 40px auto;
  color: #303133;
}
</style>

