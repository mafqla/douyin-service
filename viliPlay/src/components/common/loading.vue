<script setup lang="ts">
import type {PropType} from 'vue'

defineProps({
  text: {
    type: String as PropType<string>,
    required: false,
    default: '正在加载中...' // 默认加载文本
  },
  show: {
    type: Boolean as PropType<boolean>,
    required: false,
    default: true // 默认显示
  },
  isShowText: {
    type: Boolean as PropType<boolean>,
    required: false,
    default: false // 默认显示
  }
})
</script>
<template>
  <div class="loading" v-if="show">
    <div class="loading-content">
      <div class="loading-content-img"></div>
      <div class="loading-content-text" v-if="isShowText">{{ text }}</div>
    </div>
  </div>
  <slot v-if="!show"/>
</template>

<style lang="scss" scoped>
.loading {
  margin-top: 40px;
  padding-bottom: 40px;
  text-align: center;

  .loading-content {
    align-items: center;
    display: flex;
    flex-direction: column;
    justify-content: center;
    position: relative;
    width: 100%;

    .loading-content-img {
      animation: loading 1s steps(60, start) infinite;
      background-image: url(@/assets/loading.png);
      background-size: 48px;
      display: inline-block;
      font-size: 0;
      height: 48px;
      transform: scale(0.7);
      width: 48px;
      overflow: hidden;
    }
  }
}

@keyframes loading {
  to {
    background-position-y: -2880px;
  }
}
</style>
