import { defineStore } from 'pinia'
import { ref } from 'vue'

export const videosCtrolStore = defineStore('control', () => {
  const translateY = ref(0)
  const initTranslateY = ref(876)
  //记录当前活动的视频索引
  const activeVideoIndex = ref(0)
  //记录当前视频的播放状态
  const activeVideoPlayState = ref(true)
  const handlePrev = () => {
    if (activeVideoIndex.value > 0) {
      translateY.value = translateY.value + initTranslateY.value
      //切换到上一个视频
      activeVideoIndex.value = activeVideoIndex.value - 1
    }
  }
  const handleNext = () => {
    //暂停当前视频
    activeVideoPlayState.value = false
    translateY.value = translateY.value - initTranslateY.value
    //切换到下一个视频
    activeVideoIndex.value = activeVideoIndex.value + 1
    activeVideoPlayState.value = true
  }

  return {
    translateY,
    activeVideoIndex,
    activeVideoPlayState,
    handlePrev,
    handleNext
  }
})
