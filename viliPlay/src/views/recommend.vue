<script setup lang="ts">
import {} from 'vue'
</script>
<template>
  <div class="recommend">
    <swiper-video />
    <carousel-controls />
  </div>
</template>

<style lang="scss" scoped>
.recommend {
  width: 100%;
  height: 100%;
  position: relative;

  .carousel-controls {
    position: absolute;
    right: 13px;
    top: 5%;
    transform: translateY(-50%);
  }
}
</style>
