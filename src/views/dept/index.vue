<script setup>
import { ElMessage } from 'element-plus';
import { findAllDept,saveDept,findByIdDept,updateDept,deleteDept } from '../../api/depts';
import {ref,onMounted} from 'vue';

let tableData = ref([]);

let dept = ref({name:''});

const dialogTitle = ref('');

const dialogFormVisible = ref(false);

const deptFormRef = ref();

const deleteById = async(id) =>{
  const result = await deleteDept(id);
  if(result.code == 1){
    ElMessage.success('删除成功');
    searchAll();
  }
  else{
    ElMessage.error(result.msg);
  }
}


const findById = async(id) =>{
  const result = await findByIdDept(id);
  if(result.code == 1){
    dept.value = result.data;
    dialogTitle.value = '修改部门';
    dialogFormVisible.value = true;
    if (deptFormRef.value)deptFormRef.value.resetFields();
  }else{
    ElMessage.error(result.msg);
  }
}

const save = () =>{
  dialogTitle.value = '新增部门';
  dialogFormVisible.value = true;
  dept.value = {name:''};
  if (deptFormRef.value)deptFormRef.value.resetFields();
}

const saveToS = async() =>{
    if(!deptFormRef.value){
      return;
    }
    deptFormRef.value.validate(async(vaild) => {
      if(vaild){
        let result = null;
        if(dept.value.id){
          result = await updateDept(dept.value);
        }else{
          result = await saveDept(dept.value);
        }
        
        if(result.code == 1){
          ElMessage.success('操作成功');
          dialogFormVisible.value = false;
          searchAll();
        }
        else{
          ElMessage.error(result.msg);
        }
      }
    })
  };

onMounted(() => {
  searchAll();
})

const searchAll = async() =>{
  const result = await findAllDept();
  if(result.code == 1){
    tableData.value = result.data;
  }
}

const rules = ref({
  name: [
    { required: true, message: '该项为必填项', trigger: 'blur' },
    { min: 2, max: 10, message: '部门名称的长度应该在2-10位之间', trigger: 'blur' },
  ]});

</script>
<!-- ---------------------------------------------------------------------------------------------------------------------- -->
<template>
  <h1>部门管理</h1>

  <div class="dept-c">
    <el-button type="success" round style="float: right;" @click="save">+  新增部门</el-button>
  </div>

  <div class="dept-d">
    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column type="index" label="序号" width="100" align="center"/>
      <el-table-column prop="name" label="部门名称" width="300" align="center"/>
      <el-table-column prop="updateTime" label="最后操作时间" width="460" align="center"/>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" @click="findById(scope.row.id)">
            <el-icon><CirclePlusFilled /></el-icon>编辑
          </el-button>
          <el-popconfirm
            width="220"
            :icon="InfoFilled"
            icon-color="#626AEF"
            title="确定要删除该部门吗?"
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

  <el-dialog v-model="dialogFormVisible" width="500">
    <template #title>
      <div class="dialog-title">
        <el-icon><Promotion /></el-icon>
        {{ dialogTitle }}
      </div>
    </template>
    <el-form :model="dept" :rules="rules" ref="deptFormRef">
      <el-form-item label="部门名称" label-width="80px" prop="name">
        <el-input v-model="dept.name" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="saveToS">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>
<!-- ---------------------------------------------------------------------------------------------------------------------- -->
<style scoped>
h1 {
  color: rgba(0, 162, 255, 0.836);
}

.dept-c {
 margin: 20px 0;
}

.dept-d {
  margin: 60px 0;
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
