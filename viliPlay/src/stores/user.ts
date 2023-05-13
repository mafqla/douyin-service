import { AuthLogin } from '@/service/auth/auth'
import { ElMessage } from 'element-plus'
import { defineStore } from 'pinia'

export const userStore = defineStore('user', {
  state: () => ({
    userInfo: {
      userId: 0,
      username: '',
      token: '',
      type: '',
      userAvatar: '',
      email: '',
      phone: '',
      userNum: '',
      ipLocation: '',
      gender: '',
      birthdate: '',
      signature: '',
      createTime: ''
    },
    message: ''
  }),

  actions: {
    // 设置用户信息, 调用登录接口
    async login(userInfo: any) {
      const data = await AuthLogin(userInfo)

      this.message = data.msg

      ElMessage({
        message: data.msg
      })

      const {
        userId,
        username,
        token,
        type,
        userAvatar,
        email,
        phone,
        userNum,
        ipLocation,
        gender,
        birthdate,
        signature,
        createTime
      } = data.data

      // 存储到 state
      this.userInfo = {
        userId,
        username,
        token,
        type,
        userAvatar,
        email,
        phone,
        userNum,
        ipLocation,
        gender,
        birthdate,
        signature,
        createTime
      }
    },

    //判断是否登录
    isLogin() {
      return this.userInfo.token !== ''
    },
    logout() {
      // 清空用户信息为空对象
      this.userInfo = {} as any
      // 清空本地存储
      localStorage.clear()
      //重新加载页面
      window.location.reload()
    }
  },
  persist: {
    enabled: true
  }
})
