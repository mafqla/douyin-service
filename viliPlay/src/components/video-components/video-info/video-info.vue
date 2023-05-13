<script setup lang="ts">
import {} from 'vue'

const props = defineProps<{
  username: string
  uploadTime: string
  description: string
}>()
</script>
<template>
  <div class="video-info">
    <div class="video-info-top">
      <div class="video-info-author">
        <span>@{{ props.username }}</span>
      </div>
      <div class="video-info-time">
        <span>{{ props.uploadTime }}</span>
      </div>
    </div>
    <div class="video-info-desc">
      <span>{{ props.description }}</span>
      <div class="video-info-tag">
        <!-- <a href="#">
          <span>#愿所有的美好和期待2023都能如约而至</span>
        </a> -->
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.video-info {
  position: absolute;
  bottom: 40px;
  left: 0;
  width: 100%;
  height: 100px;
  padding: 16px 95px 16px 16px;
  width: 100%;
  z-index: 1000;
  color: #fff;
  user-select: none;

  .video-info-top {
    display: flex;
    align-items: center;
    margin-bottom: 10px;

    .video-info-author {
      font-size: 18px;
    }
    .video-info-time {
      padding: 0 10px;
      font-size: 14px;
    }
  }

  .video-info-desc {
    display: flex;
    align-items: center;
    font-size: 14px;

    .video-info-tag {
      padding: 0 10px;
      font-size: 14px;

      a {
        color: #f1c40f;
        text-decoration: none;
        padding: 0 5px;

        &:hover {
          text-decoration: underline;
          background-color: transparent;
        }
      }
    }
  }
}
</style>
