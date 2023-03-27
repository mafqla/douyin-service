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
    <div class="discover-container">
      <div class="discover-container-in">
        <discover-header />
        <discover-classify />
        <discover-new />
        <discover-recommend />
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.discover {
  height: 100%;
  position: relative;
  overflow: auto;

  &-container {
    color: rgba(22, 24, 35, 0.75);
    font-family: 'PingFang SC', DFPKingGothicGB-Regular, sans-serif;
    font-size: 20px;
    font-weight: 400;
    height: 100%;
    overflow-y: scroll;
    position: relative;

    &-in {
      overflow-x: visible;
    }
  }
}
</style>
