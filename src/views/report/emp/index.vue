<script setup>
import { onMounted, ref } from "vue";
import * as echarts from 'echarts';
import { reportEmpGender,reportEmpJob } from "../../../api/report";

// 第一个图表
const chartDom1 = ref(null);
let myChart1;

// 第二个图表
const chartDom2 = ref(null);
let myChart2;

// 获取性别统计数据
const getReportEmpGender = async () => {
  const result = await reportEmpGender();
  if (result.code == 1) {
    const data = result.data;
    const seriesData = [];
    // 遍历返回的数据数组
    for (const item of data) {
      // 将每个数据项的gender和count构造成对象形式，并添加到seriesData数组中
      seriesData.push({
        value: item.count,
        name: item.gender
      });
    }
    myChart1.setOption({
      series: [{
        data: seriesData,
        itemStyle: {
          color: (params) => {
            // 根据数据项的索引设置颜色
            if (params.dataIndex === 0) {
              return 'rgba(104, 255, 255, 0.836)';
            } else if (params.dataIndex === 1) {
              return 'rgba(255, 190, 254, 0.838)';
            }
          }
        }
      }]
    });
  }
};

// 获取职位统计数据
const getReportEmpJob = async () => {
  const result = await reportEmpJob();
  console.log(result);
  if (result.code == 1) {
    const data = result.data;
    const xAxisData = [];
    const seriesData = [];
    // 遍历返回的数据数组
    for (const item of data) {
      xAxisData.push(item.job);
      seriesData.push(item.count);
    }
    console.log(xAxisData, seriesData);
    myChart2.setOption({
      xAxis: {
        data: xAxisData
      },
      series: [{
        data: seriesData
      }]
    });
  }
};
onMounted(() => {
// 初始化第一个图表
if (chartDom1.value) {
    myChart1 = echarts.init(chartDom1.value);
    getReportEmpGender();
    const option1 = {
      title: {
        text: '性别统计',
        left: 'center',
        top: 20,
        textStyle: {
          color: 'rgba(58, 59, 59, 0.836)',
          fontSize: 20
        }
      },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '40%',
          left: 'center',
          itemWidth: 50,
          itemHeight: 15
        },
        series: [
          {
            name: '性别统计',
            type: 'pie',
            radius: ['40%', '90%'],
            center: ['50%', '98%'],
            // adjust the start and end angle
            startAngle: 180,
            endAngle: 360,
            data: []
          }
        ]
};

    myChart1.setOption(option1);
   }

  // 初始化第二个图表
  if (chartDom2.value) {
    myChart2 = echarts.init(chartDom2.value);
    getReportEmpJob();
    const option2 = {
      title: {
        text: '职位统计',
        left: 'center',
        top: 20,
        textStyle: {
          color: 'rgba(58, 59, 59, 0.836)',
          fontSize: 20
        }
      },
      tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '0%',
    containLabel: true
  },
  xAxis: [
    {
      type: 'category',
      data: [],
      axisTick: {
        alignWithLabel: true
      }
    }
  ],
  yAxis: [
    {
      type: 'value'
    }
  ],
  series: [
    {
      name: '职位统计',
      type: 'bar',
      barWidth: '60%',
      data: []
    }
  ],
    };
    myChart2.setOption(option2);
  }
});
</script>

<template>
  <h1>员工信息统计</h1>
  <div class="charts-container">
    <!-- 第一个图表 -->
    <div ref="chartDom1" class="chart" style="width: 700px; height: 600px;"></div>
    <!-- 第二个图表 -->
    <div ref="chartDom2" class="chart" style="width: 700px; height: 600px;"></div>
  </div>
</template>

<style scoped>
.charts-container {
  display: flex;
  justify-content: space-between;
  gap: 20px; /* 控制两个图表之间的间隔 */
}

.chart {
  flex: 1; /* 使图表容器自适应宽度 */
  min-width: 600px; /* 设置最小宽度，防止在小屏幕上图表过小 */
}

h1 {
  color: rgba(0, 162, 255, 0.836);
}
</style>