<script setup lang="ts">
import { ref } from 'vue'
import AsideBar from '@/layout/AsideBar.vue'
import HeaderNav from '@/layout/HeaderNav.vue'

// 设置背景颜色
const backgroundColor = ref('none')

// 滚动监听
window.addEventListener('scroll', function () {
  if (window.scrollY > 60) {
    backgroundColor.value = '#fff'
  } else {
    backgroundColor.value = 'none'
  }
})


</script>

<template>
  <div class="main">
    <el-container>
      <el-aside>
        <aside-bar />
      </el-aside>
      <el-container class="main-container">
        <el-affix>
          <el-header><header-nav /></el-header>
        </el-affix>
        <el-main>
          <div class="page-info">
            <router-view v-slot="{ Component, route }">
              <!-- <keep-alive> -->
              <component :is="Component" :key="route.path" />
              <!-- </keep-alive> -->
            </router-view>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style lang="scss" scoped>
.el-aside {
  width: $sidebar-width;
  z-index: 2;
}
.main-container {
  width: calc(100% - $sidebar-width);
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  // height: 100%;
  overflow: hidden;
  position: relative;
}
.el-header {
  height: 60px;
  position: sticky;
  z-index: 1;
  padding: 0;
  background-color: v-bind(backgroundColor);
}
.el-main {
  padding: 0;
  // flex: 1 1 0%;
  // display: flex;
  // flex-direction: column;
  // min-height: 100%;
  // width: 100%;
}
.page-info {
  width: 100%;
  height: 100%;
  // min-height: 450px;
}

@media screen and (max-width: 1240px) {
  .el-aside {
    width: $sidebar-width-min;
  }
  .main-container {
    width: calc(100% - $sidebar-width-min);
  }
}
</style>
