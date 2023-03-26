<script setup lang="ts">
import {} from 'vue'
import videoItem from './video-item.vue'

const videosList = [
  0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
  23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41,
  42, 43, 44, 45, 46, 47, 48, 49
]
</script>

<template>
  <div class="video-list">
    <!-- <video-item v-for="item in videosList" :key="item.id" :item="item" /> -->
    <video-item v-for="item in videosList" />
    <list-footer />
  </div>
</template>

<style lang="scss" scoped>
.video-list {
  width: 100%;
  display: inline-block;
}
</style>
