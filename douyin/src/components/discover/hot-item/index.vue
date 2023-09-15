<script setup lang="ts">
import { ref, type PropType, computed } from 'vue'

import hot_boom from '@/assets/hot/hot_boom.png'
import hot_first from '@/assets/hot/hot_first.png'
import hot_hot from '@/assets/hot/hot_hot.png'
import hot_exclusive from '@/assets/hot/hot_exclusive.png'

import hot_top1 from '@/assets/hot/hot_top1.png'
import hot_top2 from '@/assets/hot/hot_top2.png'
import hot_top3 from '@/assets/hot/hot_top3.png'
import { useElementSize } from '@vueuse/core'
import { discoverStore } from '@/stores/discover'

interface ITopData {
  id: number
  title: string
}

interface IListData {
  id: number
  title: string
  hotNum: number
  imgType?: string
}
const props = defineProps({
  titleItems: {
    type: Array,
    default: () => ['抖音热榜', '娱乐榜', '社会榜', '挑战榜']
  },
  topData: {
    type: Object as PropType<ITopData>,
    required: true
  },
  listData: {
    type: Array<IListData>,
    required: true
  }
})

const imgTypeToSrc = {
  hot_boom: hot_boom,
  hot_first: hot_first,
  hot_hot: hot_hot,
  hot_exclusive: hot_exclusive
}

//使用计算属性，处理图片路径
const list = computed(() => {
  return props.listData.map((item) => {
    //@ts-ignore
    item.imgType = imgTypeToSrc[item.imgType]
    return item
  })
})

//处理1-3名的图片路径
const getImgSrc = (id: number) => {
  if (id === 1) {
    return hot_top1
  } else if (id === 2) {
    return hot_top2
  } else if (id === 3) {
    return hot_top3
  }
}

// const titleItems = ['抖音热榜', '娱乐榜', '社会榜', '挑战榜']
const selectedIndex = ref(0)

const selectItem = (index: number) => {
  selectedIndex.value = index
}

const el = ref(null)
const { height } = useElementSize(el)

