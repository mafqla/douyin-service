
import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import localCache from '@/utils/cache'

const routes: RouteRecordRaw[] = [
]
const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to) => {})

export default router
