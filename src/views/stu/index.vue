<script setup>
import { ref,onMounted } from "vue";
import { findAllStudent,saveStudent,findByIdStudent,updateStudent,deleteStudent,addViolationScore } from "../../api/student";
import { ElMessage,ElMessageBox } from "element-plus";

const student = ref({
  name:'',
  degree: '',
  clazzId: ''
});

let updateStu = ref({
  name: '',
  no: '',
  gender: '',
  phone: '',
  degree: '',
  idCard: '',
  isCollege: '',
  address:'',
  graduationDate: '',
  violationScore: '',
  classId: ''
});

let tableData = ref([]);

const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

const dialogFormVisible = ref(false);
const dialogFormVisible1 = ref(false);

const dialogTitle = ref('');

const genders = ref([{ name: '男', value: 1 }, { name: '女', value: 2 }]);
const yn = ref([{ name: '是', value: 1 }, { name: '否', value: 0 }]);
const degrees = ref([{ name: '初中', value: 1 }, { name: '高中', value: 2 }, { name: '大专', value: 3 }, { name: '本科', value: 4 }, { name: '硕士', value: 5 }, { name: '博士', value: 6 }]);


  //记录勾选的员工的id
  const selectedIds = ref([]);

  const stuFormRef = ref(null);
//复选框勾选发生变化时触发 - selection: 当前选中的记录 (数组)
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map( item => item.id);
}

const searchAll = async () => {
  const result = await findAllStudent(student.value.name,student.value.degree,student.value.clazzId, currentPage.value, pageSize.value);
  if (result.code == 1) {
    tableData.value = result.data.rows;
    total.value = result.data.total;
  } else {
    ElMessage.error(result.msg);
  }
}

const handleSizeChange = (val) => {
  searchAll()
}

const handleCurrentChange = (val) => {
  searchAll()
}

onMounted(() => {
  searchAll();
});

const onReset = () => {
  student.value = {
    name:'',
    degree: '',
    clazzId: ''
  };
  searchAll();
}

const save = async () => {
  dialogTitle.value = '新增学员';
  dialogFormVisible.value = true;
  updateStu.value = {
    name: '',
    no: '',
    gender: '',
    phone: '',
    degree: '',
    idCard: '',
    isCollege: '',
    address:'',
    graduationDate: '',
    violationScore: '',
    classId: ''
  };
  if (stuFormRef.value){
    stuFormRef.value.resetFields();
  }
}

const saveToS = async () => {
  if(!stuFormRef.value)return;
  stuFormRef.value.validate(async (valid) => {
    if(valid){
      let result = null;
      if (updateStu.value.id){
        result = await updateStudent(updateStu.value);
      }else{
      result = await saveStudent(updateStu.value);
      }
      if (result.code == 1) {
        ElMessage.success('保存成功');
        dialogFormVisible.value = false;
        searchAll();
        } else {
        ElMessage.error(result.msg);
        }
    }
  });
}

