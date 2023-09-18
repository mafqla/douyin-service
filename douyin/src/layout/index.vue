<script setup lang="ts">
import { ref, watchEffect } from 'vue'
import AsideBar from '@/layout/AsideBar.vue'
import HeaderNav from '@/layout/HeaderNav.vue'
import { useRouter } from 'vue-router'
import { userStore } from '@/stores/user'
// 设置背景颜色
const backgroundColor = ref('none')
const isScrolled = ref(false)
// 滚动监听
window.addEventListener(
  'scroll',
  function () {
    // console.log(window.scrollY)
    if (window.scrollY > 60) {
      backgroundColor.value = '#fff'
      isScrolled.value = true
    } else {
      backgroundColor.value = 'none'
      isScrolled.value = false
    }
  },
  true
)
//获取路由地址
const router = useRouter()
const my = ref(false)
watchEffect(() => {
  if (router.currentRoute.value.path === '/user/self') {
    my.value = true
  } else {
    my.value = false
  }
})
</script>

<template>
  <div class="main" :class="{ user: my }">
    <div class="bg"></div>
    <aside-bar />

    <div class="right-container min">
      <el-affix class="affix">
        <el-header><header-nav :class="{ scrolled: isScrolled }" /></el-header>
      </el-affix>

      <router-view v-slot="{ Component, route }" :key="userStore().routerKey">
        <!-- <keep-alive> -->
        <component :is="Component" :key="route.path" />
        <!-- </keep-alive> -->
      </router-view>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.main {
  background-color: #fff;
  background-position: top;
  background-size: cover;
  display: flex;
  flex-direction: row;
  height: 100vh;
  width: 100%;

  &.user {
    height: auto;

    .bg {
      display: none;
    }
    :deep(.aside .aside-bar) {
      background: transparent !important;
    }
    .affix {
      background: unset !important;
    }
  }
  .bg {
    background: no-repeat url(@/assets/test.png) rgba(255, 255, 255, 1);
    height: 100vh;
    position: fixed;
    width: 100vw;
  }
}
.right-container {
  // width: calc(100% - $sidebar-width);
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  overflow: hidden;
  position: relative;
  width: 100%;
  height: 100%;

  &.min {
    min-height: 450px;
  }
}
.el-header {
  // height: 60px;
  height: 68px;
  position: sticky;
  z-index: 1;
  padding: 0;
  background-color: v-bind(backgroundColor);
}
.affix {
  background: no-repeat url(@/assets/test.png) rgba(255, 255, 255, 1);
  background-position-x: -72px;
}

@media (min-width: 1240px) {
  .affix {
    background-position-x: calc(160px * -1);
  }
}
</style>
