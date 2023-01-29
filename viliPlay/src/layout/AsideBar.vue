<script lang="ts" setup>
import { computed, ref } from 'vue'
//路由跳转
import { useRoute, useRouter } from 'vue-router'
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
  //@ts-ignore
  router.push(routerIndex[activeIndex.value.index])
}

// 计算高亮 menu 的方法
const route = useRoute()
const activeMenu = computed(() => {
  const { path } = route
  const index = Object.values(routerIndex).findIndex((item) => item === path)
  return index + 1 + ''
})
console.log(activeMenu.value)
</script>
<template>
  <div class="aside-bar">
    <div class="aside-logo">
      <span>viliplay</span>
    </div>

    <div class="aside-container">
      <el-scrollbar>
        <el-menu class="el-menu-container" :default-active="activeMenu">
          <el-menu-item index="1" @click="handleSelect">
            <template #title>
              <svg-icon class="icon" icon="discover" />
              <span class="title">首页</span>
            </template>
          </el-menu-item>
          <el-menu-item index="2" @click="handleSelect">
            <template #title>
              <svg-icon class="icon" icon="recommend" />
              <span class="title">推荐</span>
            </template>
          </el-menu-item>
          <el-menu-item index="3" @click="handleSelect">
            <template #title>
              <svg-icon class="icon" icon="follow" />
              <span class="title">关注</span>
            </template>
          </el-menu-item>
          <el-menu-item index="4" @click="handleSelect">
            <template #title>
              <svg-icon class="icon" icon="my" />
              <span class="title">我的</span>
            </template>
          </el-menu-item>
        </el-menu>
      </el-scrollbar>

      <div class="aside-bottom"></div>
    </div>
  </div>
</template>

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
    width: $sidebar-width;
    z-index: 20;

    .el-menu-container {
      display: flex;
      flex-direction: column;
      align-items: center;

      user-select: none;
      background: $color-navigation-bg;

      .el-menu-item {
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        width: 100%;
        height: 100%;
        transition: all 0.3s;

        .icon {
          width: 36px;
          height: 36px;
          opacity: 0.5;
        }
        .title {
          font-family: 'PingFang SC', DFPKingGothicGB-Medium, sans-serif;
          font-size: 16px;
          font-weight: 400;
          max-width: 70px;
          opacity: 0.6;
          color: #2f3035;
        }
      }

      // 选中的样式
      .el-menu-item.is-active {
        color: #000;
        .icon {
          opacity: 1;
        }
        span {
          opacity: 1;
          color: #000;
        }
      }
    }

    .aside-bottom {
      width: $sidebar-width;
      height: 540px;
      display: block;
      position: absolute;
      bottom: -400px;
      z-index: 1;
    }
  }
}

@media screen and (max-width: 1240px) {
  .aside-bar {
    .aside-container {
      width: $sidebar-width-min;

      .el-menu-container {
        .el-menu-item {
          display: flex;
          flex-direction: column;
          .title {
            font-size: 12px;
          }
        }
      }

      .aside-bottom {
        display: none;
      }
    }
  }
}
</style>
