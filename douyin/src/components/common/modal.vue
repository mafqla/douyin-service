<script setup lang="ts">
import type { IVideoList } from '@/service/videos/videosType'
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()
// 点击获取数据
const modalData = reactive({
  id: 0,
  username: '',
  uploadTime: '',
  description: '',
  url: '',
  poster: '',
  isPlay: false,
  img: '',
  dianzan: 0,
  comment: 0,
  shoucang: 0,
  globalVolume: 0,

  isLike: false,
  isCollect: false,
  isAttention: false
})

//关闭modal
const handleClose = () => {
  //去除路由参数
  router.push({
    query: {
      ...router.currentRoute.value.query,
      modal_id: undefined
    }
  })
}
</script>
<template>
  <model-player v-bind="modalData" @closeBtn="handleClose" />
</template>

<style lang="scss" scoped></style>
