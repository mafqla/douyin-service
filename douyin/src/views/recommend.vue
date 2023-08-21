<script setup lang="ts">
import { ref } from 'vue'
import SwiperControl from '@/components/swper/swiper-control.vue'
import miniPlayer from '@/components/video-player/mini-player.vue'
import SwiperVideo from '@/components/swper/swiper-video.vue'

const loading = ref(true)
</script>
<template>
  <div class="recommend">
    <Loading :show="loading" :isShowText="true" :center="true">
      <!-- <mini-player
      url="http://172.16.1.36:8081/res/3aeeaf84-c38c-4409-aebe-7912b05ee39a.mp4"
    /> -->
      <swiper-control />
    </Loading>
  </div>
</template>

<style lang="scss" scoped>
.recommend {
  width: 100%;
  height: 80vh;
  position: relative;
}
</style>
