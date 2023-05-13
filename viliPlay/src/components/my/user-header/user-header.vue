<script setup lang="ts">
import { ref, watchEffect } from 'vue'
import switchButton from './switch-button.vue'
import { userStore } from '@/stores/user'

const isLogin = ref(true)

const avatar = ref('../assets/icons/user-avatar.svg')

const store = userStore()

watchEffect(() => {
    // console.log(store.isLogin())
    isLogin.value = store.isLogin()
    avatar.value = store.userInfo.userAvatar
})
</script>

<template>
    <div class="user-detail-header">
        <div class="user-avatar">
            <el-avatar v-if="isLogin" :size="112" :src="avatar">
                <svg-icon class="icon" icon="user-avatar" />
            </el-avatar>
        </div>
        <div class="user-info">
            <div class="user-name">
                <h1 class="user-name-noin" v-if="!isLogin">未登录</h1>
                <h1 class="user-name-login" v-if="isLogin">
                    <span>{{store.userInfo.username  }}</span>
                </h1>
            </div>
            <p v-if="isLogin" class="user-account">
        <span class="user-account-num">
          账号：{{ store.userInfo.userNum }}
        </span>
                <!-- 作者页面显示 -->
                <span class="user-account-addr"
                >ip属地: {{ store.userInfo.ipLocation }}</span
                >
                <span v-if="store.userInfo.gender !== ''" class="user-account-info">
          <svg-icon
                  v-if="store.userInfo.gender === '女'"
                  icon="small-woman"
                  class="user-account-icon"
          />
          <svg-icon
                  v-if="store.userInfo.gender === '男'"
                  icon="small-man"
                  class="user-account-icon"
          />
          <span>{{ store.userInfo.gender }}</span>
        </span>
                <span class="user-account-info">天津·南开</span>
                <span class="user-account-info">南开大学</span>
            </p>
            <p v-if="isLogin" class="user-signature">
                <span> 这个人很懒，什么都没有留下</span>
            </p>

            <div class="user-info-detail">
                <div class="user-item">
                    <template v-if="!isLogin">
                        <p class="user-text">关注</p>
                        <p class="user-line">-</p>
                    </template>
                    <template v-else>
                        <div class="user-info-text">关注</div>
                        <div class="user-number">0</div>
                    </template>
                </div>
                <div class="user-item">
                    <template v-if="!isLogin">
                        <p class="user-text">粉丝</p>
                        <p class="user-line">-</p>
                    </template>
                    <template v-else>
                        <div class="user-info-text">粉丝</div>
                        <div class="user-number">0</div>
                    </template>
                </div>
                <div class="user-item">
                    <template v-if="!isLogin">
                        <p class="user-text">获赞</p>
                        <p class="user-line">-</p>
                    </template>
                    <template v-else>
                        <div class="user-info-text">获赞</div>
                        <div class="user-number">0</div>
                    </template>
                </div>
            </div>
        </div>

        <div class="trust-login-switch">
            <div class="trust-login-tips-left">
                <div class="trust-login-tips-icon">
                    <svg
                            width="15"
                            height="16"
                            fill="none"
                            xmlns="http://www.w3.org/2000/svg"
                            class=""
                            viewBox="0 0 15 16"
                    >
                        <path
                                fill-rule="evenodd"
                                clip-rule="evenodd"
                                d="M7.5 15.473a7.5 7.5 0 100-15 7.5 7.5 0 000 15zm.034-12c-.956 0-1.842.367-2.45 1.076-.076.089-.162.22-.243.36-.209.357-.056.805.308.999l.027.014c.396.211.842-.003 1.114-.36.3-.393.715-.588 1.244-.588.787 0 1.338.327 1.338.992 0 .536-.427.94-.91 1.371l-.03.028c-.517.469-1.05.953-1.277 1.857-.01.04-.02.088-.028.14-.077.464.31.86.779.86.423 0 .753-.33.858-.74a.984.984 0 01.027-.092c.185-.512.535-.848.885-1.152.676-.6 1.324-1.227 1.324-2.272 0-1.619-1.366-2.493-2.966-2.493zm-.034 7.5a1.125 1.125 0 100 2.25 1.125 1.125 0 000-2.25z"
                                fill="#2F3035"
                                fill-opacity="0.4"
                        ></path>
                    </svg>
                    <!-- <div class="trust-login-tips-info trust-login-tips-info-bottom">
                        保存登录信息，下次登录免验证
                      </div> -->
                </div>
            </div>
            <switch-button />
        </div>
    </div>
