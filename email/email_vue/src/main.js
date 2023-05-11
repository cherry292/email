
import Vue from 'vue'
import App from './App'
import router from './router'
import cors from 'cors'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios'



Vue.prototype.$axios = axios
axios.defaults.baseURL="http://localhost:8081/api"
Vue.use(ElementUI).use(cors);
axios.defaults.withCredentials=true;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
