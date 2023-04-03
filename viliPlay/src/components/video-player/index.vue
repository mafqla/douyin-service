<script setup lang="ts">
import ViliPlayer from './vili-player.vue'
// import ControlBar from './ControlBar.vue'
import { ref, watchEffect } from 'vue'

import {
  VideoInfo,
  VideoSideBarBtn,
  VideoSidebar
} from '@/components/video-components'

const props = defineProps({
  username: {
    type: String,
    required: true
  },
  uploadTime: {
    type: String,
    required: true
  },
  description: {
    type: String,
    required: true
  },
  url: {
    type: String,
    required: true
  },
  poster: {
    type: String,
    required: true
  },
  isPlay: {
    type: Boolean,
    required: true
  }
})

const globalVolume = ref(0)

// 给组件传递的参数playerOptions
const playerOptions = {
  url: props.url,
  width: '100%',
  height: '100%',
  playsinline: true,
  autoplay: props.isPlay,
  controls: true,
  loop: true,
  lang: 'zh-cn',
  volume: globalVolume.value,
  poster: props.poster,
  keyShortcut: 'on',
  cssFullscreen: true,
  playbackRate: [0.5, 1.0, 1.5, 1.75, 2],
  closeInactive: true,
  allowSeekPlayed: true,
  allowPlayAfterEnded: true,
  allowSeekAfterEnded: true
} as any

watchEffect(() => {
  playerOptions.volume = globalVolume.value
})

let isShow = ref(true)
let currentWidth = ref('100%')
//打开评论
const openComments = () => {
  //隐藏按钮
  isShow.value = false
  //设置宽度
  currentWidth.value = 'calc(100% - 336px)'

  //获取屏幕宽度
  const screenWidth = document.body.clientWidth
  // console.log(screenWidth)

  //如果屏幕宽度大于等于2560px，就设置宽度为100%-28.5714285714%
  if (screenWidth >= 1440) {
    currentWidth.value = 'calc(100% - 28.5714285714%)'
  }
}

//关闭评论
const closeComments = () => {
  //显示按钮
  isShow.value = true
  //设置宽度
  currentWidth.value = '100%'
}

//切换评论区的显示状态
const toggleComments = () => {
  isShow.value = !isShow.value
  if (!isShow.value) {
    //如果评论区关闭，就执行打开评论操作
    openComments()
  } else {
    //否则执行关闭评论操作
    closeComments()
  }
}
</script>

<template>
  <ViliPlayer
    :options="playerOptions"
    :style="{ width: currentWidth }"
    @toggleComments="toggleComments"
  />

  <video-info
    :username="props.username"
    :uploadTime="props.uploadTime"
    :description="props.description"
  />
  <video-side-bar-btn @click="openComments" v-show="isShow" />

  <video-sidebar @closeComments="closeComments" v-show="!isShow" />
</template>

<style lang="scss" scoped></style>
