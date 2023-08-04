<script setup lang="ts">
import {} from 'vue'

const titleItems = ['抖音热榜', '娱乐榜', '社会榜', '挑战榜']
const selectedIndex = ref(0)

const selectItem = (index) => {
  selectedIndex.value = index
}
</script>
<template>
  <div
    class="hot-item"
    style="
      height: 698.062px;
      width: 328.5px;
      transform: translate(352.5px, 16px);
    "
  >
    <div class="hot-item-main">
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
                <img
                  src="//lf3-cdn-tos.bytegoofy.com/obj/goofy/ies/douyin_web//icons/hot/up.png"
                  alt=""
                />
              </div>
              <div class="hot-item-list-content-item-title-content">
                <div class="custom-link">
                  <a
                    href="/hot/1339865/%E6%9C%AC%E8%BD%AE%E5%BC%BA%E9%99%8D%E9%9B%A8%E6%8E%A5%E4%B8%8B%E6%9D%A5%E4%BC%9A%E5%BD%B1%E5%93%8D%E5%93%AA"
                    class="custom-title active"
                    target="_blank"
                  >
                    <h3>本轮强降雨接下来会影响哪</h3></a
                  >
                  <img
                    src="//lf3-cdn-tos.bytegoofy.com/obj/goofy/ies/douyin_web//icons/hot/hot_boom.png"
                    alt=""
                  />
                </div>
              </div>
            </li>

            <li class="hot-item-list-content-item">
              <div class="hot-item-list-content-text listStyle">
                <img
                  src="//lf3-cdn-tos.bytegoofy.com/obj/goofy/ies/douyin_web//icons/hot/hot_top1.png"
                  alt=""
                />
              </div>
              <div class="hot-item-list-content-item-title-content">
                <div class="custom-link">
                  <a
                    href="/hot/1339865/%E6%9C%AC%E8%BD%AE%E5%BC%BA%E9%99%8D%E9%9B%A8%E6%8E%A5%E4%B8%8B%E6%9D%A5%E4%BC%9A%E5%BD%B1%E5%93%8D%E5%93%AA"
                    class="custom-title active"
                    target="_blank"
                  >
                    <h3>中国女足1:6不敌英格兰</h3></a
                  >
                  <img
                    src="//lf3-cdn-tos.bytegoofy.com/obj/goofy/ies/douyin_web//icons/hot/hot_boom.png"
                    alt=""
                  />
                </div>

                <div class="hot-du">
                  <span class="hot-num">1136.2万</span><span>热度</span>
                </div>
              </div>
            </li>
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
    // background-color: #fff;
    background-color: rgba(37, 38, 50, 1);
    // border: 0.5px solid rgba(22, 24, 35, 0.06);
    border: 0.5px solid rgba(255, 255, 255, 0.04);
    border-radius: 12px;
    // box-shadow: 0 0 0.5px 0 #f2f2f4;
    box-shadow: 0 0 0.5px 0 rgba(242, 242, 243, 0.08);
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
          // color: rgba(22, 24, 35, 0.75);
          color: rgba(255, 255, 255, 0.75);
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
          // color: #000;
          color: rgba(255, 255, 255, 1);
          font-weight: 600;
        }
      }

      .hot-itme-line {
        // background-color: rgba(22, 24, 35, 0.06);
        background-color: rgba(255, 255, 255, 0.04);
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
            .hot-item-list-content-text {
              // color: rgba(22, 24, 35, 0.6);
              color: rgba(255, 255, 255, 0.5);
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
            .hot-item-list-content-text.listStyle {
            }

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
                    // color: #161823;
                    color: rgba(255, 255, 255, 0.9);
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
                // color: rgba(22, 24, 35, 0.6);
                color: rgba(255, 255, 255, 0.5);
                font-size: 14px;
                line-height: 22px;
                .hot-num {
                  font-family: PingFang SC, DFPKingGothicGB-Medium, sans-serif;
                  font-weight: 500;
                  padding-right: 4px;
                }
              }
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
}
</style>
