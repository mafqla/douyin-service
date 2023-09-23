<script setup lang="ts">
import {} from 'vue'
</script>
<template>
  <div class="download-btn btnCommon" >
    <div class="content">
      <div class="content-text  ">下载客户端，随时接收消息</div>
      <a href="" download="" class="content-btn hoverRed"><span>下载</span> </a>
    </div>
    <div class="wdgWdzI8"></div>
  </div>
</template>

<style lang="scss" scoped>
.download-btn {
  height: 36px;
  overflow: hidden;
  transition: all 0.3s ease-in-out;
  .wdgWdzI8 {
    background: hsla(0, 0%, 100%, 0.04);
    height: 1px;
    margin-top: 10px;
    width: 100%;
  }
  &:hover {
    .hoverRed {
      // background: #ff2c55 !important;
      background: var(--color-primary-default) !important;
      color: #fff;
    }
  }
  .content {
    align-items: center;
    background-color: var(--color-secondary-default);
    border-radius: 4px;
    color: var(--color-text-t0);
    display: flex;
    flex-direction: row;
    font-family: PingFang SC, DFPKingGothicGB-Regular, sans-serif;
    font-size: 13px;
    font-weight: 400;
    justify-content: space-between;
    line-height: 21px;
    overflow: hidden;

    .content-text {
      padding: 8px 0 8px 12px;
      white-space: nowrap;
    }
    .content-btn {
      align-items: center;
      // background-color: #e4e4e6;
      background-color: var(--color-secondary-hover);
      // background-color: rgba(228, 228, 230, 1);
      cursor: pointer;
      display: flex;
      flex-shrink: 0;
      height: 100%;
      padding: 8px 12px;
      transition: all 0.3s ease-in;
      white-space: nowrap;
      span {
        font-family: PingFang SC, DFPKingGothicGB-Regular, sans-serif;
        font-weight: 400;
        font-weight: 600;
      }
    }
  }
}
.btnCommon {
  margin: 12px 12px 12px 16px;
  min-width: 206px;
  -webkit-user-select: none;
  -ms-user-select: none;
  user-select: none;
}
</style>
