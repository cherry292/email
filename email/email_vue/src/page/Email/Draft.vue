<template>
  <div id="draft" style="height: 100%">
    <div>草稿箱</div>
    <el-table
      :data="tableData"
      height="100%"
      max-height="calc(100vh - 200px)"
      border
      style="width: 100%"
      @row-click="send"
    >
      <el-table-column
        prop="updateTime"
        label="日期"
        width="180"

      >
        <template slot-scope="scope">
          {{ formatDate(scope.row.updateTime) }}
        </template>
      </el-table-column>
      <el-table-column
        prop="senderEmail"
        label="发件人"
        width="200"
      ></el-table-column>
      <el-table-column prop="subject" label="主题"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">

          <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>

        </template>
      </el-table-column>


    </el-table>

    <el-pagination
      :current-page="currentPage"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next"
      :total="tableData.length"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
    />
  </div>
</template>

<script>
import axios from "axios";

export default{
  name: 'draft',
  data() {
    return {
      tableData:[],
      email: {
        id:'',
        senderEmail:'',
        updateTime:'',
        subject:'',
        context:'',
        recipientEmail:''
  },
      currentPage: 1,
      pageSize: 5,
    }
  },
  methods: {
    handleCurrentChange(newPage) {
      this.currentPage = newPage;
    },
    handleSizeChange(newSize) {
      this.pageSize = newSize;
    },
    formatDate(dateString) {
      const date = new Date(dateString);
      const formatter = new Intl.DateTimeFormat('en', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
      });
      return formatter.format(date);
    },
    handleDelete(row) {
      console.log(row);
    },
    getTableData(){
      axios.get('/email/getdraft', {
        params: {
          page: this.currentPage,
          size: this.pageSize,
        },
      })
        .then(res=> {
          if (res){
            this.tableData = res.data.data.records;
          }else{
            this.$message.error('邮件查询失败，请重试。');
          }
        })
        .catch(error => {
          console.error('Error:', error);
          this.$message.error('邮件查询失败，请重试。');
        });
    },
    send(row) {
      this.$router.push({
        name: 'send',
        params: {
          email: row,
        },
      });
    },

    sendEmail() {
      // 发送邮件的逻辑

      // 判断表单是否为空
      if (this.form.recipientEmail.trim() === '' || this.form.subject.trim() === '' || this.form.content.trim() === '') {
        this.$message.error('请填写完整的邮件信息');
        return;
      }

      axios.post('/api/email/send', this.form, {'Content-Type': 'application/json',})
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

      this.composeDialogVisible = false;
    },
    saveDraft() {
      this.$confirm('是否保存为草稿？', '提示', {
        confirmButtonText: '保存',
        cancelButtonText: '不保存',
        type: 'warning',
      }).then(() => {

        this.$message.error('邮件保存草稿，请重试。');
        // 保存草稿的逻辑
        axios.post('/api/email/createdraft', this.form, {'Content-Type': 'application/json',})
          .then((response) => {
            if (response.data) {
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
      }).catch(() => {
        // 不保存草稿
        this.form.recipientEmail = '';
        this.form.subject = '';
        this.form.content = '';
        this.showComposeForm=false
      });
    },
  },
  computed: {
    tableDataPaginated() {
      return this.getTableData();
    },
  },
  created() {
    this.getTableData();
  },
}
</script>
