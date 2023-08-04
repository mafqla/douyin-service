<script setup lang="ts">
import { ref } from 'vue'
import TitleBox from './title-box.vue'
import DiscoverVideo from './discover-video.vue'
import { useElementSize } from '@vueuse/core'

const props = defineProps({
  img: String,
  videoTime: String,
  like: Number,
  title: String,
  author: String,
  fellow: Boolean,
  comment: Number,
  collect: Number,
  postTime: String,
  videoUrl: String,
  itemWidth: Number
})

// console.log(props)

const renderedImg = ref()
const { height } = useElementSize(renderedImg)
</script>
<template>
  <div
    class="discover-item"
    :style="{
      height: `${height + 77}px`
    }"
  >
    <a href="#" class="item-link rlkYqFL_ waterfall-videoCardContainer">
      <div class="item-content">
        <div ref="renderedImg">
          <discover-video
            :img="props.img"
            :videoTime="props.videoTime"
            :like="props.like"
            :videoUrl="props.videoUrl"
            :itemWidth="props.itemWidth"
          />
        </div>

        <title-box
          :author="props.author"
          :title="props.title"
          :time="props.postTime"
        />
      </div>
    </a>
  </div>
</template>

<style lang="scss" scoped>
.discover-item {
  position: absolute;
  will-change: transform;

  .item-link {
    position: relative;

    .item-content {
      display: flex;
      flex-direction: column;
    }

    .item-content:hover {
      box-shadow: 0 0 24px rgba(0, 0, 0, 0.1);
    }
  }

  .rlkYqFL_ {
    box-shadow: 0 0 0.5px 0 rgba(242, 242, 243, 0.08);
    display: flex;
    flex-direction: column;
    position: relative;
    transition-duration: 0.35s;
    transition-property: transform, box-shadow;
    border: 0.5px solid rgba(255, 255, 255, 0.04);
    border-radius: 6px;
    overflow: hidden;
  }

  // html[dark] .waterfall-videoCardContainer.rlkYqFL_ {
  //   border-width: 0.5px;
  //   border-style: solid;
  //   border-color: rgba(255, 255, 255, 0.1);
  //   border-image: initial;
  // }

  html .waterfall-videoCardContainer.rlkYqFL_ {
    border-width: 0.5px;
    border-style: solid;
    border-color: rgba(22, 24, 35, 0.1);
    border-image: initial;
  }
  .waterfall-videoCardContainer.rlkYqFL_ {
    border-radius: 12px;
  }
}
// @media (min-width: 884px) and (max-width: 1139px) {
//   .discover-item {
//     width: calc(33.3% - 16px);
//   }
// }
// @media (min-width: 1140px) and (max-width: 1359px) {
//   .discover-item {
//     width: calc(25% - 16px);
//   }
// }
// @media (min-width: 1360px) and (max-width: 1871px) {
//   .discover-item {
//     width: calc(20% - 16px);
//   }
// }

// @media (min-width: 1872px) and (max-width: 2231px) {
//   .discover-item {
//     width: calc(16.6% - 16px);
//   }
// }

// @media (min-width: 2231px) {
//   .discover-item {
//     width: calc(14.2% - 16px);
//   }
// }
</style>
