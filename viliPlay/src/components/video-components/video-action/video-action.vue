<script setup lang="ts">
import {} from 'vue'
import { ElAvatar } from 'element-plus'
</script>

<template>
  <div class="video-action">
    <slot />
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
</template>

<style lang="scss" scoped>
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
</style>
