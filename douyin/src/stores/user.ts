import router from '@/router'
import { AuthLogin, PostAuthLogin, PostAuthSendCode } from '@/service/auth/auth'
import { ElMessage } from 'element-plus'
import { defineStore } from 'pinia'

export const userStore = defineStore('user', {
  state: () => ({
      token: localStorage.getItem('token') || '',
    userInfo: {
      userId: 0,
      username: '',
      type: '',
      userAvatar: '',
      email: '',
      phone: '',
      userNum: '',
      ipLocation: '',
      gender: '',
      birthdate: '',
        school: '',
        location: '',
      signature: '',
      createTime: ''
    }
  }),

  actions: {
    // 设置用户信息, 调用登录接口
    async login(userInfo: any) {
        try {
            const data = await AuthLogin(userInfo)
            const {token, ...userData} = data.data

            // 存储到 state
            this.userInfo = Object.assign(this.userInfo, userData)

            // 存储到 localStorage
            localStorage.setItem('token', token)
            this.token = token
            ElMessage({
                message: data.msg,
                type: 'success'
            })
            window.location.reload()
        } catch (e: any) {
            ElMessage({
                message: e || '登录失败',
                type: 'error'
            })
      }
    },

    //判断是否登录
    isLogin() {
        return this.token !== ''
    },
    logout() {
      // 清空用户信息为空对象
      this.userInfo = {} as any
        this.token = ''
      // 清空本地存储
      localStorage.clear()
      window.location.reload()
    },

    async postCode(email: string) {
      const data = await PostAuthSendCode(email)
      console.log(data)
    },
    async codeLogin(email: string, code: string) {
      const data = await PostAuthLogin(email, code)
      console.log(data)
    }
  },
  persist: {
      enabled: true,
      strategies: [
          {
              key: 'pinia-state',
              paths: ['userInfo'],
              storage: localStorage
          }
      ]
  }
})
