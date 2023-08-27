<script setup lang="ts">
import { ref, watchEffect } from 'vue'
import AsideBar from '@/layout/AsideBar.vue'
import HeaderNav from '@/layout/HeaderNav.vue'
import { useRouter } from 'vue-router'

// 设置背景颜色
const backgroundColor = ref('none')

// 滚动监听
window.addEventListener(
  'scroll',
  function () {
    // console.log(window.scrollY)
    if (window.scrollY > 60) {
      backgroundColor.value = '#fff'
    } else {
      backgroundColor.value = 'none'
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
    <el-aside>
      <aside-bar />
    </el-aside>
    <div class="right-container min">
      <el-affix class="affix">
        <el-header><header-nav /></el-header>
      </el-affix>

      <router-view v-slot="{ Component, route }">
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
  }
}
.el-aside {
  width: $sidebar-width;
  z-index: 2;
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
  height: 60px;
  position: sticky;
  z-index: 1;
  padding: 0;
  background-color: v-bind(backgroundColor);
}

@media screen and (max-width: 1240px) {
  .el-aside {
    width: $sidebar-width-min;
  }
  // .affix {
  //   width: calc(100% - $sidebar-width-min);
  // }
}
</style>
