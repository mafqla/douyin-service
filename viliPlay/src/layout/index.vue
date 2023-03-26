<script setup lang="ts">
import {} from 'vue'
import AsideBar from '@/layout/AsideBar.vue'
import HeaderNav from '@/layout/HeaderNav.vue'
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
  z-index: 1;
  padding: 0;
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
