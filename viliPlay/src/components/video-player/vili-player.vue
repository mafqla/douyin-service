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
  <div class="xgplayer" ref="player" :id="playerId"></div>
</template>

<style lang="scss" scoped>
.xgplayer {
  position: relative;
  background: transparent;
  z-index: 1;
}

@media screen and (min-width: 1440px) and (max-width: 2560px) {
  .xgplayer {
    width: 71.4285714286%;
  }
}

@media screen and (min-width: 2560px) {
  .xgplayer {
    width: calc(100% - 656px);
  }
}
</style>
