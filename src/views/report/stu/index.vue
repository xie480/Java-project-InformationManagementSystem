<script setup>
import { onMounted, ref } from "vue";
import * as echarts from 'echarts';
import { reportStuDegree, reportStuClazz } from "../../../api/report";

// 第一个图表
const chartDom1 = ref(null);
let myChart1;

// 第二个图表
const chartDom2 = ref(null);
let myChart2;

const getReportStuDegree = async () => {
  const result = await reportStuDegree();
  if (result.code == 1) {
    const data = result.data;
    const seriesData = [];
    // 遍历返回的数据数组
    for (const item of data) {
      seriesData.push({
        value: item.degreeCount,
        name: item.degree
      });
    }
    console.log(seriesData);
    myChart1.setOption({
      series: [{
        data: seriesData,
        type: 'pie'
      }]
    });
  }
}

const getReportStuClazz = async () => {
  const result = await reportStuClazz();
  console.log(result);
  if (result.code == 1) {
    const data = result.data;
    const source = [];
    // 遍历返回的数据数组并构造源数据
    for (const item of data) {
      source.push({
        amount: item.count,
        product: item.clazzName
      });
    }
    console.log(source);
    myChart2.setOption({
      title: {
        text: '班级人数统计',
        left: 'center',
        top: 20,
        textStyle: {
          color: 'rgba(58, 59, 59, 0.836)',
        }
      },
      dataset: {
        source: source,  // 将返回的 source 数据传入
      },
      grid: { containLabel: true },
      xAxis: { name: '人数' },
      yAxis: { type: 'category' },
      visualMap: {
        orient: 'horizontal',
        left: 'center',
        min: 0,
        max: 10,
        text: ['10', '0'],
        dimension: 0, // 这里的dimension应该映射到 `score` 或 `amount`
        inRange: {
          color: ['#65B581', '#FFCE34', '#FD665F']
        }
      },
      series: [
        {
          type: 'bar',
          encode: {
            x: 'amount',
            y: 'product'
          },
          radius: '90%',
          center: ['50%', '80%'],
        }
      ]
    });
  }
}

onMounted(() => {
  // 初始化第一个图表
  if (chartDom1.value) {
    myChart1 = echarts.init(chartDom1.value);
    getReportStuDegree();
    const option1 = {
      title: {
        text: '学历统计',
        left: 'center',
        top: 20,
        textStyle: {
          color: 'rgba(58, 59, 59, 0.836)',
        }
      },
      tooltip: {
        show: true,
        trigger: 'item'
      },
      series: [
        {
          roseType: 'area',
          name: '学历统计',
          type: 'pie',
          radius: '70%',
          center: ['50%', '60%'],
          data: [],
          label: {
            color: 'rgba(111, 119, 124, 0.836)'
          },
          labelLine: {
            lineStyle: {
              color: 'rgba(111, 119, 124, 0.836)'
            },
            smooth: 0.2,
            length: 10,
            length2: 20
          }
        }
      ]
    };
    myChart1.setOption(option1);
  }

  // 初始化第二个图表
  if (chartDom2.value) {
    myChart2 = echarts.init(chartDom2.value);
    getReportStuClazz();
  }
});
</script>

<template>
  <h1>学员信息统计</h1>
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
  gap: 20px; /* 控制两个图表之间的间隔 */
}

h1 {
  color: rgba(0, 162, 255, 0.836);
}
</style>
