<script setup lang="ts">
import {onBeforeUnmount, onMounted, ref} from 'vue'
import Player from 'xgplayer'

const props = defineProps({
  url: {
    type: String,
    required: true,
    default: ''
  }
})

const player = ref<any>(null)
onMounted(() => {
  player.value = new Player({
    id: 'video-player',
    url: props.url,
    autoplay: true,
    loop: true,
    width: '100%',
    height: '100%',
    // autoplayMuted: true,
    // volume: 0,
    closeVideoClick: true,
    lang: 'zh-cn',
    // playsinline: true,
    errorTips: `不支持的音频/视频格式`,
    closeInactive: true,
    ignores: ['fullscreen', 'play']
  })
})

onBeforeUnmount(() => {
  player.value.destroy()
})
</script>

<template>
  <div class="video-play">
    <div id="video-player" class="video-player"></div>
  </div>
</template>

<style lang="scss" scoped>
.video-play {
  position: relative;
  width: 100%;
  height: 100%;
  // bottom: 48px;
  // bottom: 60px;

  // :deep(.xgplayer-skin-default video) {
  //   position: absolute;
  //   width: 100%;
  //   height: 100%;
  //   bottom: 48px;
  // }
}

:deep(.xgplayer-skin-default .xgplayer-progress) {
  left: 2px;
  right: 2px;
  top: 26px;
}
</style>
