<script setup lang="ts">
import { onMounted, ref } from 'vue'
import SwiperControl from '@/components/swper/swiper-control.vue'
import miniPlayer from '@/components/video-player/mini-player.vue'
import SwiperVideo from '@/components/swper/swiper-video.vue'
import type { IFeedParams, IVideoList } from '@/service/videos/videosType'
import { getVideoList } from '@/service/videos/videos'

const loading = ref(true)
const page = ref(1)
const size = ref(10)
const list = ref<IVideoList[]>([])
const getData = async (params: IFeedParams) => {
  try {
    const res = await getVideoList(params)
    // console.log(res)

    const { data } = res

    setTimeout(() => {
      loading.value = false
    }, 1000)

    list.value.push(...data)
  } catch (err) {
    console.log(err)
  }
}
onMounted(() => {
  getData({
    page: page.value,
    size: size.value
  })
})
</script>
<template>
  <div class="recommend">
    <Loading :show="loading" :isShowText="true" :center="true">
      <div class="xgplayer-playswitch">
        <swiper-control />
      </div>

      <div class="slide-list">
        <swiper-video :videoList="list" />
      </div>
    </Loading>
  </div>
</template>

<style lang="scss" scoped>
.recommend {
  width: 100%;
  min-height: 80vh;
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  height: 878px;
  overflow: hidden;
  position: relative;

  .xgplayer-playswitch {
    flex-shrink: 0;
  }
  @media only screen and (min-width: 580px) {
    .xgplayer-playswitch {
      position: absolute;
      right: 13px;
      top: calc(50% + 60px);
      transform: translateY(calc(-50% - 30px));
      z-index: 2;
    }
  }

  @media only screen and (max-width: 580px) {
    .xgplayer-playswitch {
      display: none;
    }
  }

  @media only screen and (min-width: 878px) {
    .IvL3R0Q4 {
      position: absolute;
      right: 13px;
      top: calc(50% + 60px);
      transform: translateY(calc(-50% - 30px));
      z-index: 2;
    }
  }

  .slide-list {
    display: flex;
    flex: 1 1;
    flex-direction: column;
    height: 100%;
    overflow: hidden;
    position: relative;
  }
}
</style>
