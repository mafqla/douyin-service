<script setup lang="ts">
import { nextTick, onMounted, onBeforeUnmount } from 'vue'
import Player from 'xgplayer'
let canvas: any,
  ctx: {
    drawImage: (
      arg0: Element | null,
      arg1: number,
      arg2: number,
      arg3: any,
      arg4: any
    ) => void
  },
  video: any,
  drawId: number

onMounted(() => {
  new Player({
    id: 'video-player',
    url: 'http://192.168.227.1:8080/res/77bc5dac-b7d8-4c86-a1d7-b161b1592de2.mp4',
    fluid: true,
    autoplay: true,
    loop: true,
    controls: true,
    lang: 'zh-cn',
    volume: 0,
    keyShortcut: 'on',
    cssFullscreen: true,
    playsinline: true,
    playbackRate: [0.5, 1.0, 1.5, 1.75, 2]
  })
})

// 组件挂载后，等待下一帧再执行
nextTick(() => {
  // 延迟执行，等待视频加载完成
  setTimeout(() => {
    canvas = document.querySelector('#canvas-blur')
    ctx = canvas.getContext('2d')
    video = document.querySelector('#video-player > video')
    canvas.width = video.videoWidth
    canvas.height = video.videoHeight

    function draw() {
      ctx.drawImage(video, 0, 0, canvas.width, canvas.height)
      drawId = requestAnimationFrame(draw)
    }
    draw()
    cancelAnimationFrame(drawId)
  }, 1000)
})

onBeforeUnmount(() => {
  cancelAnimationFrame(drawId)
})
</script>

<template>
  <div class="video-play">
    <div id="video-player" class="video-player">
      <canvas id="canvas-blur" class="canvas-blur"></canvas>

      <div class="video-info">
        <div class="video-info-top">
          <div class="video-info-author">
            <span>@视频作者</span>
          </div>
          <div class="video-info-time">
            <span>2023-01-08</span>
          </div>
        </div>
        <div class="video-info-desc">
          <span>视频描述</span>
          <div class="video-info-tag">
            <a href="#"> <span>#愿所有的美好和期待2023都能如约而至</span> </a>
            <a href="#"> <span>#愿所有的美好和期待2023都能如约而至</span> </a>
            <a href="#"> <span>#愿所有的美好和期待2023都能如约而至</span> </a>
          </div>
        </div>
      </div>

      <div class="video-action">
        <div class="video-action-avatar">
          <div class="video-action-avatar">
            <img
              src="https://img2.baidu.com/it/u=1818181818,1818181818&fm=26&fmt=auto&gp=0.jpg"
              alt=""
            />
          </div>
          <div class="video-action-avatar-follow">
            <span>关注图标</span>
          </div>
        </div>
        <div class="video-action-item">
          <span class="iconfont icon-dianzan"></span>
          <span>点赞</span>
          <span>999</span>
        </div>
        <div class="video-action-item">
          <span class="iconfont icon-pinglun"></span>
          <span>评论</span>
          <span>999</span>
        </div>
        <div class="video-action-item">
          <span class="iconfont icon-shoucang"></span>
          <span>收藏</span>
          <span>999</span>
        </div>
        <div class="video-action-item">
          <span class="iconfont icon-fenxiang"></span>
          <span>分享</span>
          <span>999</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.video-player {
  position: relative;
  z-index: 1;
  border-radius: 6px;

  .canvas-blur {
    display: flex;
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    z-index: -1;
    filter: blur(10px);
  }

  .video-info {
    position: absolute;
    bottom: 40px;
    left: 0;
    width: 100%;
    height: 100px;
    padding: 16px 95px 16px 16px;
    width: 100%;
    z-index: 1;
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
    z-index: 1;
    color: #fff;
    display: flex;
    flex-direction: column;
    justify-content: center;
    flex-shrink: 0;

    .video-action-item {
      display: flex;
      align-items: center;
      justify-content: center;
      flex-direction: column;
      height: 20px;
      margin-bottom: 23px;
      margin-top: 24px;
      cursor: pointer;

      span {
        margin-left: 5px;
      }
    }
  }
}
</style>
