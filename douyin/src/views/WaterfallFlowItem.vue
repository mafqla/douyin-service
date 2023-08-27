<template>
  <div class="WaterfallItem" :style="style" ref="frameDom">
    <div class="WaterfallItem__img">
      <img
        ref="imgDom"
        :style="{ visibility: isLoading ? 'visible' : 'hidden' }"
      />
    </div>
    <div class="WaterfallItem__name">
      {{ title }}
      <div v-if="!isLoading" class="WaterfallItem__name--placeholder"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'

const props = defineProps({
  showBorder: Number,
  src: String,
  title: String,
  style: Object,
  unitWidth: Number,
  index: Number,
  sizeChange: Function
})

const frameDom = ref(null)
const isLoading = ref(false)
const imgInfo = ref({ height: 1, width: 1 })
const imgDom = ref(null)

const top = computed(() => {
  let y = props.style.transform
    ? Number(
        props.style.transform?.substring(
          props.style.transform.indexOf(',', 0) + 1,
          props.style.transform.length - 3
        )
      )
    : undefined
  return y
})

const isImgShow = computed(() => {
  if (top.value === undefined) {
    return false
  }
  return top.value <= props.showBorder
})

const getData = async () => {
  if (imgDom.value === null || props.src === '' || !isImgShow.value) {
    return
  }
  let img = new Image()
  img.src = props.src
  img.onload = () => {
    imgInfo.value = {
      height: img.height,
      width: img.width
    }
    isLoading.value = true
  }
  imgDom.value.src = props.src
}

onMounted(() => {
  getData()
})

const onSizeChange = (height, index) => {
  if (waterfallFlowListInfo.value[index] === undefined) {
    waterfallFlowListInfo.value[index] = {}
  }
  waterfallFlowListInfo.value[index].height = height
  setStyleList(getStyleList())
}
</script>

<style scoped>
/* 样式可以保持不变 */
</style>
