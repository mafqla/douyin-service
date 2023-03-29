<script setup lang="ts">
import {} from 'vue'
</script>
<template>
  <div class="item-title-box">
    <div class="item-title-content">
      <div class="item-title-content-title">心理战大数据是如何操控大选的?</div>
      <div class="item-title-author">
        <span class="item-title-bottom"
          ><span class="item-title-author-f">@</span
          ><span class="item-title-author-name">小Lin说</span></span
        >
        <span class="item-title-tag fellow">
          <span class="item-title-tag-name">已关注</span>
        </span>
        <span class="item-title-tag comment">
          <span class="item-title-tag-name">评论</span>
        </span>
        <span class="item-title-tag collect">
          <span class="item-title-tag-name">收藏</span>
        </span>

        <span class="item-title-time"> · 23小时前</span>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.item-title-box {
  position: relative;
  flex: 1 1 0%;
  padding: 12px 20px 22px;
  background: rgba(249, 249, 249, 1);
  .item-title-content {
    position: relative;
    .item-title-content-title {
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 2;
      color: rgba(22, 24, 35, 1);
      // color: rgb(255, 255, 255);
      display: -webkit-box;
      font-size: 15px;
      height: 46px;
      line-height: 23px;
      text-overflow: ellipsis;
      word-break: break-all;
      overflow: hidden;
    }

    .item-title-author {
      display: inline-flex;
      font-size: 14px;
      line-height: 22px;
      margin-top: 4px;
      text-overflow: ellipsis;
      white-space: nowrap;
      width: 100%;
      word-break: break-all;
      overflow: hidden;
      .item-title-bottom {
        display: inline-block;
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
        color: rgba(22, 24, 35, 0.34);
        // color: rgba(255, 255, 255, 0.6);
        .item-title-author-f {
          font-size: 18px;
          line-height: 22px;
          vertical-align: text-top;
        }

        .item-title-author-name {
          position: relative;
          top: 1px;
          margin-left: 4px;
        }
      }

      .item-title-tag {
        display: flex;
        align-self: center;
        align-items: center;
        height: 18px;
        margin-left: 4px;
        border-radius: 2px;

        .item-title-tag-name {
          backface-visibility: hidden;
          display: inline-block;
          font-size: 12px;
          line-height: 1;
          transform: scale(0.9166);
          padding: 0px 3px;
        }
      }
      .item-title-tag.fellow {
        background: rgba(242, 242, 243, 1);
        color: rgba(22, 24, 35, 0.34);
      }
      .item-title-tag.comment {
        background: rgba(108, 208, 136, 0.1);
        color: rgba(108, 208, 136, 0.9);
      }
      .item-title-tag.collect {
        background: rgba(255, 153, 1, 0.1);
        color: rgba(255, 153, 1, 0.9);
      }

      .item-title-time {
        color: rgba(22, 24, 35, 0.34);
        font-family: PingFang SC, DFPKingGothicGB-Regular, sans-serif;
        font-size: 12px;
        font-weight: 400;
        line-height: 20px;
        line-height: 23px;
        margin-left: 5px;
      }
    }
  }
}
</style>
