<template>
  <div class="follow">
    <swiper-video>
      <template #slide>
        <carousel-controls />
      </template>
    </swiper-video>
  </div>
</template>

<script setup lang="ts">
import {} from 'vue'
</script>
<style lang="scss" scoped>
.follow {
  width: 100%;
  height: 100%;
  position: relative;
}
</style>
