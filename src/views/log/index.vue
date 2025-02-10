<script setup>
import { ref,onMounted,watch } from "vue";
import {findAllLog} from "../../api/log";

const dataMessage = ref({
  operateEmpName: '',
  begin: '',
  end: '',
  operateTime: [],
  className: '',
  methodName: '',
  methodParams: '',
  returnValue: '',
  costTime: ''
});

const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

let dataList = ref([]);

onMounted(() => {
  searchAll();
});

const handleSizeChange = (val) => {
  searchAll()
}

const handleCurrentChange = (val) => {
  searchAll()
}

watch(() => dataMessage.value.operateTime, (newValue, oldValue) => {
  if (newValue.length == 2) {
    dataMessage.value.begin = newValue[0];
    dataMessage.value.end = newValue[1];
  } else {
    dataMessage.value.begin = '';
    dataMessage.value.end = '';
  }
});

const searchAll = async() => {
  const result = await findAllLog(dataMessage.value.operateEmpName,dataMessage.value.begin,dataMessage.value.end, currentPage.value, pageSize.value);
  if (result.code == 1) {
    dataList.value = result.data.rows;
    total.value = result.data.total;
  } else {
    ElMessage.error(result.msg);
  }
}

const onReset = () => {
  dataMessage.value = {
    operateEmpName: '',
    begin: '',
    end: '',
    operateTime: [],
    className: '',
    methodName: '',
    methodParams: '',
    returnValue: '',
    costTime: ''
  }
  searchAll()
}
</script>
<!-- ---------------------------------------------------------------------------------------------------------------------- -->
<template>
  <h1>日志管理</h1>

  <div class="log-c">
  <el-form :inline="true" :model="dataMessage" class="demo-form-inline">
    <el-form-item label="操作人员姓名">
      <el-input v-model="dataMessage.operateEmpName" placeholder="请输入操作人员姓名" clearable />
    </el-form-item>
    <el-form-item label="操作时间">
      <el-date-picker
        v-model="dataMessage.operateTime"
        type="datetimerange"
        range-separator="至"
        start-placeholder="开始时间"
        end-placeholder="结束时间"
        value-format="YYYY-MM-DDTHH:mm:ss"
      />
    </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchAll">查询</el-button>
        <el-button type="info" @click="onReset">重置</el-button>
      </el-form-item>
  </el-form>
  </div>

  <div class="emp-c">
    <el-table :data="dataList" border stripe height="490" style="width: 100%">
      <el-table-column type="index" label="序号" width="55" align="center"/>
      <el-table-column property="operateEmpName" label="操作人员姓名" width="120" align="center">
        <template #default="scope">
          <span v-if="scope.row.operateEmpName == null">该人员数据已被删除</span>
        </template>
      </el-table-column>
      <el-table-column property="operateTime" label="操作时间" width="170" align="center"/>
      <el-table-column property="className" show-overflow-tooltip label="类名" width="250" align="center"/>
      <el-table-column property="methodName" show-overflow-tooltip label="方法名称" width="250" align="center"/>
      <el-table-column property="methodParams" show-overflow-tooltip label="参数" width="250" align="center"/>
      <el-table-column property="returnValue" show-overflow-tooltip label="返回值" width="250" align="center"/>
      <el-table-column property="costTime" label="耗时/ms" align="center"/>
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
</template>
<!-- ---------------------------------------------------------------------------------------------------------------------- -->
<style scoped>
h1 {
  color: rgba(0, 162, 255, 0.836);
}

.log-c {
  display: flex;
  justify-content: center;
  margin: 20px 0;
}

.demo-pagination-block{
  display: flex;
  justify-content: center;
  margin: 20px 0;
}
</style>