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
</script>
<style lang="scss" scoped></style>
