<script setup>
import { ref,watch,onMounted } from "vue";
import { findAllEmp,saveEmp,findByIdEmp,updateEmps,deleteEmp } from "../../api/emps";
import { findAllDept } from "../../api/depts";
import { ElMessage,ElMessageBox } from 'element-plus';

const emp = ref({
  name: '',
  gender: '',
  enterDate: [],
  begin: '',
  end: ''
});

//职位列表数据
const jobs = ref([{ name: '班主任', value: 1 },{ name: '讲师', value: 2 },{ name: '学工主管', value: 3 },{ name: '教研主管', value: 4 },{ name: '咨询师', value: 5 },{ name: '其他', value: 6 }]);
//性别列表数据
const genders = ref([{ name: '男', value: 1 }, { name: '女', value: 2 }]);
//部门列表数据
const deptList = ref([]);

const empFormRef = ref(null);

const save = () => {
  dialogTitle.value = '新增员工';
  dialogFormVisible.value = true;
  updateEmp.value = {
  username: '',
  name: '',
  gender: '',
  phone: '',
  job: '',
  salary: '',
  deptId: '',
  entryDate: '',
  image: '',
  exprList: []
 }
 if(empFormRef.value){
  empFormRef.value.resetFields();
 }
}



const dialogFormVisible = ref(false);

const dialogTitle = ref('');

let updateEmp = ref({
  username: '',
  name: '',
  gender: '',
  phone: '',
  job: '',
  salary: '',
  deptId: '',
  entryDate: '',
  image: '',
  exprList: []
});

const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const token = ref('');

onMounted(async() => {
  searchAll();
  getToken();

  const result = await findAllDept();
  if(result.code == 1){
    deptList.value = result.data;
  }
});


const getToken = () => {
  const result = JSON.parse(localStorage.getItem('loginUser'));
  console.log(result);
  if(result && result.token){
    token.value = result.token;
  }
}

const searchAll = async() =>{
  const result = await findAllEmp(emp.value.name,emp.value.gender,emp.value.begin,emp.value.end, currentPage.value, pageSize.value);
  if(result.code == 1){
    empList.value = result.data.rows;
    total.value = result.data.total;
  }else{
    ElMessage.error(result.msg);
  }
}

watch(() => emp.value.enterDate, (newValue, oldValue) => {
  if(newValue.length === 2) {
    emp.value.begin = newValue[0];
    emp.value.end = newValue[1];
  }else{
    emp.value.begin = '';
    emp.value.end = '';
  }
});


let empList = ref([]);

const onReset = () => {
  emp.value = {
    name: '',
    gender: '',
    enterDate: ''
  };
  searchAll();
}

const handleSizeChange = (val) => {
  searchAll()
}

const handleCurrentChange = (val) => {
  searchAll()
}

//文件上传
// 图片上传成功后触发
const handleAvatarSuccess = (response,uploadFile) => {
  updateEmp.value.image = response.data
}
// 文件上传之前触发
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('只支持上传图片')
    return false
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('只能上传10M以内图片')
    return false
  }
  return true
}

const saveExpr = () => {
  updateEmp.value.exprList.push({
    company: '',
    job: '',
    begin: '',
    end: '',
    date: []
  });
}

const deleteExpr = (index) => {
  updateEmp.value.exprList.splice(index, 1);
}

watch(() => updateEmp.value.exprList,(newValue,oldValue) => {
  if(newValue && newValue.length > 0){
    newValue.forEach((expr) => {
      if (expr.date && expr.date.length > 0) {
        expr.begin = expr.date[0];
        expr.end = expr.date[1];
      }
    })
  }
},{deep:true})

const saveToS = async() => {
  if(!empFormRef.value){
      return;
    }
  empFormRef.value.validate(async(valid) => {
    if(valid){
      let result = null;
      if(updateEmp.value.id){
        result = await updateEmps(updateEmp.value);
      }else{
        result = await saveEmp(updateEmp.value);
      }
      if(result.code == 1){
        ElMessage.success('操作成功')
        dialogFormVisible.value = false
        searchAll()
      }else{
        ElMessage.error(result.msg)
      }
    }
  });
}

