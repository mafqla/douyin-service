<template>
  <ViliPlayer :options="playerOptions" />
</template>

<script setup lang="ts">
import ViliPlayer from './ViliPlayer.vue'
import ControlBar from './ControlBar.vue'
import { ref, watchEffect } from 'vue'

const props = defineProps({
  url: {
    type: String,
    required: true
  },
  poster: {
    type: String,
    required: true
  }
})

// const playerId = ref(`video-player-${props.index}`)
// console.log(playerId.value)
// watchEffect(() => {
//   console.log(playerId.value)
// })

// 给组件传递的参数playerOptions
const playerOptions = {
  url: props.url,
  width: '100%',
  height: '100%',
  playsinline: true,
  autoplay: true,
  controls: true,
  loop: true,
  lang: 'zh-cn',
  volume: 0,
  poster: props.poster,
  videoInit: true,

  plugins: [
    {
      name: 'danmu',
      options: {
        font_size: 24,
        duration: 10,
        danmu_list: []
      }
    },
    {
      component: ControlBar,
      options: {
        playBtn: '.xgplayer-control-play-btn', // 播放按钮
        progress: '.xgplayer-control-progress', // 进度条
        progressBar: '.xgplayer-control-progress-bar', // 进度条背景
        progressBarBuffer: '.xgplayer-control-progress-buffer', // 进度条缓冲
        progressBarThumb: '.xgplayer-control-progress-bar-thumb', // 进度条拖动按钮
        progressTime: '.xgplayer-control-progress-time', // 当前进度时间
        progressDuration: '.xgplayer-control-progress-duration', // 总进度时间
        fullscreenBtn: '.xgplayer-control-fullscreen-btn', // 全屏按钮
        danmuBtn: '.xgplayer-control-danmu-btn', // 弹幕按钮
        volume: '.xgplayer-control-volume', // 音量控制
        volumeIcon: '.xgplayer-control-volume-icon', // 音量图标
        volumeBar: '.xgplayer-control-volume-bar', // 音量条背景
        volumeBarWrap: '.xgplayer-control-volume-bar-wrap', // 音量条背景外框
        volumeBarThumb: '.xgplayer-control-volume-bar-thumb', // 音量拖动按钮
        timeCurrent: '.xgplayer-control-time-current', // 当前播放时间
        timeDuration: '.xgplayer-control-time-duration' // 视频总时长
      }
    }
  ]
} as any
</script>
<style lang="scss" scoped></style>
