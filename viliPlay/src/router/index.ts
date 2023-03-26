import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import layout from '@/layout/index.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: layout,
    redirect: '/',
    children: [
      {
        path: '/discover',
        name: 'discover',
        component: () => import('@/views/discover.vue')
      },
      {
        path: '/',
        name: 'recommend',
        component: () => import('@/views/recommend.vue')
      },
      {
        path: '/follow',
        name: 'follow',
        component: () => import('@/views/follow.vue')
      },

      {
        path: '/user',
        name: 'user',
        redirect: '/user/self',
        children: [
          {
            path: 'self',
            component: () => import('@/views/my.vue')
          }
        ]
      }
    ]
  }

  // {
  //   path: '/:pathMatch(.*)*',
  //   name: 'not-found'
  //   component: () => import('@/views/not-found/not-found.vue')
  // }
]
const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to) => {})

export default router
