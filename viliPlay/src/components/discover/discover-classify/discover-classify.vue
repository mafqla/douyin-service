<script setup lang="ts">
import {} from 'vue'
import DiscoverItem from '../discover-item/discover-item.vue'
</script>
<template>
  <div class="discover-classify">
    <div class="discover-content">
      <template v-for="item in 20">
        <discover-item />
      </template>
    </div>
    <div class="discover-bottom">
      <div>查看更多精选视频</div>
      <svg-icon class="icon" icon="discover-bottom-arrow" />
    </div>
  </div>
</template>

<style lang="scss" scoped>
.discover-classify {
  height: 100%;
  position: relative;
  padding: 16px 30px 0;
  //滚动条隐藏
  -ms-overflow-style: none;
  &::-webkit-scrollbar {
    display: none;
  }

  .discover-content {
    display: flex;
    flex-wrap: wrap;
    margin: 0px -8px;
  }

  .discover-bottom {
    align-items: center;
    border: 1px solid rgba(0, 0, 0, 0.1);
    border-radius: 4px;
    color: rgba(22, 24, 35, 0.34);
    cursor: pointer;
    display: flex;
    font-size: 13px;
    height: 40px;
    justify-content: center;
    line-height: 21px;
    text-align: center;

    .icon {
      width: 10px;
      height: 7px;
      margin-left: 4px;
      transform: rotate(0);
      transition: transform 0.3s;
      color: rgba(22, 24, 35, 0.34);
    }
  }
}
</style>
