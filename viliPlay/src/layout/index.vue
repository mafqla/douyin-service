<script setup lang="ts">
import {} from 'vue'
import AsideBar from '@/layout/AsideBar.vue'
</script>

<template>
  <div class="main">
    <el-container>
      <el-aside width="172px">
        <aside-bar />
      </el-aside>
      <el-container class="main-container">
        <el-header>Header</el-header>
        <el-main>
          <div class="page-info">
            <router-view v-slot="{ Component, route }">
              <keep-alive>
                <component :is="Component" :key="route.path" />
              </keep-alive>
            </router-view>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style lang="scss" scoped>
.main-container {
  width: calc(100% - 172px);
}
.el-header {
  height: 60px;
}
.el-main {
  padding: 0;
}
.page-info {
  width: 100%;
  height: 100%;
  padding: 25px;
}
</style>