const rules = ref({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度应在2到20个字符之间', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '姓名长度应在2到10个字符之间', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1\d{10}$/g, message: '请输入有效的手机号', trigger: 'blur' }
  ]
  });

  const findById = async(id) =>{
    const result = await findByIdEmp(id);
    if(result.code == 1){
      dialogFormVisible.value = true;
      dialogTitle.value = '修改员工';
      updateEmp.value = result.data;
      
      //对工作经历进行处理
      let exprList = updateEmp.value.exprList;
      if(exprList && exprList.length > 0){
        exprList.forEach((expr) => {
          expr.date = [expr.begin, expr.end];
        })
      }
    }
  }

  //记录勾选的员工的id
const selectedIds = ref([]);
//复选框勾选发生变化时触发 - selection: 当前选中的记录 (数组)
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map( item => item.id);
}

 const deleteById = async(id) =>{
    const result = await deleteEmp(id);
    if(result.code == 1){
      ElMessage.success('删除成功');
      searchAll();
    }
    else{
      ElMessage.error(result.msg);
    }
  }

  const deleteAll = async() =>{
    //弹出确认框
  ElMessageBox.confirm('您确认删除所选中的员工吗?','提示',
    { confirmButtonText: '确认',cancelButtonText: '取消',type: 'warning'}
  ).then(async () => { //确认
    if(selectedIds.value && selectedIds.value.length > 0){
      const result = await deleteEmp(selectedIds.value);
      if(result.code){
        ElMessage.success('删除成功');
        searchAll();
      }else{
        ElMessage.error(result.msg);
      }
    }else {
      ElMessage.info('您没有选择任何要删除的数据');
    }
  }).catch(() => { //取消
    ElMessage.info('已取消');
  })
  }

  
