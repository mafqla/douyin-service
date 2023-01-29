<script setup lang="ts">
import { ref } from 'vue'

//切换深色主题
const isDark = ref(false)

//切换主题
const toggleTheme = () => {
  isDark.value = !isDark.value
}

//切换侧边栏
const toggleSidebar = () => {
  console.log('toggleSidebar')
}
</script>

<template>
  <div class="header">header</div>
</template>

<style lang="scss" scoped></style>
