<script setup lang="ts">
import { videosCtrolStore } from '@/stores/videos-control'
</script>

<template>
  <div class="carousel-controls">
    <div class="carousel-controls-switch">
      <div
        class="carousel-controls-switch-up"
        :class="{ disabled: videosCtrolStore().activeVideoIndex === 0 }"
        @click="videosCtrolStore().handlePrev()"
      >
        <svg-icon class="icon" icon="video-switch-prev-arrow" />
      </div>
      <div
        class="carousel-controls-switch-down"
        :class="{
          disabled: videosCtrolStore().stopScroll
        }"
        @click="
          videosCtrolStore().stopScroll === true
            ? null
            : videosCtrolStore().handleNext()
        "
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
