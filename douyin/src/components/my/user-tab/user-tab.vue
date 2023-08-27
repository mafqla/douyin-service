<script setup lang="ts">
import { ref, watchEffect, type Ref, reactive, onMounted } from 'vue'
import { UserCollect, UserHistory, UserLike, UserPost } from '.'
import { ElTabPane, ElTabs, type TabsPaneContext } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { userStore } from '@/stores/user'
import { videoStore } from '@/stores/videos'
import { useScroll } from '@vueuse/core'
const route = useRoute()
const router = useRouter()

const activeName = ref(router.currentRoute.value.query.showTab) as Ref<
  'post' | 'comments' | 'history' | string | undefined | number
>

// console.log(activeName.value, 'activeName.value')
const handleClick = (tab: TabsPaneContext) => {
  // console.log(tab.paneName)
  activeName.value = tab.paneName

  router.push({
    query: {
      ...route.query,
      showTab: tab.paneName
    }
  })
}

// 滚动监听
window.addEventListener('scroll', function () {
  if (window.scrollY > 160) {
    document.querySelector('.el-tabs__header')?.classList.add('header-scroll')
  } else {
    document
      .querySelector('.el-tabs__header')
      ?.classList.remove('header-scroll')
  }
})

const tabData = reactive({
  isHide: true
})

const isLogin = ref(false)
const store = userStore()
const video = videoStore()

const postCount = ref(0)
const likeCount = ref(0)
const collectCount = ref(0)

watchEffect(() => {
  isLogin.value = store.isLogin()
  postCount.value = video.postCount
  likeCount.value = video.likeCount
  collectCount.value = video.collectCount

  activeName.value = router.currentRoute.value.query.showTab as any
})
</script>

<template>
  <div class="user-tab">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="作品" name="post" :lazy="true">
        <template #label v-if="isLogin">
          <span class="tab-title">作品</span>
          <span class="tab-video-num" v-if="isLogin || tabData.isHide">
            {{ postCount }}
          </span>
          <!-- <svg-icon icon="lock" class="icon" v-if="tabData.isHide" /> -->
        </template>

        <template v-if="isLogin">
          <Loading :show="videoStore().loading">
            <user-post />
          </Loading>
        </template>
      </el-tab-pane>

      <el-tab-pane label="喜欢" name="like" :lazy="true">
        <template #label v-if="isLogin">
          <span class="tab-title">喜欢</span>
          <span class="tab-video-num" v-if="tabData.isHide">
            {{ likeCount }}
          </span>
          <svg-icon icon="lock" class="icon" v-if="!tabData.isHide" />
        </template>

        <template v-if="isLogin">
          <Loading :show="videoStore().loading">
            <user-like />
          </Loading>
        </template>
      </el-tab-pane>

      <el-tab-pane label="收藏" name="favorite_collection" :lazy="true">
        <template #label v-if="isLogin">
          <span class="tab-title">收藏 </span>
          <span class="tab-video-num" v-if="tabData.isHide">
            {{ collectCount }}
          </span>
          <svg-icon icon="lock" class="icon" v-if="!tabData.isHide" />
        </template>
        <template v-if="isLogin">
          <Loading :show="videoStore().loading">
            <user-collect />
          </Loading>
        </template>
      </el-tab-pane>

      <el-tab-pane label="观看历史" name="record" :lazy="true">
        <template v-if="isLogin">
          <Loading :show="videoStore().loading">
            <user-history />
          </Loading>
        </template>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<style lang="scss" scoped>
.user-tab {
  background-color: #fff;

  .tab-title {
    margin-right: 6px;
  }

  .icon {
    width: 14px;
    height: 16px;
  }
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
  :deep(.el-tabs__header).header-scroll {
    left: 0;
    min-width: 650px;
    position: fixed;
    right: 0;
    top: 60px;
    transform: translateZ(0);
    z-index: 1;
  }

  @media (min-width: 1475px) {
    :deep(.el-tabs__header).header-scroll {
      padding: 0px 166.333px 0px 332.667px;
    }
  }

  @media (max-width: 1475px) {
    :deep(.el-tabs__header).header-scroll {
      padding: 0px 26px 0px 230px;
    }
  }
  @media (max-width: 1328px) {
    :deep(.el-tabs__header).header-scroll {
      padding: 0px 66px 0px 132px;
    }
  }

  @media (max-width: 840px) {
    :deep(.el-tabs__header).header-scroll {
      padding: 0px 26px 0px 94px;
    }
  }
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
