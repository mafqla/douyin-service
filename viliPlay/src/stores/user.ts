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
    }
  })
  
})