setTimeout(() => {
  // console.log('hot:', height)
  discoverStore().hotHeight = height.value
}, 2000)
</script>
<template>
  <div class="hot-item" :style="{ height: `${height}px` }">
    <div class="hot-item-main" ref="el">
      <div class="hot-item-blank"></div>
      <div class="hot-item-content">
        <div class="hot-item-content-title">
          <div
            v-for="(item, index) in titleItems"
            :key="index"
            :class="{
              'hot-item-content-title-item': true,
              selected: selectedIndex === index
            }"
            @click="selectItem(index)"
          >
            {{ item }}
          </div>
        </div>
        <div class="hot-itme-line"></div>

        <div class="hot-item-list">
          <ul class="hot-item-list-content hotChangableList">
            <li class="hot-item-list-content-item">
              <div class="hot-item-list-content-text listStyle">
                <img src="../../../assets/hot/up.png" alt="" />
              </div>
              <div class="hot-item-list-content-item-title-content">
                <div class="custom-link">
                  <RouterLink
                    :to="{ name: 'discover', query: { modal_id: topData.id } }"
                    class="custom-title active"
                  >
                    <h3>{{ topData.title }}</h3>
                  </RouterLink>
                </div>
              </div>
            </li>

            <template v-for="item in list" :key="item.id">
              <li class="hot-item-list-content-item">
                <div class="hot-item-list-content-text listStyle">
                  <img :src="getImgSrc(item.id)" alt="" v-if="item.id <= 3" />

                  <svg-icon
                    :icon="`icon-${parseInt(item.id.toString()[0], 10)}`"
                    class="icon"
                    v-if="item.id > 3"
                  />
                  <svg-icon
                    :icon="`icon-${parseInt(item.id.toString()[1], 10)}`"
                    class="icon"
                    v-if="item.id >= 10"
                  />
                </div>
                <div class="hot-item-list-content-item-title-content">
                  <div class="custom-link">
                    <RouterLink
                      :to="{ name: 'discover', query: { modal_id: item.id } }"
                      class="custom-title active"
                    >
                      <h3>{{ item.title }}</h3>
                    </RouterLink>
                    <img :src="item.imgType" alt="" />
                  </div>

                  <div class="hot-du">
                    <span class="hot-num">{{ item.hotNum }}</span>
                    <span>热度</span>
                  </div>
                </div>

                <div
                  class="hot-item-content-show"
                  style="text-align: center; width: 202px"
                >
                  {{ item.title }}
                </div>
              </li>
            </template>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.hot-item {
  position: absolute;
  will-change: transform;

  .hot-item-main {
    background-color: #fff;
    // background-color: rgba(37, 38, 50, 1);
    border: 0.5px solid rgba(22, 24, 35, 0.06);
    // border: 0.5px solid rgba(255, 255, 255, 0.04);
    border-radius: 12px;
    box-shadow: 0 0 0.5px 0 #f2f2f4;
    // box-shadow: 0 0 0.5px 0 rgba(242, 242, 243, 0.08);
    overflow: hidden;
    position: relative;
    width: 100%;

    .hot-item-blank {
      padding-bottom: 212.5%;
      width: 100%;
    }

    .hot-item-content {
      display: flex;
      flex-direction: column;
      height: 100%;
      position: absolute;
      top: 0;
      width: 100%;

      .hot-item-content-title {
        display: flex;
        height: 46px;
        justify-content: space-between;
        padding: 0 16px;
        width: 100%;

        .hot-item-content-title-item {
          align-items: center;
          color: rgba(22, 24, 35, 0.75);
          // color: rgba(255, 255, 255, 0.75);
          cursor: pointer;
          display: flex;
          font-family: PingFang SC;
          font-size: 14px;
          font-style: normal;
          font-weight: 400;
          height: 100%;
          line-height: 22px;
        }

        .hot-item-content-title-item.selected {
          border-bottom: 3px solid #fe2c55;
          border-top: 3px solid transparent;
          color: #000;
          // color: rgba(255, 255, 255, 1);
          font-weight: 600;
        }
      }

      .hot-itme-line {
        background-color: rgba(22, 24, 35, 0.06);
        // background-color: rgba(255, 255, 255, 0.04);
        display: block;
        height: 1px;
        min-height: 1px;
        position: relative;
        width: 100%;
      }

      .hot-item-list {
        flex: 1 1;
        margin-right: 4px;
        overflow: scroll;
        overflow-x: hidden;

        .hot-item-list-content {
          .hot-item-list-content-item {
            display: flex;
            overflow: hidden;
            padding-bottom: 18px;
            position: relative;
            width: 100%;

            &:hover {
              h3 {
                color: #ff2c55 !important;
              }

              .hot-item-content-show {
                display: block;
              }
            }
            .hot-item-list-content-text {
              color: rgba(22, 24, 35, 0.6);
              // color: rgba(255, 255, 255, 0.5);
              flex-shrink: 0;
              font-size: 21px;
              font-weight: 700;
              line-height: 24px;
              margin-right: 4px;
              text-align: center;
              width: 24px;

              img {
                height: 24px;
                width: 24px;
              }
            }
            // .hot-item-list-content-text.listStyle {
            // }

            .hot-item-list-content-item-title-content {
              overflow: hidden;
              .custom-link {
                align-items: center;
                display: flex;
                font-family: PingFang SC, DFPKingGothicGB-Medium, sans-serif;
                font-weight: 500;
                padding-bottom: 4px;
                .custom-title {
                  position: relative;
                  h3 {
                    color: #161823;
                    // color: rgba(255, 255, 255, 0.9);
                    font-size: 16px;
                    line-height: 24px;
                    margin-right: 5px;
                  }
                }
                img {
                  height: 16px;
                  width: auto;
                }
              }
              .active,
              h3 {
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
              }

              .hot-du {
                color: rgba(22, 24, 35, 0.6);
                // color: rgba(255, 255, 255, 0.5);
                font-size: 14px;
                line-height: 22px;
                .hot-num {
                  font-family: PingFang SC, DFPKingGothicGB-Medium, sans-serif;
                  font-weight: 500;
                  padding-right: 4px;
                }
              }
            }

            .hot-item-content-show {
              background-color: #f2f2f4;
              // background-color: rgba(51,52,63,1);
              border: 1px solid rgba(22, 24, 35, 0.2);
              // border: 1px solid rgba(255, 255, 255, 0.12);
              border-radius: 4px;
              color: #161823;
              // color: rgba(255, 255, 255, 0.9);
              display: none;
              filter: drop-shadow(0 12px 24px rgba(0, 0, 0, 0.04));
              // filter: drop-shadow(0 12px 24px rgba(0, 0, 0, 0.6));
              font-size: 12px;
              left: 50%;
              line-height: 20px;
              max-width: 100%;
              padding: 0 10px;
              position: absolute;
              text-align: center;
              top: 24px;
              transform: translateX(-50%);
              z-index: 1;
            }
          }
        }
        .hotChangableList.hot-item-list-content {
          padding: 16px 8px 0 12px;
        }
        .hotChangableList .hot-item-list-content-item {
          overflow: visible;
          padding-bottom: 16px;

          .hot-du .hot-num {
            font-weight: 400;
          }
          .active h3 {
            font-size: 15px !important;
            font-weight: 500;
          }
        }
      }
    }
  }

  .icon {
    width: 12px;
    height: 24px;
    color: rgba(22, 24, 35, 0.6);
    // color: rgba(255, 255, 255, 0.5);
  }
}
</style>
