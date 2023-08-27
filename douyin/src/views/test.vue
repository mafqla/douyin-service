<template>
  <div class="waterfallFlow" ref="scrollParent">
    <div class="waterfallFlow__title">响应式瀑布流</div>
    <section ref="waterfallFlowDom" class="waterfallFlow__content">
      <div v-for="(item, ind) in list" :key="ind">
        <WaterfallFlowItem
          :showBorder="scrollTop + scrollParent.clientHeight"
          :src="item.src"
          :title="item.title"
          :style="styleList[ind]"
          :unitWidth="unitWidth"
          :index="ind"
          @sizeChange="onSizeChange"
        />
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, computed } from 'vue'
import img1 from '@/assets/imgs/1.jpg'
import img2 from '@/assets/imgs/2.jpg'
import img12 from '@/assets/imgs/12.png'
import WaterfallFlowItem from './WaterfallFlowItem.vue'

const scrollParent = ref(null)
const scrollTop = ref(0)
const list = ref([])
const waterfallFlowDom = ref(null)
const styleList = ref([])
const isLoadingData = ref(false)
const heightList = [170, 230, 300]
const waterfallFlowListInfo = ref([])

const frameInfo = ref({ width: 0 })
const rowsNum = computed(() => {
  let width = frameInfo.value.width || 0
  if (width >= 1200) {
    return 6
  } else if (width >= 768 && width <= 1199) {
    return 4
  } else {
    return 2
  }
})
const unitWidth = computed(() => {
  return (frameInfo.value.width - (rowsNum.value - 1) * 10) / rowsNum.value
})

const createRandomNum = (min, max) => {
  return Math.floor(Math.random() * (max - min + 1)) + min
}

onMounted(() => {
  isLoadingData.value = true
  let data = []
  for (let i = 0; i < 50; i++) {
    let item = {
      src: '',
      title: ''
    }
    data.push(item)
  }
  list.value = data

  data = []

  for (let i = 0; i < 50; i++) {
    let item
    if (i % 3 == 0) {
      item = {
        src: img1,
        title: `第${i}个Item`
      }
    } else if (i % 3 == 1) {
      item = {
        src: img2,
        title: `第${i}个Item`
      }
    } else {
      item = {
        src: img12,
        title: `第${i}个Item`
      }
    }
    data.push(item)
  }

  setTimeout(() => {
    list.value = data
    isLoadingData.value = false
  }, 1200)
})

const onResize = () => {
  if (waterfallFlowDom.value === null) {
    return
  }
  frameInfo.value.width = waterfallFlowDom.value.getBoundingClientRect().width
}

onMounted(() => {
  onResize()
  window.addEventListener('resize', onResize)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', onResize)
})

const onScroll = () => {
  scrollTop.value = scrollParent.value.scrollTop

  let top = scrollParent.value.scrollTop
  let clientHeight = scrollParent.value.clientHeight
  let scrollHeight = scrollParent.value.scrollHeight

  if (
    scrollHeight - clientHeight / 3 <= top + clientHeight &&
    !isLoadingData.value
  ) {
    isLoadingData.value = true
    let data = []

    for (let i = 0; i < 50; i++) {
      let item
      if (i % 3 == 0) {
        item = {
          src: img1,
          title: `第${i}个Item`
        }
      } else if (i % 3 == 1) {
        item = {
          src: img2,
          title: `第${i}个Item`
        }
      } else {
        item = {
          src: img12,
          title: `第${i}个Item`
        }
      }
      data.push(item)
    }

    setTimeout(() => {
      isLoadingData.value = false
      list.value.push(...data)
    }, 1200)
  }
}

onMounted(() => {
  scrollParent.value.addEventListener('scroll', onScroll)
})

onBeforeUnmount(() => {
  scrollParent.value.removeEventListener('scroll', onScroll)
})
</script>

<style scoped>
/* 样式可以保持不变 */
</style>
