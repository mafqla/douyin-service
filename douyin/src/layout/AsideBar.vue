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
  2: '/',
  3: '/follow',
  4: '/user'
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
  // console.log(path.split('/')[1])
  const index = Object.values(routerIndex).findIndex(
      (item) => item === `/${path.split('/')[1]}`
  )
  // console.log(index)
  return index + 1 + ''
})
console.log(activeMenu.value)
</script>
<template>
  <div class="aside">
    <div class="aside-bar">
      <div class="aside-top">
        <div class="aside-logo"></div>
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
  </div>
</template>

<style lang="scss" scoped>
.aside {
  width: $sidebar-width;
  position: fixed;
  .aside-bar {
    width: 100%;
    height: 100vh;
    background: $color-navigation-bg;
    position: relative;

    .aside-top {
      height: 60px;
      width: 100%;
      position: relative;
      .aside-logo {
        align-items: center;
        background: linear-gradient(
          180deg,
          #eff0f3 80%,
          rgba(241, 242, 245, 0)
        );
        background-image: url(@/assets/icons/douyin.svg);
        background-repeat: no-repeat;
        display: flex;
        flex-basis: 72px;
        justify-content: center;
        left: 50%;
        position: absolute;
        top: 50%;
        transform: translate(-24%, -28%);
        width: 100%;
        height: 60px;
        z-index: 2;

        .icon {
          height: 34px;
          opacity: 1;
          width: 91px;
        }
      }
    }

    .aside-container {
      background-position: 0 100%;
      background-size: cover;
      bottom: 0;
      height: calc(100vh - 60px);
      outline: none;
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

          &:hover {
            background: transparent;
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
}
@media screen and (max-width: 1240px) {
  .aside {
    width: $sidebar-width-min;

    .aside-bar {
      .aside-top {
        .aside-logo {
          background-image: url(@/assets/icons/smalldouyin.svg);
          background-repeat: no-repeat;
        }
      }
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
}
</style>
