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
