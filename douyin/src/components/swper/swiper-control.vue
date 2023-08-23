<script setup lang="ts">
import { ref, watchEffect } from 'vue'
import { ElMessage } from 'element-plus'
import { videoStore } from '@/stores/videos'
import { videosCtrolStore } from '@/stores/videos-control'

const currentIndex = ref(0)
const prev = ref(false)
const next = ref(false)
const isPlay = ref(false)

const page = ref(1)
const pageSize = ref(5)

console.log(currentIndex.value)

// const videoData = await videoStore().getVideos(page.value, pageSize.value)
// console.log(videoData)

//点击上一张 ，当前索引为0时，不能再点击
const handlePrev = () => {
  if (currentIndex.value === 0) {
    prev.value = true
    ElMessage({
      message: '🤣🤣🤣，这是第一个视频！',
      type: 'warning'
    })
    return
  }
  // 暂停上一个视频
  isPlay.value = false
  currentIndex.value--
  videoStore().translateY += 876
  prev.value = false
  next.value = false
}

const handleNext = async () => {
  if (currentIndex.value === -1) {
    page.value++

    if (!next.value) {
      const videoData = await videoStore().getVideos(page.value, pageSize.value)
      console.log(videoData)

      // 如果不是true，则将data.list的值赋值给videosList.value
      if (videoData.code === 200) {
        //@ts-ignore
        videosList.value.push(...videoData.list)
      }
      //检查data的值是否为null，如果是，则调用ElMessage()函数，以弹出提示信息。
      if (videoData.code === 204) {
        ElMessage({
          message: `${videoData.msg}🤣🤣🤣，没有更多视频了！`,
          type: 'warning'
        })
        next.value = true
        //停止执行
        return
      }
    }
  }

  // console.log(videosList.value)

  // 暂停上一个视频
  isPlay.value = false
  currentIndex.value++
  videoStore().translateY -= 876
}
</script>

<template>
  <div class="carousel-controls">
    <div class="carousel-controls-switch">
      <div
        class="carousel-controls-switch-up"
        :class="{ disabled: videosCtrolStore().activeVideoIndex === 0 || prev }"
        @click="videosCtrolStore().handlePrev()"
      >
        <svg-icon class="icon" icon="video-switch-prev-arrow" />
      </div>
      <div
        class="carousel-controls-switch-down"
        :class="{
          disabled: next
        }"
        @click="next === true ? null : videosCtrolStore().handleNext()"
      >
        <svg-icon class="icon" icon="video-switch-next-arrow" />
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.carousel-controls {
  width: 100%;
  height: 100%;

  .carousel-controls-switch {
    background-color: rgb(50, 52, 66);
    height: 80px;
    opacity: 0.7;
    position: relative;
    width: 36px;
    border-radius: 18px;
    &:hover {
      opacity: 0.9;
    }
    .carousel-controls-switch-up {
      height: 40px;
      position: absolute;
      top: 0px;
      width: 36px;
      cursor: pointer;
      opacity: 0.7;

      .icon {
        left: 5px;
        position: relative;
        top: 7px;
        height: 26px !important;
        width: 26px !important;
        color: #fff;
      }
    }
    .carousel-controls-switch-up.disabled {
      cursor: not-allowed;
      opacity: 0.3;
    }
    .carousel-controls-switch-down.disabled {
      cursor: not-allowed;
      opacity: 0.3;
    }

    .carousel-controls-switch-down {
      bottom: 0px;
      height: 40px;
      opacity: 0.7;
      position: absolute;
      width: 36px;
      cursor: pointer;

      .icon {
        left: 5px;
        position: relative;
        top: 7px;
        height: 26px !important;
        width: 26px !important;
        color: #fff;
      }
    }
  }
}
</style>
