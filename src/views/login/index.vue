<script setup>
  import { ref } from 'vue';
  import { loginSystem } from "../../api/login";
  import { useRouter } from "vue-router";
  import { ElMessage } from 'element-plus';
  
  let loginForm = ref({username:'', password:''})
  const router = useRouter();

  const login = async() => {
   const result = await loginSystem(loginForm.value);
   console.log(loginForm.value);
   if (result.code == 1) {
     ElMessage.success('登录成功');
     localStorage.setItem('loginUser', JSON.stringify(result.data));
     router.push('/index');
   } else {
     ElMessage.error(result.msg);
   }
  }

  const onReset = () => {
    loginForm.value = {username:'', password:''}
  }
  
</script>
<!-- ---------------------------------------------------------------------------------------------------------------------- -->
<template>
  <div id="container">
    <div class="login-form">
      <el-form label-width="80px">
        <p class="title">Yilena教学后台信息管理系统</p>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button class="button" type="primary" @click="login">登 录</el-button>
          <el-button class="button" type="info" @click="onReset">重 置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<!-- ---------------------------------------------------------------------------------------------------------------------- -->
<style scoped>
#container {
  padding: 15%;
  height: 410px;
  background-image: url('../../assets/pic/tutieshi_640x360_29s.gif');
  background-repeat: no-repeat;
  background-size: cover;
}

.login-form {
  max-width: 400px;
  padding: 30px;
  margin: 0 auto;
  border: 1px solid #0e0a0a;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(232, 13, 13, 0.5);

}

.title {
  font-size: 30px;
  font-family: '仿宋';
  text-align: center;
  margin-bottom: 30px;
  font-weight: bold;
}

.button {
  margin-top: 30px;
  width: 120px;
}

::v-deep .el-form-item__label {
  color: #fffc42; /* 使用深度选择器 */
}

::v-deep .el-input__inner {
  background-color: transparent; /* 设置输入框背景颜色为透明 */
  color: #4d4c4ca9; /* 可选：设置输入框文字颜色 */
}
</style>