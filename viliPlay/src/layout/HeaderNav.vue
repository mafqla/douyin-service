<script setup lang="ts">
import { ref } from 'vue'

//切换深色主题
const isDark = ref(false)

//切换主题
const toggleTheme = () => {
  isDark.value = !isDark.value
}
</script>

<template>
  <div class="header">
    <div class="header-left">
      <div class="header-left-search">
        <input
          type="text"
          class="header-search-input"
          placeholder="搜索你感兴趣的内容"
        />
        <div class="header-search-icon">
          <button>
            <svg-icon class="icon-search" icon="search" />
            <span class="btn-title">搜索</span>
          </button>
        </div>
      </div>
    </div>
    <div class="header-right">
      <div class="header-right-item">
        <div class="header-right-item-overplay">
          <svg-icon class="icon" icon="expand" />
        </div>
        <p>更多</p>
      </div>
      <div class="header-right-item">
        <div class="header-right-item-overplay">
          <svg-icon class="icon" icon="client" />
        </div>
        <p>客户端</p>
      </div>
      <div class="header-right-item">
        <div class="header-right-item-overplay">
          <svg-icon class="icon" icon="collection" />
        </div>
        <p>收藏网页</p>
      </div>
      <div class="header-right-item">
        <div class="header-right-item-overplay">
          <svg-icon class="icon" icon="cooperation" />
        </div>
        <p>合作</p>
      </div>
      <div class="header-right-item">
        <div class="header-right-item-overplay">
          <svg-icon class="icon" icon="tip" />
        </div>
        <p>通知</p>
      </div>
      <div class="header-right-item">
        <div class="header-right-item-overplay">
          <svg-icon class="icon" icon="chat" />
        </div>
        <p>私信</p>
      </div>
      <div class="header-right-item">
        <div class="header-right-item-overplay">
          <svg-icon class="icon" icon="post" />
        </div>
        <p>投稿</p>
      </div>
      <el-divider direction="vertical" />
      <div class="header-right-avatar">
        <el-avatar
          size="small"
          src="https://p6-pc-sign.douyinpic.com/aweme/100x100/aweme-avatar/tos-cn-i-0813c001_fddf54d2b1544d0aa4f0987fefc73f65.jpeg?x-expires=1675152000&x-signature=JTdm%2Bj2WlXsLsFrOWhx1f5%2BLW64%3D&from=2480802190"
        />
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;

  .header-left {
    display: flex;
    align-items: center;
    height: 100%;
    width: 450px;
    padding: 0 8px;

    .header-left-search {
      display: flex;
      align-items: center;
      width: 100%;
      height: 40px;
      position: relative;

      .header-search-icon {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 69px;
        position: absolute;
        right: 0;
        top: 0;
        height: 100%;

        button {
          display: flex;
          align-items: center;
          justify-content: center;
          width: 100%;
          height: 100%;
          border: none;
          background-color: transparent;
          cursor: pointer;

          .icon-search {
            width: 18px;
            height: 18px;
          }

          .btn-title {
            color: #2f3035;
            margin-left: 3px;
            font-size: 16px;
            font-weight: 500;
            user-select: none;
          }

          &:hover {
            background-color: #000;
            border-bottom-right-radius: 6px;
            border-top-right-radius: 6px;

            .icon-search {
              color: #fff;
            }
            .btn-title {
              color: #fff;
            }
          }
        }
      }

      .header-search-input {
        display: flex;
        flex-direction: row;
        flex-grow: 1;
        flex-shrink: 1;
        align-items: center;
        border-radius: 6px;
        height: 40px;
        width: 100%;
        padding: 0 10px;
        background-color: #fff;
        border: 2px solid rgba(0, 0, 0, 0.7);
        caret-color: #fe2c55;

        &::placeholder {
          color: rgba(47, 48, 53, 0.4);
          opacity: 1;
          font-size: 14px;
          font-weight: 200;
          line-height: 40px;
        }

        &:active {
          border: 2px solid rgba(0, 0, 0, 0.7);
        }
      }
    }
  }
  .header-right {
    display: flex;
    align-items: center;
    .header-right-item {
      display: flex;
      align-items: center;
      justify-content: center;
      flex-direction: column;
      width: 48px;

      .header-right-item-overplay {
        background-color: #e7e7ec;
        border-radius: 72px;
        display: flex;
        height: 24px;
        align-items: center;
        justify-content: center;
        width: 24px;
      }

      p {
        font-size: 10px;
        font-weight: 500;
        line-height: 20px;
        text-align: center;
        word-break: keep-all;
        color: rgba(47, 48, 53, 0.7);
      }
      .icon {
        opacity: 0.8;
      }
      &:nth-child(1) .icon {
        width: 15px;
        height: 12.16px;
      }
      &:nth-child(2) .icon {
        width: 15px;
        height: 12.16px;
      }
      &:nth-child(3) .icon {
        width: 12px;
        height: 12px;
      }
      &:nth-child(4) .icon {
        width: 12px;
        height: 10.9px;
      }
      &:nth-child(5) .icon {
        width: 12px;
        height: 12px;
      }
      &:nth-child(6) .icon {
        width: 11px;
        height: 12px;
      }
      &:nth-child(7) .icon {
        width: 13px;
        height: 10px;
      }

      &:hover {
        .header-right-item-overplay {
          background-color: #e7e7ec;
        }
        p {
          color: #2f3035;
        }
        .icon {
          opacity: 1;
        }
      }

      &:nth-child(1) {
        display: none;
      }
    }
    .header-right-item:first-child {
      margin-left: 4.5px;
      margin-right: 4.5px !important;
    }

    .header-right-avatar {
      width: 32px;
      height: 32px;
      margin-left: 16px;

      .el-avatar {
        width: 100%;
        height: 100%;
        border-radius: 50%;
      }
    }
  }
}

@media screen and (max-width: 1240px) {
  .header {
    .header-left {
      min-width: 400px;
      padding: 0 16px;
    }
    .header-right {
      .header-right-item:nth-child(1) {
        display: flex;
      }
      .header-right-item:nth-child(2),
      .header-right-item:nth-child(3),
      .header-right-item:nth-child(4) {
        display: none;
      }
    }
  }
}
</style>
