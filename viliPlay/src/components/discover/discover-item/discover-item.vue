<script setup lang="ts">
import {} from 'vue'
import TitleBox from './title-box.vue'
import DiscoverVideo from './discover-video.vue'

const props = defineProps({
  img: String,
  videoTime: String,
  like: Number,
  title: String,
  author: String,
  fellow: Boolean,
  comment: Number,
  collect: Number,
  time: String
})
</script>
<template>
  <div class="discover-item">
    <a href="#" class="item-link">
      <div class="item-content">
        <discover-video
          :img="props.img"
          :videoTime="props.videoTime"
          :like="props.like"
        />
        <title-box
          :author="props.author"
          :title="props.title"
          :time="props.time"
        />
      </div>
    </a>
  </div>
</template>

<style lang="scss" scoped>
.discover-item {
  flex-shrink: 0;
  width: calc(50% - 16px);
  margin: 0px 8px 16px;

  .item-link {
    position: relative;

    .item-content {
      box-shadow: 0 0 0.5px 0 rgba(242, 242, 243, 1);
      cursor: pointer;
      display: flex;
      flex-direction: column;
      transition-duration: 0.35s;
      transition-property: transform, shadow;
      border: 0.5px solid rgba(22, 24, 35, 0.06);
      border-radius: 6px;
      overflow: hidden;
    }

    .item-content:hover {
      box-shadow: 0 0 24px rgba(0, 0, 0, 0.1);
    }
  }
}
@media (min-width: 884px) and (max-width: 1139px) {
  .discover-item {
    width: calc(33.3% - 16px);
  }
}
@media (min-width: 1140px) and (max-width: 1359px) {
  .discover-item {
    width: calc(25% - 16px);
  }
}
@media (min-width: 1360px) and (max-width: 1871px) {
  .discover-item {
    width: calc(20% - 16px);
  }
}

@media (min-width: 1872px) and (max-width: 2231px) {
  .discover-item {
    width: calc(16.6% - 16px);
  }
}

@media (min-width: 2231px) {
  .discover-item {
    width: calc(14.2% - 16px);
  }
}
</style>
