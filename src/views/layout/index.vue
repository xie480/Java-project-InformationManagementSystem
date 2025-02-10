<template>
  <div class="common-layout">
    <el-container>
      <!-- Header 区域 -->
      <el-header class="header">
        <span class="title">Yilena教学后台信息管理系统</span>
        <span class="right_tool">
          <span class="avatar-wrapper"><el-avatar :src="User1.image" :size="30" ></el-avatar></span>
            <span class="username">【{{ loginName }}】</span>
          <a href="javascript:void(0)" @click="updatePassword">
            &nbsp;&nbsp;&nbsp; |  &nbsp;&nbsp;&nbsp;<el-icon><InfoFilled /></el-icon> 修改密码 &nbsp;&nbsp;&nbsp; |  &nbsp;&nbsp;&nbsp;
          </a>
          <a href="javascript:void(0)" @click="logout">
            <el-icon><SwitchButton /></el-icon> 退出登录
          </a>
        </span>
        
      </el-header>
      
      <el-container>
        <!-- 左侧菜单 -->
        <el-aside width="200px" class="aside">
          <el-scrollbar>
        <el-menu router>
          <el-menu-item index="/index">
            <template #title>
              <el-icon><HomeFilled /></el-icon>首页
            </template>
          </el-menu-item>
          <el-sub-menu index="2">
            <template #title>
              <el-icon><Avatar /></el-icon>班级学员管理
            </template>
              <el-menu-item index="/clazz"><el-icon><EditPen /></el-icon>班级管理</el-menu-item>
              <el-menu-item index="/student"><el-icon><User /></el-icon>学员管理</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              <el-icon><Tools /></el-icon>系统信息管理
            </template>
              <el-menu-item index="/depts"><el-icon><EditPen /></el-icon>部门管理</el-menu-item>
              <el-menu-item index="/emp"><el-icon><User /></el-icon>员工管理</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="4">
            <template #title>
              <el-icon><Histogram /></el-icon>信息统计管理
            </template>
              <el-menu-item index="/sr"><el-icon><PieChart /></el-icon>学员信息统计</el-menu-item>
              <el-menu-item index="/er"><el-icon><PieChart /></el-icon>员工信息统计</el-menu-item>
          </el-sub-menu>
          <el-menu-item index="/log">
            <template #title>
              <el-icon><Management /></el-icon>后台日志管理
            </template>
          </el-menu-item>
        </el-menu>
      </el-scrollbar>
        </el-aside>
        
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
        
    </el-container>
  </div>

  <el-dialog v-model="dialogFormVisible" width="500" >
    <template #title>
      <div class="dialog-title">
        <el-icon><Promotion /></el-icon>修改密码
      </div>
    </template>
    <el-form :model="password" ref="passwordFormRef" :rules="rules">
      <el-form-item label="请输入更改后的密码" :label-width="formLabelWidth" prop="newPassword">
        <el-input type="password" v-model="password.newPassword" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="请再输入一次&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" :label-width="formLabelWidth" prop="confirmPassword">
        <el-input type="password" v-model="password.confirmPassword" autocomplete="off"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="updatePass(User1)">确认</el-button>
      </div>
    </template>
  </el-dialog>
</template>
<!-- ---------------------------------------------------------------------------------------------------------------------- -->
<script setup>
import {ref, onMounted} from 'vue'
import {getId,findByIdEmp,updateEmps} from '../../api/emps'
import { ElMessage, ElMessageBox } from 'element-plus';
import {useRouter} from 'vue-router'

let router = useRouter()

const loginName = ref('');

const password = ref({
  newPassword: '',
  confirmPassword: ''
});

const passwordFormRef = ref(null);

const dialogFormVisible = ref(false);

const User1 = ref({
  id: '',
  password: '',
  image: ''
})

const getUserId = async(name) => {
  const result = await getId(name);
  if (result.code == 1) {
    User1.value.id = result.data.id;

    const result2 = await findByIdEmp(result.data.id);
    if (result2.code == 1) {
      User1.value = result2.data;
      loginName.value = User1.value.name;
    } else {
      ElMessage.error(result2.msg);
    }
  } else {
    ElMessage.error(result.msg);
  }
}

const logout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    localStorage.removeItem('loginUser');
    router.push('/login');
  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '已取消退出'
    });
  });
}

const updatePassword = () => {
  dialogFormVisible.value = true;
  password.value = ({
    newPassword: '',
    confirmPassword: ''
  });
  if(passwordFormRef.value) {
    passwordFormRef.value.resetFields();
  }
}

const updatePass = async(User) => {
  if(passwordFormRef.value) {
    passwordFormRef.value.validate(async(valid) => {
      if (valid) {
        User.password = password.value.newPassword;
        const result = await updateEmps(User);
        if (result.code == 1) {
          ElMessage.success('修改成功');
          dialogFormVisible.value = false;
        } else {
          ElMessage.error(result.msg);
        }
      } else {
        ElMessage.error('请输入正确的密码');
      }
    });
  }
}

const rules = {
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, max: 16, message: '密码长度为6-16位', trigger: 'blur' },
    { pattern: /^[a-zA-Z0-9]+$/, message: '密码只能包含字母和数字', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { validator: (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== password.value.newPassword) {
        callback(new Error('两次输入的密码不一致'));
      } else {
        callback();
      }
    }, trigger: 'blur' }
  ]
}

//定义钩子函数, 获取登录用户名
onMounted(() => {
  //获取登录用户名
  let loginUser = JSON.parse(localStorage.getItem('loginUser'))
  if (loginUser) {
    loginName.value = loginUser.name
  }
  getUserId(loginName.value);
})


</script>
<!-- ---------------------------------------------------------------------------------------------------------------------- -->
<style scoped>
.header {
  background-image: linear-gradient(to right, #00547d, #007fa4, #00aaa0, #00d072, #a8eb12);
}

.title {
  color: white;
  font-size: 40px;
  font-family: 楷体;
  line-height: 60px;
  font-weight: bolder;
}

.right_tool{
  float: right;
  line-height: 60px;
}

a {
  color: white;
  text-decoration: none;
}

.aside {
  width: 220px;
  border-right: 1px solid #ccc;
  height: 730px;
}
.layout-container-demo .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}
.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
}
.layout-container-demo .el-menu {
  border-right: none;
}
.layout-container-demo .el-main {
  padding: 0;
}
.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}

.dialog-title {
  display: flex;
  align-items: center;
  font-weight: bold;
  font-size: 20px;  
  color: rgba(0, 162, 255, 0.836);
  gap: 10px;
}

.username{
  color: rgb(255, 223, 79);
  text-decoration: none;
  margin-right: 10px;
}

.avatar-wrapper {
  display: inline-block;  /* 改为块级格式化上下文 */
  vertical-align: middle; /* 关键：垂直对齐方式 */
  margin-bottom: 20px;       /* 现在可以单独控制 */
  width: 30px;
  height: 30px;
}
</style>
