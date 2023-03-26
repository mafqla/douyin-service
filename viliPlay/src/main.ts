import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

// import './assets/main.css'
import '@/assets/styles/main.scss'
// 使用svg
import svgIcon from '@/components/svg-icon.vue'
import 'virtual:svg-icons-register'
//pinia持久化
import piniaPersist from 'pinia-plugin-persist'

const app = createApp(App)

const pinia = createPinia()
pinia.use(piniaPersist)
app.use(pinia)
app.use(router)

// 全局注册svg-icon
app.component('svg-icon', svgIcon)
app.mount('#app')
