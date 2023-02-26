<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref } from 'vue'
import xgplayer from 'xgplayer'

import { v4 as uuidv4 } from 'uuid'
const props = defineProps({
  options: {
    type: Object,
    required: true
  }
})

const player = ref<any>(null)
const uniqueId = uuidv4()
onMounted(() => {
  //@ts-ignore
  player.value = new xgplayer({
    ...props.options,
    id: `xgplayer-${uniqueId}`
  })
  const playerRef = ref<HTMLDivElement | null>(null)
  playerRef.value?.appendChild(player.value.root)
})

onBeforeUnmount(() => {
  player.value.destroy()
})

const playerId = ref(`xgplayer-${uniqueId}`)
const poster = ref(props.options.poster || '')
</script>
<template>
  <div class="xgplayer" ref="player" :id="playerId">
    <div
      class="xgplayer-blur"
      :style="{ backgroundImage: 'url(' + poster + ')' }"
    ></div>
  </div>
</template>

<style lang="scss" scoped>
.xgplayer {
  position: relative;
  width: 100%;
  height: 100%;
  z-index: 1;

  .xgplayer-blur {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    filter: blur(50px);
    z-index: -1;
  }
}
</style>
