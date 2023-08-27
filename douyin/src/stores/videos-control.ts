import { defineStore } from 'pinia'
import { ref } from 'vue'

export const videosCtrolStore = defineStore('control', () => {
  const isMuted = ref(true)
  const page = ref(1)
  const size = ref(10)
  const translateY = ref(0)
  const initTranslateY = ref(876)
  //记录当前活动的视频索引
  const activeVideoIndex = ref(0)
  //记录当前视频的播放状态
  const activeVideoPlayState = ref(true)
  const videosNum = ref(0)
  //是否停止滚动
  const stopScroll = ref(false)
  const handlePrev = () => {
    if (activeVideoIndex.value > 0) {
      translateY.value = translateY.value + initTranslateY.value
      //切换到上一个视频
      activeVideoIndex.value = activeVideoIndex.value - 1
    }
    stopScroll.value = false
  }
  const handleNext = () => {
    //暂停当前视频
    activeVideoPlayState.value = false
    translateY.value = translateY.value - initTranslateY.value
    //切换到下一个视频
    activeVideoIndex.value = activeVideoIndex.value + 1
    activeVideoPlayState.value = true

    //判断是否到达最后一个视频
    if (activeVideoIndex.value === videosNum.value - 1) {
      page.value = page.value + 1
    }
  }

  //重置状态
  const reset = () => {
    page.value = 1
    size.value = 10
    translateY.value = 0
    activeVideoIndex.value = 0
    activeVideoPlayState.value = true
    videosNum.value = 0
    stopScroll.value = false
  }

  return {
    isMuted,
    page,
    size,
    videosNum,
    translateY,
    initTranslateY,
    activeVideoIndex,
    activeVideoPlayState,
    stopScroll,
    handlePrev,
    handleNext,
    reset
  }
})
