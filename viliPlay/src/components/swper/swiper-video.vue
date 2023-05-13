<script setup lang="ts">
import { ref, watchEffect } from 'vue'
import VideoPlayer from '@/components/video-player/index.vue'
import { videoStore } from '@/stores/videos'
</script>
<template>
  <div class="carousel">
    <div
      class="carousel-inner"
      :style="{
        transform: 'translate3d(0px,' + videoStore().translateY + 'px, 0px)'
      }"
    >
      <div
        class="carousel-item"
        v-for="(item, index) in videoStore().videos as any "
        :key="item.id"
      >
        <video-player
          :id="item.id"
          :username="item.authorName"
          :description="item.description"
          :uploadTime="item.uploadTime"
          :url="item.videosAddress"
          :poster="item.videosCover"
          :isPlay="true"
          :key="item.id"
          :img="item.authorAvatar"
          :dianzan="item.likeCount"
          :comment="item.commentCount"
          :shoucang="item.collectionCount"
        />

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
