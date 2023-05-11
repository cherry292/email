<style scoped>
.el-row{
  height: 100%;
}

.el-menu{
  border-right:none;
}
.el-aside{
  border-right: 1px solid #f5f1f1;
}
</style>

<template>
  <el-aside width="200px">
    <el-row class="tac">
      <el-col>
        <el-menu
          default-active="1"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
        >
          <el-menu-item index="1" @click="goPage('inbox')">
            <i class="el-icon-message"></i>
            <span slot="title">收件箱</span>
          </el-menu-item>
          <el-menu-item index="2" @click="goPage('sent')">
            <i class="el-icon-chat-dot-square"></i>
            <span slot="title">发件箱</span>
          </el-menu-item>
          <el-menu-item index="3" @click="goPage('draft')">
            <i class="el-icon-edit"></i>
            <span slot="title">草稿箱</span>
          </el-menu-item>
          <el-menu-item index="4" @click="goPage('spam')">
            <i class="el-icon-delete"></i>
            <span slot="title">垃圾箱</span>
          </el-menu-item>
          <el-menu-item index="5" @click="goPage('send')">
            <i class="el-icon-chat-line-square"></i>
            <span slot="title">写邮件</span>
          </el-menu-item>
          <el-menu-item index="6" @click="goPage('sendqq')">
            <i class="el-icon-chat-line-square"></i>
            <span slot="title">小功能</span>
          </el-menu-item>

        </el-menu>
      </el-col>
    </el-row>
  </el-aside>
</template>

<script>
var $this = {};
export default {
  data() {
    return {};
  },
  beforeCreate() {
    $this = this;
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },

    //跳转到某个导航页
    goPage(link) {
      if (link === "inbox") {
        $this.$router.push("/inbox").catch(error => error);
      } else if ((link === "sent")) {
        $this.$router.push("/sent").catch(error => error);
      }else if ((link === "spam")) {
        $this.$router.push("/spam").catch(error => error);
      }else if ((link === "draft")) {
        $this.$router.push("/draft").catch(error => error);
      }else if ((link === "send")) {
        $this.$router.push("/send").catch(error => error);
      }else if ((link === "sendqq")) {
        $this.$router.push("/sendqq").catch(error => error);
      }
    }
  }
};
</script>
