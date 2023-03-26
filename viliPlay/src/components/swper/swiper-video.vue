<script setup lang="ts">
import { ref, watchEffect } from 'vue'
import VideoPlayer from '@/components/video-player/index.vue'
import type { IVideoList, IVideoListResult } from '@/service/videos/videosType'

import { videoStore } from '@/stores/videos'

import {
  VideoInfo,
  VideoAction,
  VideoSideBarBtn,
  VideoComments
} from '@/components/video-components'

const page = ref(1)
const pageSize = ref(10)
const status = ref(0)

let video: IVideoList = {
  page: page.value,
  size: pageSize.value,
  status: status.value
}
const videoData = videoStore().getVideos(video)

const videosList = ref<IVideoListResult[]>([])
watchEffect(async () => {
  //@ts-ignore
  videosList.value = (await videoData).list
})

const translateY = ref(0)
const timer: any = ref(null)
</script>
<template>
  <div class="carousel">
    <div
      class="carousel-inner"
      :style="{ transform: 'translate3d(0px,' + translateY + 'px, 0px)' }"
    >
      <div
        class="carousel-item"
        v-for="(item, index) in videosList"
        :key="item.id"
      >
        <video-player
          :url="item.videosAddress"
          :poster="item.videosCover"
          :isPlay="true"
          :key="item.id"
        />

        <video-info
          :username="item.username"
          :uploadTime="item.uploadTime"
          :description="item.description"
        />

        <video-action>
          <slot name="slide" />
        </video-action>

        <video-side-bar-btn />

        <video-comments />

        <div class="video-blur">
          <img :src="item.videosCover" alt="" />
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.carousel {
  display: flex;
  position: relative;
  left: 0px;
  top: calc(0% + 12px);
  width: 100%;
  height: calc(100% - 24px);
  overflow: visible;
  padding-left: 30px;
  padding-right: 60px;
  // left: 0px;
  // top: 0px;
  // width: 100%;
  // height: 100%;
  // overflow: visible;
  // padding-left: 0px;
  // padding-right: 0px;
  .carousel-inner {
    width: 100%;
    height: 100%;
    .carousel-item {
      position: relative;
      min-height: 461px;
      flex-grow: 1;
      height: 100%;
      opacity: 1;
      width: 100%;
      height: 881px;
      margin-bottom: 12px;
      border-radius: 4px;
      overflow: hidden;
      transition: all 0.15s linear 0s;
      .video-blur {
        bottom: 0px;
        left: 0px;
        position: absolute;
        right: 0px;
        top: 0px;
        z-index: 0;
        overflow: hidden;

        img {
          width: 100%;
          height: 100%;
          filter: blur(60px);
          opacity: 0.8;
        }
      }
    }
    @media screen and (max-width: 1240px) {
      .carousel-item {
        min-width: 440px;
      }
    }
  }
}
</style>
