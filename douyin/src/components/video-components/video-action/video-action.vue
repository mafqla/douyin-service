<script setup lang="ts">
import { ref } from 'vue'
import { ElAvatar } from 'element-plus'

const props = defineProps({
  avatar: String,
  dianzan: Number,
  comment: Number,
  shoucang: Number,
  isLike: Boolean,
  isCollect: Boolean
})

const dianzan = ref(props.dianzan) as any
const liked = ref(props.isLike) as any
const addDianzan = () => {
  if (liked.value) {
    dianzan.value--
    liked.value = false
  } else {
    dianzan.value++
    liked.value = true
  }
}

const shoucang = ref(props.shoucang) as any
const isCollect = ref(props.isCollect) as any

// console.log(isCollect.value)
const addShoucang = () => {
  if (isCollect.value) {
    shoucang.value--
    isCollect.value = false
  } else {
    shoucang.value++
    isCollect.value = true
  }
}
</script>

<template>
  <div class="video-action">
    <div class="video-action-content">
      <slot />
      <div class="video-action-avatar">
        <ElAvatar size="small" :src="props.avatar" />
      </div>
      <div class="video-action-avatar-follow">
        <svg-icon class="icon" icon="avfollow" />
      </div>
    </div>
    <div class="video-action-item" @click="addDianzan">
      <svg-icon class="icon" :class="{ liked: liked }" icon="dianzan" />
      <span>{{ dianzan }}</span>
    </div>
    <div class="video-action-item" @click="$emit('toggleComments')">
      <svg-icon class="icon" icon="comment" :class="{ liked: liked }" />
      <span>{{ props.comment }}</span>
    </div>
    <div class="video-action-item" @click="addShoucang">
      <svg-icon
        class="icon-collect"
        icon="collection"
        :class="{ collect: isCollect }"
      />
      <span>{{ shoucang }}</span>
    </div>
    <div class="video-action-item">
      <svg-icon class="icon" icon="fenxiang" />
      <span>9</span>
    </div>

    <div class="video-action-item">
      <svg-icon class="icon" icon="more" />
    </div>
  </div>
</template>

<style lang="scss" scoped>
.video-action {
  position: absolute;
  bottom: 60px;
  // right: 38px;
  right: 0;
  height: auto;
  padding-right: 38px;
  z-index: 100;
  color: #fff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  flex-shrink: 0;

  .video-action-content {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    margin-bottom: 23px;
    margin-top: 24px;
    position: relative;

    .video-action-avatar {
      height: 40px;
      width: 40px;
      box-sizing: content-box;
      flex-grow: 0;
      flex-shrink: 0;
      border-radius: 50%;
      overflow: hidden;
      border: 1px solid rgba(231, 231, 236, 0.3) !important;

      .el-avatar {
        height: 100%;
        width: 100%;
      }
    }
    .video-action-avatar-follow {
      bottom: -12px;
      cursor: pointer;
      height: 24px;
      left: 0px;
      position: absolute;
      right: 0px;
      width: 24px;
      margin: 0px auto;
      display: flex;
      justify-content: center;
      .icon {
        height: 20px;
        width: 20px;
        opacity: 1;
        color: #fff;
      }
      .icon.liked {
        color: red !important;
      }
    }
  }
  .video-action-item {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;

    cursor: pointer;

    span {
      margin-left: 5px;
    }

    .icon {
      height: 50px;
      width: 50px;
      color: #fff;
      opacity: 1;
    }
    .icon-collect {
      height: 50px;
      width: 26px;
      color: #fff;
      opacity: 1;
      margin-left: 5px;
    }

    .icon.liked {
      color: rgb(254, 44, 85);
    }
    .icon-collect.collect {
      color: rgb(255, 184, 2) !important;
    }
  }
}
</style>
