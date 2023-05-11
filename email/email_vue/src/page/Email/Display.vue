<template>
  <div>
    <div slot="header" class="email-header">
        <span class="sender">{{ email.senderEmail }}</span>
        <span class="date">{{ email.updateTime }}</span>
      </div>
      <div class="email-content">
        <div class="email-info">
          <span class="email-info-label">收件人:</span>
          <span class="email-info-value">{{ email.recipientEmail }}</span>
        </div>
        <div class="email-info">
          <span class="email-info-label">主题:</span>
          <span class="email-info-value">{{ email.subject }}</span>
        </div>
        <div class="email-body">
          <div  v-if="email.attachmentIds!==0">
            <a>附件：</a>
            <button @click="getFile()">
              {{ filename }}</button>
          </div>
          <div v-html="email.content"></div>
        </div>


      </div>
  </div>

</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      filename: '123123',
      fileUrl: '',
      email: {
        attachmentIds: '',
        bccRecipientEmail: '',
        ccRecipientEmail: '',
        content: '',
        createTime: '',
        draft: '',
        id: '',
        read: '',
        recipient: '',
        recipientEmail: '',
        send: '',
        senderEmail: '',
        spam: '',
        subject: '',
        updateTime: '',
        attachmentNames: [],
      }
    }
  },
  methods:{
    getFile() {
      axios({
        url: `/attachments/download/${this.email.attachmentIds}`,
        method: 'GET',
        responseType: 'blob'
      })
        .then(response => {
          const filename = this.filename;
          const url = window.URL.createObjectURL(new Blob([response.data]));
          const link = document.createElement('a');
          link.href = url;
          link.setAttribute('download', filename);
          document.body.appendChild(link);
          link.click();
        })
        .catch(error => {
          console.error(error);
        });
    },
   getFileName(){
     axios({
       url: `/attachments/getfilename/${this.email.attachmentIds}`,
       method: 'GET'
     }).then((response) => {
       console.log(response);
       this.filename=response.data.msg;

     });
   }
  },
  computed: {

  },
  created() {
    const email = this.$route.params.email;
    if (email) {
      this.email = email;
      if (this.email.attachmentIds){
        this.getFileName();
      }
    }
  },
};
</script>

<style scoped>
.email-card {
  margin-bottom: 20px;
}

.email-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background-color: #f5f5f5;
  font-weight: bold;
  font-size: 16px;
}

.email-info {
  margin-bottom: 10px;
}

.email-info-label {
  font-weight: bold;
  margin-right: 10px;
}

.email-body pre {
  white-space: pre-wrap;
}
</style>
