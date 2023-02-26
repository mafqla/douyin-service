<script setup lang="ts">
import { onMounted, ref, watchEffect } from 'vue'
import VideoPlayer from '@/components/videoPlayer/index.vue'
import { getVideoList } from '@/service/videos/videos'
import type { IVideoList, IVideoListResult } from '@/service/videos/videosType'
import { ElAvatar, ElMessage } from 'element-plus'

const page = ref(1)
const pageSize = ref(10)
const status = ref(0)

let video: IVideoList = {
  page: page.value,
  size: pageSize.value,
  status: status.value
}

const videosList = ref<IVideoListResult[]>([])

onMounted(async () => {
  const res = await getVideoList(video)
  //@ts-ignore
  videosList.value = res.data
})

const currentIndex = ref(0)
const translateY = ref(0)
const timer: any = ref(null)
const isDisabled = ref(false)
const isPlay = ref(false)

//点击上一张 ，当前索引为0时，不能再点击
const handlePrev = () => {
  if (currentIndex.value === 0) {
    isDisabled.value = true
    return
  }
  // 暂停上一个视频
  isPlay.value = true
  currentIndex.value--
  translateY.value += 893
  isDisabled.value = false
}

const handleNext = () => {
  if (currentIndex.value === videosList.value.length - 1) {
    isDisabled.value = true
    ElMessage({
      message: '没有了🤣🤣🤣，欢迎来投稿！',
      type: 'warning'
    })
    return
  }
  // 暂停上一个视频
  isPlay.value = true
  currentIndex.value++
  translateY.value -= 893
  isDisabled.value = false
}
console.log(isPlay.value)
</script>
<template>
  <div class="carousel">
    <div
      class="carousel-inner"
      :style="{ transform: 'translate3d(0px,' + translateY + 'px, 0px)' }"
    >
      <div class="carousel-item" v-for="item in videosList" :key="item.id">
        <video-player
          :url="item.videosAddress"
          :poster="item.videosCover"
          :key="item.id"
        />

        <div class="video-info">
          <div class="video-info-top">
            <div class="video-info-author">
              <span>@{{ item.username }}</span>
            </div>
            <div class="video-info-time">
              <span>{{ item.uploadTime }}</span>
            </div>
          </div>
          <div class="video-info-desc">
            <span>{{ item.description }}</span>
            <div class="video-info-tag">
              <a href="#"> <span>#愿所有的美好和期待2023都能如约而至</span> </a>
              <a href="#"> <span>#愿所有的美好和期待2023都能如约而至</span> </a>
              <a href="#"> <span>#愿所有的美好和期待2023都能如约而至</span> </a>
            </div>
          </div>
        </div>

        <div class="video-action">
          <div class="video-action-content">
            <div class="video-action-avatar">
              <ElAvatar
                size="small"
                src="https://img2.baidu.com/it/u=1818181818,1818181818&fm=26&fmt=auto&gp=0.jpg"
              />
            </div>
            <div class="video-action-avatar-follow">
              <svg-icon class="icon" icon="avfollow" />
            </div>
          </div>
          <div class="video-action-item">
            <svg-icon class="icon" icon="dianzan" />
            <span>999</span>
          </div>
          <div class="video-action-item">
            <svg-icon class="icon" icon="comment" />
            <span>999</span>
          </div>
          <div class="video-action-item">
            <span class="iconfont icon-shoucang"></span>
            <svg-icon class="icon" icon="shoucang" />
            <span>999</span>
          </div>
          <div class="video-action-item">
            <svg-icon class="icon" icon="fenxiang" />
            <span>999</span>
          </div>

          <div class="video-action-item">
            <svg-icon class="icon" icon="more" />
          </div>
        </div>
      </div>
    </div>
    <div class="carousel-controls">
      <div class="carousel-controls-switch">
        <div
          class="carousel-controls-switch-up"
          :class="{ disabled: isDisabled }"
          @click="handlePrev"
        >
          <svg-icon class="icon" icon="video-switch-prev-arrow" />
        </div>
        <div
          class="carousel-controls-switch-down"
          :class="{ disabled: isDisabled }"
          @click="handleNext"
        >
          <svg-icon class="icon" icon="video-switch-next-arrow" />
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
  .carousel-inner {
    width: 100%;
    height: 100%;
    .carousel-item {
      position: relative;

      flex-grow: 1;
      height: 100%;
      opacity: 1;
      width: 100%;
      height: 881px;
      margin-bottom: 12px;
      border-radius: 4px;
      overflow: hidden;
      transition: all 0.15s linear 0s;

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

      .video-action {
        position: absolute;
        bottom: 60px;
        right: 0;
        height: auto;
        padding-right: 38px;
        z-index: 1000;
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
          .video-action-avatar {
            height: 40px;
            width: 40px;
            box-sizing: content-box;
            flex-grow: 0;
            flex-shrink: 0;
            position: relative;
            border-radius: 50%;
            overflow: hidden;
            border: 1px solid rgba(231, 231, 236, 0.3) !important;
            position: relative;

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
        }
      }
    }
  }
  .carousel-controls {
    position: absolute;
    right: 13px;
    top: 5%;
    transform: translateY(-50%);
    z-index: 2;

    .carousel-controls-switch {
      background-color: rgb(50, 52, 66);
      height: 80px;
      opacity: 0.7;
      position: relative;
      width: 36px;
      border-radius: 18px;

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
}
</style>
