<template>
  <div>
    <input type="file" ref="fileInput" @change="handleFileChange">
    <button @click="uploadFile">上传文件</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      file: null,
    };
  },
  methods: {
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
      }).catch((error) => {
        console.error(error);
        // 处理上传失败的逻辑
      });
    },
  },
};
</script>
