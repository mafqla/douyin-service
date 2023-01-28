<template>
  <div class="aside-bar">
    <div class="aside-logo">
      <span>viliplay</span>
    </div>

    <div class="aside-container">
      <el-menu class="el-menu-container">
        <el-menu-item index="1" @click="handleSelect">
          <template #title>首页</template>
        </el-menu-item>
        <el-menu-item index="2" @click="handleSelect">
          <template #title>推荐</template>
        </el-menu-item>
        <el-menu-item index="3" @click="handleSelect">
          <template #title>关注</template>
        </el-menu-item>
        <el-menu-item index="4" @click="handleSelect">
          <template #title>我的</template>
        </el-menu-item>
      </el-menu>

      <div class="aside-bottom"></div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
//路由跳转
import { useRouter } from 'vue-router'
const router = useRouter()

const activeIndex = ref('2')

interface IRouterIndex {
  [index: string]: string
}

const routerIndex: IRouterIndex = {
  1: '/discover',
  2: '/recommend',
  3: '/follow',
  4: '/my'
}
const handleSelect = (index: string) => {
  activeIndex.value = index
  router.push(routerIndex[activeIndex.value.index])
}
</script>

<style lang="scss" scoped>
.aside-bar {
  width: 100%;
  height: 100vh;
  background: $color-navigation-bg;
  position: relative;

  .aside-logo {
    height: 60px;
    user-select: none;
  }

  .aside-container {
    background-position: 0 100%;
    background-size: cover;
    bottom: 0;
    height: calc(100vh - 60px);
    outline: none;
    position: fixed;
    scrollbar-width: none;
    width: 172px;
    z-index: 20;

    .el-menu-container:not(.el-menu--collapse) {
      display: flex;
      flex-direction: column;
      align-items: center;
      width: 172px;
      height: 100%;
      position: relative;
      user-select: none;
      background: $color-navigation-bg;
    }

    .aside-bottom {
      width: 172px;
      height: 540px;
      display: block;
      position: absolute;
      bottom: -400px;
      z-index: 1;
    }
  }
}
</style>
