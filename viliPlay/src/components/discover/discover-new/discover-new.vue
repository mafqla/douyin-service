<script setup lang="ts">
import {} from 'vue'
import DiscoverBottom from '../discover-bottom/discover-bottom.vue'
import newItem from './new-item.vue'

const newData = [
  {
    id: 1,
    title: '中国是亚洲和世界经济稳定器',
    describe: '中国是亚洲和世界经济的“稳定器”',
    img: 'https://p9-pc-sign.douyinpic.com/obj/image-cut-tos-priv/1da2478bce49583d31bc159c15c47292?biz_tag=&from=65706370&s=&sc=&se=false&sh=&x-expires=1680123600&x-signature=BZA%2BtgtaP78zqmbfKiX2i%2BCCwTI%3D',
    playNumber: '1098.1万',
    playTime: '00:31',
    author: '人民网',
    rank: 0
  },
  {
    id: 2,
    title: '中国是亚洲和世界经济稳定器',
    describe: '中国是亚洲和世界经济的“稳定器”',
    img: 'https://p9-pc-sign.douyinpic.com/obj/image-cut-tos-priv/1da2478bce49583d31bc159c15c47292?biz_tag=&from=65706370&s=&sc=&se=false&sh=&x-expires=1680123600&x-signature=BZA%2BtgtaP78zqmbfKiX2i%2BCCwTI%3D',
    playNumber: '1098.1万',
    playTime: '00:31',
    author: '人民网',
    rank: 1
  },
  {
    id: 3,
    title: '中国是亚洲和世界经济稳定器',
    describe: '中国是亚洲和世界经济的“稳定器”',
    img: 'https://p9-pc-sign.douyinpic.com/obj/image-cut-tos-priv/1da2478bce49583d31bc159c15c47292?biz_tag=&from=65706370&s=&sc=&se=false&sh=&x-expires=1680123600&x-signature=BZA%2BtgtaP78zqmbfKiX2i%2BCCwTI%3D',
    playNumber: '1098.1万',
    playTime: '00:31',
    author: '人民网',
    rank: 2
  },
  {
    id: 4,
    title: '中国是亚洲和世界经济稳定器',
    describe: '中国是亚洲和世界经济的“稳定器”',
    img: 'https://p9-pc-sign.douyinpic.com/obj/image-cut-tos-priv/1da2478bce49583d31bc159c15c47292?biz_tag=&from=65706370&s=&sc=&se=false&sh=&x-expires=1680123600&x-signature=BZA%2BtgtaP78zqmbfKiX2i%2BCCwTI%3D',
    playNumber: '1098.1万',
    playTime: '00:31',
    author: '人民网',
    rank: 3
  },
  {
    id: 5,
    title: '中国是亚洲和世界经济稳定器',
    describe: '中国是亚洲和世界经济的“稳定器”',
    img: 'https://p9-pc-sign.douyinpic.com/obj/image-cut-tos-priv/1da2478bce49583d31bc159c15c47292?biz_tag=&from=65706370&s=&sc=&se=false&sh=&x-expires=1680123600&x-signature=BZA%2BtgtaP78zqmbfKiX2i%2BCCwTI%3D',
    playNumber: '1098.1万',
    playTime: '00:31',
    author: '人民网',
    rank: 4
  },
  {
    id: 6,
    title: '中国是亚洲和世界经济稳定器',
    describe: '中国是亚洲和世界经济的“稳定器”',
    img: 'https://p9-pc-sign.douyinpic.com/obj/image-cut-tos-priv/1da2478bce49583d31bc159c15c47292?biz_tag=&from=65706370&s=&sc=&se=false&sh=&x-expires=1680123600&x-signature=BZA%2BtgtaP78zqmbfKiX2i%2BCCwTI%3D',
    playNumber: '1098.1万',
    playTime: '00:31',
    author: '人民网',
    rank: 5
  }
]
</script>
<template>
  <div class="discover-new">
    <div class="discover-new-title">抖音热榜</div>

    <template v-for="item in newData" :key="item.id">
      <new-item :item="item" />
    </template>
    <discover-bottom>
      <div>查看更多热榜内容</div>
    </discover-bottom>
  </div>
</template>

<style lang="scss" scoped>
.discover-new {
  font-family: PingFang SC, DFPKingGothicGB-Regular, sans-serif;
  font-weight: 400;
  padding: 8px 30px 24px;
  min-width: 508px;
  height: 100%;
  position: relative;

  .discover-new-title {
    // color: #000;
    color: rgba(0, 0, 0, 1);
    font-size: 22px;
    line-height: 34px;
    margin-bottom: 24px;
  }
}

@media (min-width: 2472px) {
  .discover-new {
    padding: 8px 0 24px;
  }
}
</style>
