<script setup>
import { ref, onMounted,watch } from "vue";
import { findAllClazz,saveClazz,findByIdClazz,updateClazzs,deleteClazz } from "../../api/clazz";
import { ElMessage } from "element-plus";

let clazz = ref({
  name:'',
  beginDate: '',
  endDate: '',
  date: []
  });

let tableData = ref([]);

const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

const currentTime = ref('');

const dialogFormVisible = ref(false);

let updateClazz = ref({
  name: '',
  room: '',
  beginDate: '',
  endDate: '',
  subject: '',
  masterId: '',
  date: []
});

let dialogTitle = ref('');

const clazzFormRef = ref(null);

const save = () => {
  dialogTitle.value = '新增班级';
  dialogFormVisible.value = true;
  updateClazz.value = {
    name: '',
    room: '',
    beginDate: '',
    endDate: '',
    subject: '',
    masterId: '',
    date: []
  };
  if (clazzFormRef.value) clazzFormRef.value.resetFields();
}

onMounted(() => {
  const now = new Date();
  currentTime.value = now;
  searchAll();
});

const searchAll = async () => {
  const result = await findAllClazz(clazz.value.name,clazz.value.beginDate,clazz.value.endDate, currentPage.value, pageSize.value);
  if (result.code == 1) {
    tableData.value = result.data.rows;
    total.value = result.data.total;
  }
}

const onReset = () => {
  // 使用 Object.assign 来避免直接赋值导致响应式丢失
  Object.assign(clazz.value, {
    name: '',
    beginDate: '',
    endDate: '',
    date: []
  });
  searchAll();
}

// 正确使用 watch 监听 clazz.date 的变化
watch(() => clazz.value.date, (newValue, oldValue) => {
  if (newValue && newValue.length > 0) {
    clazz.value.beginDate = newValue[0];
    clazz.value.endDate = newValue[1];
  } else {
    clazz.value.beginDate = '';
    clazz.value.endDate = '';
  }
});

const handleSizeChange = (val) => {
  searchAll()
}

const handleCurrentChange = (val) => {
  searchAll()
}

const saveToS = async() => {
  if (!clazzFormRef.value) {
    return;
  }
  clazzFormRef.value.validate(async(valid) => {
    if (valid){
      let result = null;
      if (updateClazz.value.id){
        result = await updateClazzs(updateClazz.value);
      }else{
        result = await saveClazz(updateClazz.value);
      }
      if (result.code == 1) {
        ElMessage.success('操作成功');
        dialogFormVisible.value = false;
        searchAll();
      } else {
        ElMessage.error(result.msg);
      }
    }
  });
} 
 


watch(() => updateClazz.value.date,(newValue,oldValue) => {
  if(newValue && newValue.length > 0){
    updateClazz.value.beginDate = newValue[0];
    updateClazz.value.endDate = newValue[1];
  }else{
    updateClazz.value.beginDate = '';
    updateClazz.value.endDate = '';
  }
});

const rules = ref({
  name: [
    { required: true, message: '该项为必填项', trigger: 'blur' },
    { min: 3, max: 20, message: '班级名称长度应在3-20个字符长度之间', trigger: 'blur' }
  ],
  room: [
    { required: true, message: '该项为必填项', trigger: 'blur' },
    { min: 1, max: 20, message: '房价名称长度应在1-20个字符长度之间', trigger: 'blur' }
  ],
  subject: [
    { required: true, message: '该项为必填项', trigger: 'change' },
    { min: 1, max: 3, message: '学科名称长度应在1-3个字符长度之间', trigger: 'blur' }
  ],
  date: [
    { required: true, message: '该项为必填项', trigger: 'blur' }
  ],
  masterId: [
    { required: true, message: '该项为必填项', trigger: 'blur' }
  ]
  });

const findById = async(id) => {
  const result = await findByIdClazz(id);
  if (result.code == 1) {
    dialogTitle.value = '修改班级';
    dialogFormVisible.value = true;
    updateClazz.value = result.data;
    updateClazz.value.date = [result.data.beginDate, result.data.endDate];
  } else {
    ElMessage.error(result.msg);
  }
  if (clazzFormRef.value)clazzFormRef.value.resetFields();
}

