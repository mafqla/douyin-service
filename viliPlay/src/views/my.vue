<script setup lang="ts">
import { onMounted, ref, watch, watchEffect } from 'vue'
import { UserHeader, LoginCode, UserTab, UserFooter } from '@/components/my'
import { userStore } from '@/stores/user'
import { useRouter } from 'vue-router'
import { videoStore } from '@/stores/videos'
import backgroundurlLightURL from '@/assets/user-background-light.png'
import backgroundurlDarkURL from '@/assets/user-background-dark.png'
import { useInfiniteScroll } from '@vueuse/core'

const background = ref(`url('${backgroundurlLightURL}')`)

const isDisplay = ref(false)
const router = useRouter()

// 滚动监听
window.addEventListener('scroll', function () {
  // console.log('window.scrollY', window.scrollY)
  if (window.scrollY > 60) {
    isDisplay.value = true
  } else {
    isDisplay.value = false
  }
})
const video = videoStore()

const store = userStore()
const isLogin = ref(false)
const query = ref(router.currentRoute.value.query.showTab) as any
const page = ref(1)
const size = ref(10)
const allowScroll = ref(true)

const fetchVideoData = async () => {
  if (query.value === null) {
    return
  }
  const data = await video.getVideoDataByParams({
    showTab: query.value,
    page: page.value,
    size: size.value
  })
  if (data) {
    if (query.value === 'post') {
      video.userPostVideos = []
      video.userPostVideos.push(...data)
    }
    if (query.value === 'like') {
      video.userLikeVideos = []
      video.userLikeVideos.push(...data)
    }
    if (query.value === 'favorite_collection') {
      video.userCollectVideos = []
      video.userCollectVideos.push(...data)
    }
    if (query.value === 'record') {
      video.userRecordVideos = []
      video.userRecordVideos.push(...data)
    }
  }
  allowScroll.value = data && ((data.length > 0) as any)
}

onMounted(() => {
  if (localStorage.getItem('theme') === 'dark') {
    background.value = `url('${backgroundurlDarkURL}')`
  } else {
    background.value = `url('${backgroundurlLightURL}')`
  }

  fetchVideoData()
  isLogin.value = store.isLogin()
})

watch(query, () => {
  page.value = 1

  // fetchVideoData()
})
watchEffect(() => {
  isLogin.value = store.isLogin()
  query.value = router.currentRoute.value.query.showTab

  fetchVideoData()
})

const load = () => {
  if (!allowScroll.value) {
    return
  }
  video.bottomLoading = true
  page.value += 1
  video.bottomLoading = false
}

console.log(page.value)

useInfiniteScroll(window, load, {
  distance: 500
})
</script>
<template>
  <div class="user-detail" :class="{ scrolled: isDisplay }">
    <div class="user-detail-content">
      <user-header />
      <user-tab />
      <login-code v-if="!isLogin" />
    </div>
    <user-footer />
  </div>
</template>

<style lang="scss" scoped>
.scrolled::before {
  opacity: 0; // 滚动后使背景图片消失
}
.user-detail {
  display: flex;
  width: 100%;
  flex-direction: column;
  user-select: none;

  &::before {
    background-image: v-bind(background);
    background-position: 50%;
    background-repeat: no-repeat;
    background-size: 1920px 172px;
    content: '';
    height: 172px;
    left: 0;
    position: absolute;
    right: 0;
    top: 0;
    // z-index: -1;
    transition: opacity 0.3s ease-in-out;
  }
  .user-detail-content {
    margin: 0 auto;
    max-width: 1208px;
    min-height: calc(100vh - 60px);
    min-width: 650px;
    width: 100%;
  }
}

@media (max-width: 1475px) {
  .user-detail {
    .user-detail-content {
      max-width: none;
      width: calc(100% - 120px);
    }
  }
}
@media (max-width: 1328px) {
  .user-detail {
    .user-detail-content {
      max-width: none;
      width: calc(100% - 120px);
    }
  }
}

@media (min-width: 1920px) {
  .user-detail {
    .user-detail-content {
      &::before {
        background-size: 100% 100%;
      }
    }
  }
}

@media (max-width: 840px) {
  .user-detail {
    .user-detail-content {
      margin: 0 20px;
    }
  }
}

// :deep(.header) {
//   border-bottom: 1px solid hsla(0, 0%, 100%, 0.1) !important;
// }

// :global(.header-search-icon) {
//   :global(button) {
//     background-color: transparent !important;

//     :global(.icon-search) {
//       color: #fff;
//     }
//     :global(.btn-title) {
//       color: #fff !important;
//     }

//     :global(&:hover) {
//       background-color: hsla(0, 0%, 100%, 0.3) !important;
//       :global(.icon-search) {
//         color: #fff;
//       }
//       :global(.btn-title) {
//         color: #fff;
//       }
//     }
//   }
// }

// :global(.header-search-input) {
//   background-color: transparent !important;
//   border: 2px solid hsla(0, 0%, 100%, 0.3) !important;
//   caret-color: #fe2c55;

//   :global(&::placeholder) {
//     color: rgba(255, 255, 255, 1);
//   }

//   :global(&:active) {
//     border: 2px solid hsla(0, 0%, 100%, 0.3) !important;
//   }
// }

// :global(.header-right-item-overplay) {
//   background-color: hsla(0, 0%, 100%, 0.17) !important;
// }

// :global(.header-right-item:hover) {
//   p {
//     color: rgba(255, 255, 255, 1) !important;
//   }
//   .icon {
//     opacity: 1;
//   }
// }
// :global(.header-right) {
//   :global(.header-right-item) {
//     p {
//       color: rgba(255, 255, 255, 0.75) !important;
//     }
//     :global(.icon) {
//       opacity: 0.8;
//       color: #fff;

//       path {
//         fill: rgba(255, 255, 255, 1) !important;
//       }
//     }
//   }
// }
</style>
