<style scoped>
.el-aside {
    display: flex;
    justify-content: center;
    align-items: center;
}

section{
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.logo {
  width: 200px;
}

.headerLogo,.logo{
  cursor: pointer;
}


</style>

<template>
  <el-container>
    <el-aside width="auto" class="header-logo tap" style="height: 100%">
      <i class="el-icon-money"/>
    </el-aside>

    <el-aside width="auto" class="header-logo tap" >
      <el-popover
        placement="bottom-end"
        width="160"
        trigger="click"
      >
        <p>
          <el-button type="text" @click="logout">登出</el-button>
        </p>
        <el-link slot="reference" :underline="false" class="user-info">
          用户: {{ username }}
        </el-link>
      </el-popover>
      <el-avatar icon="el-icon-user-solid" class="headerLogo"></el-avatar>
    </el-aside>
  </el-container>
</template>

<script>
import axios from "axios";
import router from "../router";

export default {
  data() {
    return {
      activeIndex: "1",
      username: '',
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    logout() {
      localStorage.removeItem('userInfo');
      localStorage.removeItem('accessToken');

      // 2. 向服务器发送登出请求（可选，取决于后端实现）
      axios.get('/api/user/logout')
        .then(response => {
          if (response.status === 200) {
            console.log('登出成功');
          } else {
            console.error('登出失败:', response);
          }
        })
        .catch(error => {
          console.error('登出失败:', error);
        })
        .finally(() => {
          alert("已登出");
          // 3. 将用户重定向到登录页面
          location.href = '/page/login.html';
        });

    },
    getUserInfo() {
      axios.get('/user/getusername')
        .then(res=> {
          if (res.data.flag){
            this.username=res.data.data;
          }else{
            this.$message.error('用户未登录');
            location.href = '/page/login.html';
          }
        })
        .catch(error => {
          console.error('Error:', error);
          this.$message.error('用户未登录');
          location.href = '/page/login.html';
        });
    },
  },
  created() {
    this.getUserInfo();
  },
};
</script>
