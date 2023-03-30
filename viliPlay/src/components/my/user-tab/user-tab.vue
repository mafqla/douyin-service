<script setup lang="ts">
import { ref, type Ref } from 'vue'
import { UserCollect, UserHistory, UserLike, UserPost } from '.'
import { ElTabPane, ElTabs } from 'element-plus'

import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const activeName = ref(route.query.showTab || 'post') as Ref<'post' | 'comments' | 'history'>
const isLogin = ref(false)

const handleClick = (tab: any) => {
  console.log(tab)
  activeName.value = tab

  router.push({
    query: {
      ...route.query,
      showTab: tab
    }
  })
}
</script>

<template>
  <div class="user-tab">
    <el-tabs v-model="activeName" @tab-click="handleClick(activeName)">
      <el-tab-pane label="作品" name="post">
        <user-post />
      </el-tab-pane>

      <el-tab-pane label="喜欢" name="like">
        <user-like />
      </el-tab-pane>

      <el-tab-pane label="收藏" name="favorite_collection">
        <user-collect />
      </el-tab-pane>

      <el-tab-pane label="观看历史" name="record">
        <user-history />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<style lang="scss" scoped>
.user-tab {
  background-color: #fff;

  :deep(.el-tabs__header) {
    height: 64px;
    margin: 0 auto;
    width: 100%;
    border-bottom: 1px solid rgba(22, 24, 35, 0.06);
    display: flex;
    align-items: center;
    background-color: #fff;
  }

  //下拉样式
  .header-scroll {
    left: 0;
    min-width: 650px;
    position: fixed;
    right: 0;
    top: 60px;
    transform: translateZ(0);
    z-index: 1;
  }

  // @media (max-width: 1475px) {
  //   :deep(.el-tabs__header) {
  //     padding: 0px 26px 0px 230px;
  //   }
  // }
  // @media (max-width: 1328px) {
  //   :deep(.el-tabs__header) {
  //     padding: 0px 26px 0px 230px;
  //   }
  // }

  // @media (min-width: 1920px) {
  //   :deep(.el-tabs__header) {
  //     padding: 0px 26px 0px 230px;
  //   }
  // }

  // @media (max-width: 840px) {
  //   :deep(.el-tabs__header) {
  //     padding: 0px 26px 0px 94px;
  //   }
  // }
  :deep(.el-tabs__item.is-active) {
    color: #000;
  }
  :deep(.el-tabs__item:hover) {
    color: none;
  }
  :deep(.el-tabs__nav-wrap::after) {
    height: 0;
  }

  :deep(.el-tabs__header) {
    margin: 0;
  }
  :deep(.el-tabs__item) {
    border-bottom: none;
    height: 100%;
    color: rgba(22, 24, 35, 0.34);
    font-size: 18px;
    line-height: 26px;
    font-weight: normal;
  }
  :deep(.el-tabs__active-bar) {
    height: 0;
  }

  :deep(.el-tabs__content) {
    flex: none;
    width: 100%;
    height: 100%;
  }
}
</style>
