<script setup lang="ts">
import { ref } from 'vue'
import CarouselControls from '@/components/swper/swiper-control.vue'

const loading = ref(true)
</script>
<template>
  <div class="follow">
    <Loading :show="loading" :isShowText="true" :center="true">
      <swiper-video>
        <template #slide>
          <carousel-controls />
        </template>
      </swiper-video>
    </Loading>
  </div>
</template>

<style lang="scss" scoped>
.follow {
  width: 100%;
  height: 80vh;
  position: relative;
}

:deep(.carousel-controls) {
  .carousel-controls-switch {
    background-color: rgba(0, 0, 0, 0.18) !important;
    border: 0.5px solid hsla(0, 0%, 100%, 0.15) !important;
    width: 35px;

    .carousel-controls-switch-up,
    .carousel-controls-switch-down {
      &:hover {
        opacity: 1;
      }
    }
  }
}
</style>
