/*
 * @Author: your name
 * @Date: 2020-04-13 10:40:44
 * @LastEditTime: 2020-04-13 18:20:40
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \vueTest\houtai\src\router\index.js
 */
import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/page/layout'
import Draft from "../page/Email/Draft.vue";
import Inbox from "../page/Email/Inbox.vue";
import Login from "../page/User/Login.vue";
import Register from "../page/User/Register.vue";
import Spam from "../page/Email/Spam.vue";
import Send from "../page/Email/Send.vue";
import Sent from "../page/Email/Sent.vue";
import UploadFile from "../page/Email/UploadFile.vue";
import DownloadButton from "../page/Email/sendqq.vue";
import display from "../page/Email/Display.vue";
import sendqq from "../page/Email/sendqq.vue";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: '登录',
      component: Login
    },
    {
      path: '/Register',
      name: '注册',
      component: Register
    },
    {
      path: '/layout',
      name: 'Layout',
      component: Layout,
      children: [ {
        path: '/draft',
        name: 'draft',
        component: Draft
      },
        {
          path: '/send',
          name: 'send',
          component: Send
        },{
          path: '/sent',
          name: 'sent',
          component: Sent
        },{
        path: '/inbox',
        name: 'inbox',
        component: Inbox
      },{
        path: '/spam',
        name: 'spam',
        component: Spam
      },{
        path:'/UploadFile',
          name:'UploadFile',
          component:UploadFile
        },{
        path:'/sendqq',
          name:'sendqq',
          component:sendqq
        },{
          path: '/display',
          name: 'display',
          component: display
        }]
    },
  ]
})