const rules = ref({
  name: [
    { required: true, message: '该项为必填项', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度应在2到20个字符之间', trigger: 'blur' }
  ],
  no: [
    { required: true, message: '该项为必填项', trigger: 'blur' },
    { min: 2, max: 10, message: '学号长度应在2到10个字符之间', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '该项为必填项', trigger: 'change' },
    { pattern: /^1\d{10}$/g, message: '请输入有效的手机号', trigger: 'blur' }
  ],
  degree: [
    { required: true, message: '该项为必填项', trigger: 'blur' }
  ],
  idCard: [
    { required: true, message: '该项为必填项', trigger: 'blur' },
    { pattern: /^\d{17}[\dXx]$/g, message: '请输入有效的身份证号', trigger: 'blur' }
  ],
  isCollege: [
    { required: true, message: '该项为必填项', trigger: 'change' }
  ],
  graduationDate: [
    { required: true, message: '该项为必填项', trigger: 'blur' }
  ],
  clazzId: [
    { required: true, message: '该项为必填项', trigger: 'blur' },
    { min: 1, max: 5, message: '班级编号长度应在1到5个字符之间', trigger: 'blur' }
  ]
  });

const findById = async(id) => {
  const result = await findByIdStudent(id);
  if (result.code == 1) {
    dialogTitle.value = '修改学员';
    dialogFormVisible.value = true;
    updateStu.value = result.data;
  } else {
    ElMessage.error(result.msg);
  }
}

const deleteById = async(id) => {
  const result = await deleteStudent(id);
  if (result.code == 1) {
    ElMessage.success('删除成功');
    searchAll();
  } else {
    ElMessage.error(result.msg);
  }
}

const deleteAll = async() =>{
    //弹出确认框
  ElMessageBox.confirm('您确认删除所选中的学员吗?','提示',
    { confirmButtonText: '确认',cancelButtonText: '取消',type: 'warning'}
  ).then(async () => { //确认
    if(selectedIds.value && selectedIds.value.length > 0){
      const result = await deleteStudent(selectedIds.value);
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

let updateViolationScore = ref({
  id:'',
  name: '',
  violationScore: '',
  violationScore1: ''
});

  const addViolation = async(id) => {
    const result1 = await findByIdStudent(id);
    if(result1.code == 1) {
      updateViolationScore.value = result1.data;
      dialogFormVisible1.value = true;
    }else{
      ElMessage.error(result1.msg);
    }
  }

  const saveVToS = async() => {
    const result2 = await addViolationScore(updateViolationScore.value.id,updateViolationScore.value.violationScore1);
        if (result2.code == 1) {
          ElMessage.success('扣分成功');
          dialogFormVisible1.value = false;
          searchAll();
      } else {
          ElMessage.error(result.msg);
      }
  }
</script>
<!-- ---------------------------------------------------------------------------------------------------------------------- -->
<template>
  <h1>学员管理</h1>

  <div class="stu-c">
    <el-form :inline="true" :model="student" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="student.name" placeholder="请输入学员姓名" clearable />
      </el-form-item>
      <el-form-item label="学历">
        <el-select
          v-model="student.degree"
          placeholder="请选择"
          clearable
        >
          <el-option label="初中" value="1" />
          <el-option label="高中" value="2" />
          <el-option label="大专" value="3" />
          <el-option label="本科" value="4" />
          <el-option label="硕士" value="5" />
          <el-option label="博士" value="6" />
        </el-select>
      </el-form-item>
      <el-form-item label="班级">
        <el-input v-model="student.clazzId" placeholder="请输入班级编号" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchAll">查询</el-button>
        <el-button type="info" @click="onReset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>

  <div class="button"> 
    <el-button type="danger" round id="delete" @click="deleteAll">-  批量删除</el-button>
    <el-button type="success" round id="add" @click="save">+  新增学员</el-button>
  </div>

  <div class="stu-c">
  <el-table :data="tableData" border stripe height="490" style="width: 100%" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55" align="center"/>
    <el-table-column prop="name" fixed="left" label="学员名称" width="100" align="center"/>
    <el-table-column prop="no" label="学号" width="180" align="center"/>
    <el-table-column prop="gender" label="性别" width="60" align="center">
      <template #default="scope">
        <span v-if="scope.row.gender == 1">男</span>
        <span v-else>女</span>
      </template>
    </el-table-column>
    <el-table-column prop="phone" label="手机号" width="180" align="center"/>
    <el-table-column prop="degree" label="学历" width="85" align="center">
      <template #default="scope">
        <span v-if="scope.row.degree == 1">初中</span>
        <span v-else-if="scope.row.degree == 2">高中</span>
        <span v-else-if="scope.row.degree == 3">大专</span>
        <span v-else-if="scope.row.degree == 4">本科</span>
        <span v-else-if="scope.row.degree == 5">硕士</span>
        <span v-else>博士</span>
      </template>
    </el-table-column>
    <el-table-column prop="idCard" label="身份证号" width="300" align="center"/>
    <el-table-column prop="isCollege" label="是否来自院校" width="120" align="center">
      <template #default="scope">
        <span v-if="scope.row.isCollege == 1">是</span>
        <span v-else>否</span>
      </template>
    </el-table-column>
    <el-table-column prop="address" label="家庭住址" width="400"align="center" />
    <el-table-column prop="graduationDate" label="毕业日期" align="center"width="120"/>
    <el-table-column prop="violationCount" label="违纪次数" width="100"align="center" />
    <el-table-column prop="violationScore" label="违纪累计扣分" width="110"align="center" />
    <el-table-column prop="clazzName" label="班级名称" width="150"align="center" />
    <el-table-column prop="createTime" label="创建时间" width="180"align="center" />
    <el-table-column prop="updateTime" label="最后操作时间" width="180"align="center" />
    <el-table-column fixed="right" label="操作" align="center">
  <template #default="scope">
    <div class="button-container1">
      <el-button type="primary" size="small" @click="findById(scope.row.id)">
        <el-icon><CirclePlusFilled /></el-icon>编辑
      </el-button>
      </div>
      <div class="button-container2">
        <el-button type="info" size="small" @click="addViolation(scope.row.id,scope.row.violationScore)"><el-icon><List /></el-icon>扣分</el-button>
      </div>
      <div class="button-container3">
      <el-popconfirm
        width="220"
        :icon="InfoFilled"
        icon-color="#626AEF"
        title="确定要删除该学员吗?"
        @cancel="onCancel"
        @confirm="deleteById(scope.row.id)"
      >
        <template #reference>
          <el-button type="danger" size="small">
            <el-icon><DeleteFilled /></el-icon>删除
          </el-button>
        </template>
      </el-popconfirm>
    </div>
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

  <el-dialog v-model="dialogFormVisible" width="800">
    <template #title>
      <div class="dialog-title">
        <el-icon><Promotion /></el-icon>
        {{ dialogTitle }}
      </div>
    </template>
    <el-form :model="updateStu" ref="stuFormRef" :rules="rules">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="学员姓名" :label-width="formLabelWidth" prop="name">
            <el-input v-model="updateStu.name" autocomplete="off" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
      <el-form-item label="学号" :label-width="formLabelWidth" prop="no">
        <el-input v-model="updateStu.no" autocomplete="off"/>
      </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
      <el-form-item label="性别&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" :label-width="formLabelWidth" prop="gender">
        <el-select v-model="updateStu.gender" placeholder="请选择" style="width: 100%;">
            <el-option v-for="gender in genders" :key="gender.name" :label="gender.name" :value="gender.value"></el-option>
        </el-select>
      </el-form-item>
    </el-col>
    <el-col :span="12">
      <el-form-item label="手机号" :label-width="formLabelWidth" prop="phone">
        <el-input v-model="updateStu.phone" autocomplete="off"/>
      </el-form-item>
    </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
      <el-form-item label="身份证号" :label-width="formLabelWidth" prop="idCard">
        <el-input v-model="updateStu.idCard" autocomplete="off"/>
      </el-form-item>
    </el-col>
    <el-col :span="12">
      <el-form-item label="是否来自院校" :label-width="formLabelWidth" prop="isCollege">
        <el-select v-model="updateStu.isCollege" placeholder="请选择" style="width: 100%;">
        <el-option v-for="choose in yn" :key="choose.name" :label="choose.name" :value="choose.value"></el-option>
      </el-select>
      </el-form-item>
    </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
      <el-form-item label="家庭地址" :label-width="formLabelWidth" prop="address">
        <el-input v-model="updateStu.address" autocomplete="off"/>
      </el-form-item>
     </el-col>
     <el-col :span="12">
      <el-form-item label="学历" :label-width="formLabelWidth" prop="degree">
        <el-select v-model="updateStu.degree" placeholder="请选择" style="width: 100%;">
        <el-option v-for="degree in degrees" :key="degree.name" :label="degree.name" :value="degree.value"></el-option>
        </el-select>
      </el-form-item>
      </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
      <el-form-item label="毕业日期" :label-width="formLabelWidth" prop="graduationDate">
        <el-date-picker v-model="updateStu.graduationDate" type="date" style="width: 100%;" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
      </el-form-item>
    </el-col>
    <el-col :span="12">
      <el-form-item label="班级ID" :label-width="formLabelWidth" prop="clazzId">
        <el-input v-model="updateStu.clazzId" autocomplete="off"/>
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

  <el-dialog v-model="dialogFormVisible1" width="500">
    <template #title>
      <div class="dialog-title">
        <el-icon><Promotion /></el-icon>违纪扣分</div>
    </template>
    <el-form :model="updateViolationScore">
      <el-form-item label="现在的分数:" :label-width="formLabelWidth">
        <span>{{ updateViolationScore.violationScore }}</span>
      </el-form-item>
      <el-form-item label="新增扣分" :label-width="formLabelWidth">
        <el-input v-model="updateViolationScore.violationScore1" autocomplete="off" placeholder="此处填写所要扣去的分数大小"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false">取消</el-button>
        <el-button type="primary" @click="saveVToS">保存</el-button>
      </div>
    </template>
  </el-dialog>
</template>
<!-- ---------------------------------------------------------------------------------------------------------------------- -->
<style scoped>
h1 {
  color: rgba(0, 162, 255, 0.836);
}

.stu-c {
  display: flex;
  justify-content: center;
  margin: 20px 0;
}

.button{
  display: flex;
  justify-content: space-between;
}

.demo-pagination-block{
  margin: 20px 0;
  display: flex;
  justify-content: center;
}

.button-container1 {
  margin: 5px 0;
}

.dialog-title {
  display: flex;
  align-items: center;
  font-weight: bold;
  font-size: 20px;  
  color: rgba(0, 162, 255, 0.836);
  gap: 10px;
}
</style>