</script>
<!-- ---------------------------------------------------------------------------------------------------------------------- -->
<template>
  <h1>员工管理</h1>

  <div class="emp-c">
    <el-form :inline="true" :model="emp" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="emp.name" placeholder="请输入员工姓名" clearable />
      </el-form-item>
      <el-form-item label="性别">
        <el-select
          v-model="emp.gender"
          placeholder="请选择"
          clearable
        >
          <el-option label="男" value="1" />
          <el-option label="女" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="入职日期">
      <el-date-picker
        v-model="emp.enterDate"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        value-format="YYYY-MM-DD"
      ></el-date-picker>
    </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchAll">查询</el-button>
        <el-button type="info" @click="onReset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>


  <div class="button"> 
    <el-button type="danger" round id="delete" @click="deleteAll">-  批量删除</el-button>
    <el-button type="success" round id="add" @click="save">+  新增员工</el-button>
  </div>

  <div class="emp-c">
    <el-table :data="empList" border stripe height="490" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column property="name" label="姓名" width="120" align="center"/>
      <el-table-column label="性别" width="60" align="center">
        <template #default="scope" >
          {{ scope.row.gender == 1 ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column label="头像" width="120" align="center">
        <template #default="scope" >
          <img :src="scope.row.image" class="avatar" />
        </template>
      </el-table-column>
      <el-table-column property="deptName" label="所属部门" width="200" align="center"/>
      <el-table-column property="job" label="职位" width="200" align="center">
        <template #default="scope">
          <span v-if="scope.row.job == 1">班主任</span>
          <span v-else-if="scope.row.job == 2">讲师</span>
          <span v-else-if="scope.row.job == 3">学工主管</span>
          <span v-else-if="scope.row.job == 4">教研主管</span>
          <span v-else-if="scope.row.job == 5">咨询师</span>
          <span v-else>其他</span>
        </template>
      </el-table-column>
      <el-table-column property="enterDate" label="入职日期" width="250" align="center"/>
      <el-table-column property="updateTime" label="最后操作时间" width="250" align="center"/>
      <el-table-column label="操作"  align="center">
        <template #default="scope">
        <el-button size="small" type="primary" @click="findById(scope.row.id)"><el-icon><CirclePlusFilled /></el-icon>编辑</el-button>
        <el-popconfirm
            width="220"
            :icon="InfoFilled"
            icon-color="#626AEF"
            title="确定要删除该员工吗?"
            @cancel="onCancel"
            @confirm="deleteById(scope.row.id)"
          >
            <template #reference>
              <el-button type="danger" size="small">
                <el-icon><DeleteFilled /></el-icon>删除
              </el-button>
            </template>
          </el-popconfirm>
      </template>
    </el-table-column>
    </el-table>
  </div>

  <div class="demo-pagination-block">
    <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[5, 10, 15, 20]"
      :background="true"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>

  <div class="dialog">
    <el-dialog v-model="dialogFormVisible" width="800">
    <template #title>
      <div class="dialog-title">
        <el-icon><Promotion /></el-icon>
        {{ dialogTitle }}
      </div>
    </template>
    <el-form :model="updateEmp" ref="empFormRef" :rules="rules">
      <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="updateEmp.username" placeholder="请输入员工用户名，2-20个字"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名&nbsp;&nbsp;&nbsp;" prop="name">
              <el-input v-model="updateEmp.name" placeholder="请输入员工姓名，2-10个字"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别&nbsp;&nbsp;&nbsp;" prop="gender">
              <el-select v-model="updateEmp.gender" placeholder="请选择" style="width: 100%;">
                <el-option v-for="gender in genders" :key="gender.name" :label="gender.name" :value="gender.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="updateEmp.phone" placeholder="请输入员工手机号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="职位&nbsp;&nbsp;&nbsp;">
              <el-select v-model="updateEmp.job" placeholder="请选择" style="width: 100%;">
                <el-option v-for="job in jobs" :key="job.name" :label="job.name" :value="job.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="薪资&nbsp;&nbsp;&nbsp;">
              <el-input v-model="updateEmp.salary" placeholder="请输入员工薪资"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所属部门">
              <el-select v-model="updateEmp.deptId" placeholder="请选择部门" style="width: 100%;">
                <el-option v-for="dept in deptList" :key="dept.id" :label="dept.name" :value="dept.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入职日期">
              <el-date-picker v-model="updateEmp.enterDate" type="date" style="width: 100%;" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="头像&nbsp;&nbsp;&nbsp;">
              <el-upload
                class="avatar-uploader"
                action="/api/upload"
                :headers="{'token':token}"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                >
                <img v-if="updateEmp.image" :src="updateEmp.image" class="avatar" />
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>


        <el-row :gutter="10">
          <el-col :span="24">
            <el-form-item label="工作经历">
              <el-button type="success" size="small" @click="saveExpr">+ 添加工作经历</el-button>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="3" v-for="(expr,index) in updateEmp.exprList">
          <el-col :span="10">
            <el-form-item size="small" label="时间" label-width="80px">
              <el-date-picker type="daterange" v-model="expr.date" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" ></el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item size="small" label="公司" label-width="60px">
              <el-input placeholder="请输入公司名称" v-model="expr.company"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item size="small" label="职位" label-width="60px">
              <el-input placeholder="请输入职位" v-model="expr.job"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="2">
            <el-form-item size="small" label-width="0px">
              <el-button type="danger" @click="deleteExpr(index)">- 删除</el-button>
            </el-form-item>
          </el-col>
        </el-row>      
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="saveToS">保存</el-button>
      </div>
    </template>
  </el-dialog>
  </div>
</template>
<!-- ---------------------------------------------------------------------------------------------------------------------- -->
<style scoped>
h1 {
  color: rgba(0, 162, 255, 0.836);
}

.emp-c {
  margin: 20px 0;
  display: flex;
  justify-content: center;
}

.demo-pagination-block {
  margin: 20px 0;
  display: flex;
  justify-content: center;
}

.avatar {
  height: 60px;
}

.button{
  display: flex;
  justify-content: space-between;
}

.dialog-title {
  display: flex;
  align-items: center;
  font-weight: bold;
  font-size: 20px;  
  color: rgba(0, 162, 255, 0.836);
  gap: 10px;
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  text-align: center;
  border: 1px dashed var(--el-border-color);
}
</style>