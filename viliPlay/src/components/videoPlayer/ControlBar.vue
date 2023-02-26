<template>
  <div class="xgplayer-control">
    <div class="xgplayer-control-inner">
      <div class="xgplayer-control-play-btn xgplayer-iconfont"></div>
      <div class="xgplayer-control-progress-bar">
        <div class="xgplayer-control-progress-buffer"></div>
        <div class="xgplayer-control-progress">
          <div class="xgplayer-control-progress-time"></div>
          <div class="xgplayer-control-progress-bar-wrap">
            <div class="xgplayer-control-progress-bar-thumb"></div>
          </div>
          <div class="xgplayer-control-progress-duration"></div>
        </div>
      </div>
      <div class="xgplayer-control-fullscreen-btn xgplayer-iconfont"></div>
      <div class="xgplayer-control-danmu-btn xgplayer-iconfont"></div>
      <div class="xgplayer-control-volume">
        <div class="xgplayer-control-volume-icon xgplayer-iconfont"></div>
        <div class="xgplayer-control-volume-bar-wrap">
          <div class="xgplayer-control-volume-bar">
            <div class="xgplayer-control-volume-bar-thumb"></div>
          </div>
        </div>
      </div>
      <div class="xgplayer-control-time">
        <div class="xgplayer-control-time-current"></div>
        <div class="xgplayer-control-time-separator">/</div>
        <div class="xgplayer-control-time-duration"></div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {} from 'vue'
</script>
<style lang="scss" scoped>
.xgplayer-control {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 40px;
  background-color: rgba(0, 0, 0, 0.5);
  font-size: 14px;
  color: #fff;
  box-sizing: border-box;
  padding: 0 10px;
  z-index: 1000;
}

.xgplayer-control-inner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 100%;
}

.xgplayer-control-play-btn,
.xgplayer-control-fullscreen-btn,
.xgplayer-control-danmu-btn,
.xgplayer-control-volume-icon {
  font-size: 20px;
  cursor: pointer;
}

.xgplayer-control-progress-bar {
  flex-grow: 1;
  height: 4px;
  margin: 0 10px;
  background-color: #666;
  position: relative;
  cursor: pointer;
}

.xgplayer-control-progress-buffer {
  height: 100%;
  background-color: rgba(255, 255, 255, 0.3);
  position: absolute;
  left: 0;
  top: 0;
}

.xgplayer-control-progress {
  height: 100%;
  background-color: #fff;
  position: absolute;
  left: 0;
  top: 0;
  display: flex;
  flex-direction: row;
  align-items: center;
}

.xgplayer-control-progress-bar-wrap {
  flex-grow: 1;
  height: 100%;
  position: relative;
}

.xgplayer-control-progress-bar-thumb {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background-color: #fff;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
}

.xgplayer-control-time {
  display: flex;
  align-items: center;
}

.xgplayer-control-time-separator {
  margin: 0 5px;
}

.xgplayer-control-volume {
  display: flex;
  align-items: center;
  margin-right: 10px;
}

.xgplayer-control-volume-bar {
  width: 40px;
  height: 4px;
  margin: 0 10px;
  background-color: #666;
  position: relative;
  cursor: pointer;
}

.xgplayer-control-volume-bar-wrap {
  height: 100%;
  position: relative;
}

.xgplayer-control-volume-bar-thumb {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background-color: #fff;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
}

.xgplayer-control-volume-icon {
  font-size: 20px;
  margin-right: 5px;
  cursor: pointer;
}

.xgplayer-control-time-current,
.xgplayer-control-time-duration {
  margin: 0 5px;
}
</style>
