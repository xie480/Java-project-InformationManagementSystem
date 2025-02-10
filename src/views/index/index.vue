<template>
  <el-carousel :interval="4000" type="card" height="200px" autoplay="true" pause-on-hover="true" motion-blur="true">
    <el-carousel-item v-for="item in imgList" :key="item.id">
      <img style="object-fit: scale-down;width:100%;height:100%;"  :src="item.idView" alt="carousel image" class="carousel-image">
    </el-carousel-item>
  </el-carousel>
    <div class="main-content">
    <img src="../../assets/index.png" alt="首页图片" class="main-image">
  </div>
</template>
<!-- ---------------------------------------------------------------------------------------------------------------------- -->
<script setup>
import { ref } from "vue";
import img1 from '@/assets/pic/1.png';
import img2 from '@/assets/pic/2.png';
import img3 from '@/assets/pic/3.png';
import img4 from '@/assets/pic/4.png';
import img5 from '@/assets/pic/5.png';

const imgList = ref([
  { id: 0, idView: img1 },
  { id: 1, idView: img2 },
  { id: 2, idView: img3 },
  { id: 3, idView: img4 },
  { id: 4, idView: img5 }
]);
</script>
<!-- ---------------------------------------------------------------------------------------------------------------------- -->
<style scoped>
.main-content {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  margin: -120px 0;
}

.main-image {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain; /* 保持图片比例并适应容器 */
}

.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 200px;
  margin: 10px 0;
  text-align: center;
}
</style>