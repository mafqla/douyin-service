<script setup lang="ts">
import { videosCtrolStore } from '@/stores/videos-control'
import type { IVideoList } from '@/service/videos/videosType'
import { watchEffect, type PropType } from 'vue'

defineProps({
  videoList: {
    type: Array as PropType<IVideoList[]>,
    default: []
  }
})

watchEffect(() => {
  console.log('videoList', videosCtrolStore().activeVideoIndex)
  //更新视频状态
})
const isActiveIndex = (index: number) =>
  index === videosCtrolStore().activeVideoIndex
</script>
<template>
  <div class="carousel">
    <div
      class="carousel-inner"
      :style="{
        transform:
          'translate3d(0px,' + videosCtrolStore().translateY + 'px, 0px)',
        'transition-duration': '0ms'
      }"
    >
      <div
        class="carousel-item"
        v-for="(item, index) in videoList"
        :key="item.id"
        :style="{
          height: '864px',
          'margin-bottom': '12px'
        }"
      >
        <swiper-player
          :id="item.id"
          :username="item.userName"
          :uploadTime="item.uploadTime"
          :description="item.description"
          :url="item.videosAddress"
          :poster="item.videosCover"
          :img="item.userAvatar"
          :dianzan="item.likeCount"
          :comment="item.commentCount"
          :isLike="item.isLike"
          :isCollect="item.isCollect"
          :isAttention="item.isAttention"
          :isPlay="isActiveIndex(index)"
        />
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.carousel {
  position: absolute;
  left: 0;
  top: calc(0% + 12px);
  width: 100%;
  height: calc(100% - 24px);

  overflow: visible;
  padding-left: 30px;
  padding-right: 60px;
  .carousel-inner {
    width: 100%;
    height: 100%;
    .carousel-item {
      display: flex;
      flex-direction: column;
      width: 100%;
      height: 100%;
      position: relative;
      flex-shrink: 0;
    }
    @media screen and (max-width: 1240px) {
      .carousel-item {
        min-width: 440px;
      }
    }
  }
}
</style>