</template>

<style lang="scss" scoped>
.user-detail-header {
  display: flex;
  margin-bottom: 24px;
  margin-top: 32px;
  position: relative;
  width: 100%;

  .user-avatar {
    .icon {
      width: 112px;
      height: 112px;
    }
  }

  .user-info {
    flex: 1 1;
    align-items: flex-start;
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin-left: 40px;
    min-width: 560px;
    .user-name {
      .user-name-noin {
        color: #161823;
        font-size: 20px;
        font-weight: 400;
        line-height: 28px;
        margin-bottom: 14px;
      }
      .user-name-login {
        span {
          color: #161823;
          display: block;
          flex: none;
          font-family: PingFang SC, DFPKingGothicGB-Medium, sans-serif;
          font-size: 20px;
          font-weight: 500;
          line-height: 28px;
          max-width: 300px;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
      }
    }

    .user-account {
      align-items: center;
      display: flex;
      height: 22px;
      margin-top: 6px;

      span {
        color: rgba(22, 24, 35, 0.6);
        // color: rgba(255, 255, 255, 0.5);
        font-size: 12px;
        line-height: 20px;
      }
      .user-account-num,
      .user-account-addr {
        margin-right: 20px;
      }

      .user-account-info {
        align-items: center;
        border: 1px solid #f2f2f4;
        // border: 1px solid rgba(242, 242, 243, 0.08);
        border-radius: 2px;
        display: flex;
        height: 20px;
        margin-right: 2px;
        padding: 0 8px;

        .user-account-icon {
          width: 12px;
          height: 12px;
          margin-right: 4px;
          color: #f5588e;
        }
      }
    }
    .user-info-text {
      color: rgba(22, 24, 35, 0.6);
      font-size: 14px;
      line-height: 22px;
      margin-right: 6px;
    }
    .user-number {
      align-items: center;
      display: flex;
      height: 22px;
      margin-top: 6px;
      color: rgba(22, 24, 35, 1);
      font-size: 16px;
      line-height: 24px;
    }
    .user-signature {
      display: flex;
      height: 23px;
      margin-top: 4px;
      position: relative;

      span {
        color: rgba(22, 24, 35, 0.6);
        font-size: 12px;
        line-height: 20px;
      }
    }

    .user-info-detail {
      display: flex;
      justify-content: flex-start;

      .user-item {
        align-items: baseline;
        display: flex;
        justify-content: flex-start;

        .user-text {
          color: rgba(22, 24, 35, 0.6);
          font-family: PingFang SC, DFPKingGothicGB-Regular, sans-serif;
          font-weight: 400;
        }

        .user-line {
          color: #161823;
          font-size: 20px;
          font-weight: 400;
          line-height: 28px;
          margin-left: 6px;
          transform: scaleX(0.5);
        }
        .user-line-number {
          color: #161823;
          font-size: 16px;
          line-height: 24px;
          margin-left: 6px;
        }

        &::after {
          border: 1px solid #f2f2f4;
          border-color: rgba(242, 242, 243, 1);
          content: '';
          height: 16px;
          margin: 0 16px;
          width: 0;
        }

        &:last-child {
          &::after {
            border: none;
            content: none;
          }
        }
      }
    }
  }

  .trust-login-switch {
    align-items: center;
    display: inline-flex;
    position: relative !important;
    height: 100%;

    .trust-login-tips-left {
      display: inline-block;
      left: -12px;
      top: 2px;
      .trust-login-tips-icon {
        cursor: pointer;
        height: 16px;
        width: 16px;
      }
    }
  }
}

@media (max-width: 1024px) {
  .user-detail-heade {
    padding-bottom: 68px;
  }
}
@media (max-width: 940px) {
  .user-detail-heade {
    padding-bottom: 68px;
  }
}
</style>
