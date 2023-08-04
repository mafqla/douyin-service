<script setup lang="ts">
import { onMounted, ref, watchEffect } from 'vue'
import HotItem from '@/components/discover/hot-item/index.vue'
import DiscoverItem from '@/components/discover/discover-item/discover-item.vue'
import { getVideoList } from '@/service/videos/videos'
import type { IFeedParams, IVideoList } from '@/service/videos/videosType'
import { useElementSize } from '@vueuse/core'

const loading = ref(true)
const page = ref(1)
const size = ref(10)
const list = ref<IVideoList[]>([])
const getData = async (params: IFeedParams) => {
  try {
    const res = await getVideoList(params)
    // console.log(res)

    const { data } = res
    list.value.push(...data)
  } catch (err) {
    console.log(err)
  }
}
const currentHeight = ref(0)
const currentWidth = ref(0)

//侧边栏宽度
const sidebarWidth = ref(172)
//每个子项的宽高
const itemWidth = ref(0)
const itemHeight = ref(0)
//子项个数
const numItems = ref(5)

// margin 宽度
const marginWidth = ref(44)
// 两个子项之间的间隔
const widthGap = ref(36)
//生成每个子项的translateX值
const translateXValues = ref<number[]>([])

/**
 *
 * @param itemWidth  每个子项的宽度
 * @param gap  两个子项之间的间隔
 * @param numItems  子项的个数
 */
function generateTranslateXValues(
  itemWidth: number,
  gap: number,
  numItems: number
) {
  const translateXValues = []

  for (let i = 0; i < numItems; i++) {
    const translateX = 8 + (itemWidth + gap) * i
    translateXValues.push(translateX)
  }

  return translateXValues
}

//组件挂载前获取窗口大小
onMounted(() => {
  currentHeight.value = window.innerHeight
  currentWidth.value = window.innerWidth

  getData({
    page: page.value,
    size: size.value
  })
})
// console.log(list.value)
// 监听窗口大小变化
window.addEventListener('resize', () => {
  currentHeight.value = window.innerHeight
  currentWidth.value = window.innerWidth
})

//监听窗口大小变化
watchEffect(() => {
  // console.log(currentHeight.value, currentWidth.value)
  //如果窗口宽度小于等于1240，
  if (currentWidth.value <= 1240) {
    sidebarWidth.value = 72
  } else {
    sidebarWidth.value = 172
  }
  // console.log(sidebarWidth.value)

  currentHeight.value = window.innerHeight
  const minWidth = 512
  currentWidth.value = window.innerWidth - sidebarWidth.value - 44
  currentWidth.value = Math.max(currentWidth.value, minWidth)
  // console.log(currentHeight.value, currentWidth.value)
  //如果currentWeight小于等于1491设置项的个数为4，小于等于1020设置项的个数为3，小于等于768设置项的个数为2
  if (currentWidth.value >= 1020 && currentWidth.value < 1491) {
    numItems.value = 4
    widthGap.value = 20
  } else if (currentWidth.value >= 768 && currentWidth.value < 1020) {
    numItems.value = 3
    widthGap.value = 4
  } else if (currentWidth.value >= 0 && currentWidth.value < 768) {
    numItems.value = 2
    widthGap.value = 32
    marginWidth.value = 0
  } else {
    numItems.value = 5
    widthGap.value = 36
  }

  // console.log(numItems.value)

  // 36 20 8  32
  itemWidth.value =
    (currentWidth.value - marginWidth.value - widthGap.value) / numItems.value
  // console.log('itemWidth', itemWidth.value)
  translateXValues.value = generateTranslateXValues(
    itemWidth.value,
    16,
    numItems.value
  )

  // console.log(translateXValues.value)
})

// const renderedHeight = ref()
// const { height } = useElementSize(renderedHeight)
// console.log(height)
//置顶数据
const topData = {
  id: 1,
  title: '本轮强降雨接下来会影响哪'
}
//列表数据
const listData = [
  {
    id: 1,
    title: '本轮强降雨接下来会影响哪些地区？',
    hotNum: 100,
    imgType: 'hot_boom'
  },
  {
    id: 2,
    title: '本轮强降雨接下来会影响哪些地区？',
    hotNum: 100,
    imgType: 'hot_hot'
  },
  {
    id: 3,
    title: '本轮强降雨接下来会影响哪些地区？',
    hotNum: 100,
    imgType: 'hot_exclusive'
  },
  {
    id: 4,
    title: '本轮强降雨接下来会影响哪些地区？',
    hotNum: 100,
    imgType: 'hot_first'
  },

  {
    id: 5,
    title: '本轮强降雨接下来会影响哪些地区？',
    hotNum: 100,
    imgType: 'hot_first'
  },
  {
    id: 6,
    title: '本轮强降雨接下来会影响哪些地区？',
    hotNum: 100,
    imgType: 'hot_first'
  },
  {
    id: 7,
    title: '本轮强降雨接下来会影响哪些地区？',
    hotNum: 100,
    imgType: 'hot_first'
  },
  {
    id: 8,
    title: '本轮强降雨接下来会影响哪些地区？',
    hotNum: 100,
    imgType: 'hot_first'
  },
  {
    id: 9,
    title: '本轮强降雨接下来会影响哪些地区？',
    hotNum: 100,
    imgType: 'hot_first'
  },
  {
    id: 10,
    title: '本轮强降雨接下来会影响哪些地区？',
    hotNum: 100,
    imgType: 'hot_first'
  },
  {
    id: 11,
    title: '本轮强降雨接下来会影响哪些地区？',
    hotNum: 100,
    imgType: 'hot_first'
  },
  {
    id: 12,
    title: '本轮强降雨接下来会影响哪些地区？',
    hotNum: 100,
    imgType: 'hot_first'
  },
  {
    id: 13,
    title: '本轮强降雨接下来会影响哪些地区？',
    hotNum: 100,
    imgType: 'hot_first'
  },
  {
    id: 14,
    title: '本轮强降雨接下来会影响哪些地区？',
    hotNum: 100,
    imgType: 'hot_first'
  }
]
</script>
<template>
  <div class="discover">
    <div class="discover-main">
      <div
        class="discover-container"
        :style="{
          height: `${currentHeight}px`,
          width: `${currentWidth}px`,
          margin: '0 22px'
        }"
      >
        <template v-for="(item, index) in list" :key="item.id">
          <discover-item
            :id="item.id"
            :index="index"
            :img="item.videosCover"
            :postTime="item.uploadTime"
            :videoTime="item.videosTime"
            :like="item.likeCount"
            :author="item.userName"
            :title="item.title"
            :fellow="item.isAttention"
            :videoUrl="item.videosAddress"
            :itemWidth="itemWidth"
            :style="{
              width: `${itemWidth}px`,
              transform: `translate(${translateXValues[index]}px, 16px)`
            }"
          />
        </template>

        <hot-item
          :topData="topData"
          :listData="listData"
          :style="{
            height: '698.062px',
            width: `${itemWidth}px`,
            transform: `translate(${translateXValues[numItems - 1]}px, 16px)`
          }"
        />
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.discover {
  display: flex;
  flex-direction: column;
  height: 100%;
  overflow-y: scroll;
  position: relative;

  .discover-main {
    flex-grow: 1;
    position: relative;
    width: 100%;

    .discover-container {
      position: relative;
    }
  }
}
</style>
