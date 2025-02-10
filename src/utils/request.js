import axios from 'axios';
import router from "../router";
import { ElMessage } from 'element-plus';

const request = axios.create({
  baseURL: '/api',
  timeout: 1000
});

request.interceptors.request.use(
  config => {
    const result = JSON.parse(localStorage.getItem('loginUser'));
    if (result && result.token) {
      config.headers.token = result.token;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

request.interceptors.response.use(
  response => {
    return response.data;
  },
  error => {
    if (error.response.status == 401) {
      ElMessage.error('登录已超时，请重新登录');
      router.push('/login');
    }
    return Promise.reject(error);
  }
);

export default request;