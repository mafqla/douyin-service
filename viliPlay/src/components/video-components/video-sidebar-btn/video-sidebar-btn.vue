<script setup lang="ts">
import {} from 'vue'
</script>

<template>
  <div class="video-sidebar-btn">
    <button class="video-sidebar-btn-cl" type="button">
      <div class="video-sidebar-btn-bg"></div>
      <svg-icon class="icon" icon="sidebar-btn" />
    </button>
  </div>
</template>

<style lang="scss" scoped>
.video-sidebar-btn {
  position: absolute;
  right: 0px;
  top: 50%;
  display: inline-block;
  transform: translateY(-50%);
  z-index: 200;
  cursor: pointer;
  height: 48px;
  width: 32px;
  border-width: 0px;
  border-style: initial;
  border-color: initial;
  border-image: initial;

  .video-sidebar-btn-cl {
    align-items: center;
    background-color: unset;
    display: flex;
    height: 48px;
    justify-content: space-around;
    transform-origin: right center;
    width: 32px;
    border-width: 0px;
    border-style: initial;
    border-color: initial;
    border-image: initial;
    border-radius: 24px 0px 0px 24px;
    transition: all 0.3s linear 0s;

    .video-sidebar-btn-bg {
      background: rgba(0, 0, 0, 0.18);
      border-radius: 24px 0 0 24px;
      bottom: 0;
      left: 0;
      position: absolute;
      right: 0;
      top: 0;
    }

    .icon {
      width: 10px;
      height: 18px;
      vertical-align: middle;
      color: hsla(0, 0%, 100%, 0.25);
      transition: unset;
      transform: rotate(180deg);

      &:hover {
        color: #fff;
      }
    }
  }
}

.video-sidebar-btn:hover {
  .video-sidebar-btn-cl {
    transform: scale(1.2);

    .video-sidebar-btn-bg {
      background: rgba(0, 0, 0, 0.25);
    }
  }
}
</style>
