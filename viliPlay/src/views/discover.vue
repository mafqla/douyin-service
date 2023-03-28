<script setup lang="ts">
import {} from 'vue'
import {
  DiscoverClassify,
  DiscoverHeader,
  DiscoverNew,
  DiscoverRecommend
} from '@/components/discover'
</script>
<template>
  <div class="discover">
    <div height="904px">
      <div class="discover-container-in">
        <el-scrollbar height="886px">
          <discover-header />
          <discover-classify />
          <discover-new />
          <discover-recommend />
        </el-scrollbar>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.discover {
  height: 100%;
  position: relative;
  user-select: none;

  //滚动条隐藏
  -ms-overflow-style: none;
  &::-webkit-scrollbar {
    display: none;
  }

  &-container {
    color: rgba(22, 24, 35, 0.75);
    font-family: 'PingFang SC', DFPKingGothicGB-Regular, sans-serif;
    font-size: 20px;
    font-weight: 400;
    height: 100%;
    // overflow-y: scroll;

    position: relative;

    &-in {
      overflow-x: visible;
    }
  }
}
</style>