const deleteById = async(id) => {
  const result = await deleteClazz(id);
  if (result.code == 1) {
    ElMessage.success('删除成功');
    searchAll();
  } else {
    ElMessage.error(result.msg);
  }
}
</script>
<!-- ---------------------------------------------------------------------------------------------------------------------- -->
<template>
 <h1>班级管理</h1>

 <div class="class-c">
  <el-form :inline="true" :model="clazz" class="demo-form-inline">
    <el-form-item label="班级名称">
      <el-input v-model="clazz.name" placeholder="请输入班级名称" clearable />
    </el-form-item>
    <el-form-item label="教学时间范围">
      <el-date-picker
        v-model="clazz.date"
        type="daterange"
        range-separator="至"
        start-placeholder="开课日期"
        end-placeholder="结课日期"
        value-format="YYYY-MM-DD"
      ></el-date-picker>
    </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchAll">查询</el-button>
        <el-button type="info" @click="onReset">重置</el-button>
      </el-form-item>
  </el-form>
 </div>

 <div class="button-add">
  <el-button type="success" round @click="save">+  新增班级</el-button>
 </div>

 <div class="clazz-c">
  <el-table :data="tableData" border stripe height="500" style="width: 100%">
    <el-table-column type="index" label="序号" width="60" align="center"/>
    <el-table-column prop="name" label="班级名称" width="180" align="center"/>
    <el-table-column prop="room" label="教室" width="90" align="center"/>
    <el-table-column prop="beginDate" label="开课日期" width="180" align="center"/>
    <el-table-column prop="endDate" label="结课日期" width="180" align="center"/>
    <el-table-column prop="subject" label="学科" width="85" align="center"/>
    <el-table-column prop="createTime" label="创建时间" width="180" align="center"/>
    <el-table-column prop="updateTime" label="最后操作时间" width="180" align="center"/>
    <el-table-column prop="masterName" label="班主任姓名" width="100"align="center" />
    <el-table-column prop="status" label="状态" align="center"width="80">
      <template #default="scope">
        <el-tag v-if="new Date(currentTime) > new Date(scope.row.endDate)" type="danger">已结课</el-tag>
        <el-tag v-else-if="new Date(currentTime) < new Date(scope.row.beginDate)" type="info">未开课</el-tag>
        <el-tag v-else type="success">未结课</el-tag>
      </template>
    </el-table-column>
    <el-table-column label="操作" align="center">
  <template #default="scope">
    <div class="button-container1">
      <el-button type="primary" size="small" @click="findById(scope.row.id)">
        <el-icon><CirclePlusFilled /></el-icon>编辑
      </el-button>
      </div>
      <div class="button-container2">
      <el-popconfirm
        width="220"
        :icon="InfoFilled"
        icon-color="#626AEF"
        title="确定要删除该班级吗?"
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

  <el-dialog v-model="dialogFormVisible" width="500">
    <template #title>
      <div class="dialog-title">
        <el-icon><Promotion /></el-icon>
        {{ dialogTitle }}
      </div>
    </template>
    <el-form :model="updateClazz" ref="clazzFormRef" :rules="rules">
      <el-form-item label="班级名称" :label-width="formLabelWidth" prop="name">
        <el-input v-model="updateClazz.name" autocomplete="off" />
      </el-form-item>
      <el-row>
      <el-col :span="12">
      <el-form-item label="教室&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" :label-width="formLabelWidth" prop="room">
        <el-input v-model="updateClazz.room" autocomplete="off" style="width: 95%;"/>
      </el-form-item>
      </el-col>
      <el-col :span="12">
      <el-form-item label="学科&nbsp;&nbsp;&nbsp;&nbsp;" :label-width="formLabelWidth" prop="subject">
        <el-input v-model="updateClazz.subject" autocomplete="off" style="width: 100%;"/>
      </el-form-item>
      </el-col>
      </el-row>
      <el-form-item size="small" label="教学时间范围" label-width="85px" prop="date">
        <el-date-picker type="daterange" v-model="updateClazz.date" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" ></el-date-picker>
      </el-form-item>
      <el-form-item label="班主任ID" :label-width="formLabelWidth" prop="masterId">
        <el-input v-model="updateClazz.masterId" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="saveToS">保存</el-button>
      </div>
    </template>
  </el-dialog>
</template>
<!-- ---------------------------------------------------------------------------------------------------------------------- -->
<style scoped>
h1 {
  color: rgba(0, 162, 255, 0.836);
}

.class-c {
  margin: 20px 0px;
  display: flex;
  justify-content: center;
}

.button-add {
  display: flex;
  justify-content: flex-end;
  margin: 10px 0px;
}

.demo-pagination-block {
  margin: 20px 0px;
  display: flex;
  justify-content: center;
}


.dialog-title {
  display: flex;
  align-items: center;
  font-weight: bold;
  font-size: 20px;  
  color: rgba(0, 162, 255, 0.836);
  gap: 10px;
}

.button-container1 {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 5px 0;
}


</style>