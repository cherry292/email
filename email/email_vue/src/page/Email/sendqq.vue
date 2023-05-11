
<template>
  <div>
    <el-form :model="form" label-width="100px">
      <el-form-item label="收件人">
        <el-input v-model="form.recipientEmail" placeholder="请输入收件人"></el-input>
      </el-form-item>
      <el-form-item label="主题">
        <el-input v-model="form.subject" placeholder="请输入主题"></el-input>
      </el-form-item>
      <el-form-item label="正文">
        <el-switch v-model="codeView" active-text="源码" inactive-text="预览" @change="toggleCodeView"></el-switch>
        <div v-if="!codeView">
          <el-input type="textarea" v-model="form.message" placeholder="请输入正文"></el-input>
        </div>
        <div v-else>
          <el-input type="textarea" v-model="form.messageHtml" placeholder="请输入正文的 HTML 源码"></el-input>
          <div v-html="messagePreview"></div>
        </div>
      </el-form-item>
      <el-form-item label="附件">
        <input type="file" ref="fileInput" @change="handleFileChange">
        <button @click="uploadFile">上传文件</button>
        <div v-if="form.attachment">
          已上传文件：{{form.attachment}}
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="sendEmail">发送</el-button>
        <el-button type="primary" @click="saveDraft">存草稿</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from "axios";
const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;


export default {
  data() {
    return {
      form: {
        attachmentIds:0,
        recipientEmail: '',
        subject: '',
        content: '',
        message:'',
        messageHtml:'',
      },
      codeView: false
    }
  },
  computed: {
    messagePreview() {
      return this.codeView ? this.form.messageHtml : this.form.message
    }
  },
  methods: {
    toggleCodeView() {
      if (!this.codeView) {
        this.form.messageHtml = this.form.message
      } else {
        this.form.message = this.form.messageHtml
      }
    },
    handleFileChange(event) {
      this.file = event.target.files[0];
    },
    uploadFile() {
      if (!this.file) {
        return;
      }

      const formData = new FormData();
      formData.append('file', this.file);

      axios.post('/attachments/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      }).then((response) => {

        console.log(response.data);
        // 处理上传成功的逻辑
        this.form.attachmentIds=response.data.data;
        console.log(this.form);
      }).catch((error) => {
        console.error(error);
        // 处理上传失败的逻辑
      });
    },
    sendEmail() {
      if (!this.codeView) {
        this.form.content = this.form.message
      } else {
        this.form.content = this.form.messageHtml
      }
      // 判断表单是否为空
      if (!this.form.recipientEmail || !this.form.subject) {
        this.$message.error('请填写收件人、主题和正文');
        return;
      }

      if (!emailRegex.test(this.form.recipientEmail)) {
        this.$message.error('收件人邮件地址格式不正确');
        return;
      }
      this.$message.error('发送');
      console.log(this.form.attachmentIds);

      axios.post('/email/sendqq', this.form, {'Content-Type': 'application/json',})
        .then((response) => {
          if (response.data.flag) {
            this.$message({
              message: '邮件发送成功！',
              type: 'success',
            });
            this.form.recipientEmail = '';
            this.form.subject = '';
            this.form.content = '';
          } else {
            this.$message.error('邮件发送失败，请重试。');
          }
        })
        .catch((error) => {
          console.error('Error:', error);
          this.$message.error('邮件发送失败，请重试。');
        });

    },
    saveDraft() {
      if (!this.codeView) {
        this.form.content = this.form.message
      } else {
        this.form.content = this.form.messageHtml
      }
      axios.post('/email/createdraft', this.form, {'Content-Type': 'application/json',})
        .then((response) => {
          if (response.data.flag) {
            this.$message({
              message: '邮件保存成功！',
              type: 'success',
            });
            this.form.recipientEmail = '';
            this.form.subject = '';
            this.form.content = '';
          } else {
            this.$message.error('邮件保存失败，请重试。');
          }
        })
        .catch((error) => {
          console.error('Error:', error);
          this.$message.error('邮件保存失败，请重试。');
        });
    },
  },
}

</script>
