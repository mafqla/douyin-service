<script setup lang="ts">
import { ref } from 'vue'
import { UserHeader, LoginCode, UserTab, UserFooter } from '@/components/my'
import { useElScrollbarScroll } from '@/hooks'
import type { ElScrollbar } from 'element-plus'

const backgroundurlLightURL = '../assets/user-background-light.png'
const backgroundurlDarkURL = '../assets/user-background-dark.png'
const background = ref(backgroundurlLightURL)
const isLogin = ref(false)
const scrollbarRef = ref<InstanceType<typeof ElScrollbar> | null>(null)
const isDisplay = ref(false)
const tabDisplay = ref(false)

useElScrollbarScroll(
  scrollbarRef,
  (scrollTop) => {
    if (scrollTop > 20) {
      isDisplay.value = true
    } else {
      isDisplay.value = false
    }

    if (scrollTop > 200) {
      tabDisplay.value = true
    } else {
      tabDisplay.value = false
    }
  },
  10
)
</script>
<template>
  <div class="user-detail">
    <el-scrollbar
      height="904px"
      ref="scrollbarRef"
      :class="{ scrolled: isDisplay }"
    >
      <div class="user-detail-content">
        <user-header />
        <user-tab :isDisplay="tabDisplay" />

        <login-code v-if="isLogin" />
      </div>
      <user-footer />
    </el-scrollbar>
  </div>
</template>

<style lang="scss" scoped>
.el-scrollbar {
  position: static;
  &::before {
    background-image: url(../assets/user-background-light.png);
    background-position: 50%;
    background-repeat: no-repeat;
    background-size: 1920px 172px;
    content: '';
    height: 172px;
    left: 0;
    position: absolute;
    right: 0;
    top: 0;
    z-index: -1;
    transition: opacity 0.3s ease-in-out;
  }
}
.scrolled.el-scrollbar::before {
  opacity: 0; // 滚动后使背景图片消失
}
.user-detail {
  display: flex;
  width: 100%;
  flex-direction: column;
  user-select: none;

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
