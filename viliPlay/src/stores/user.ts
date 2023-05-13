import {AuthLogin} from '@/service/auth/auth'
import {defineStore} from 'pinia'


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
    }),

    actions: {
        // 设置用户信息, 调用登录接口
        async setUserInfo(userInfo: any) {
            const data = await AuthLogin(userInfo)
            //存储到state
        }
    }

})
