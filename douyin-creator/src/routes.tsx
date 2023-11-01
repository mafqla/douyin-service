import auth, { AuthParams } from '@/utils/authentication'
import { createBrowserRouter } from 'react-router-dom'
import PageLayout from './layout'

export type IRoute = AuthParams & {
  name: string
  key: string
  // 当前页是否展示面包屑
  breadcrumb?: boolean
  children?: IRoute[]
  // 当前路由是否渲染菜单项，为 true 的话不会在菜单中显示，但可通过路由地址访问。
  ignore?: boolean
}
export const routes = createBrowserRouter([
  {
    path: '/creator-micro',
    element: <PageLayout />,
    children: [],
  },
  {
    path: '/',
    async lazy() {
      const { Index } = await import('@/pages/index')
      return { Component: Index }
    },
  },
  {
    path: '*',
    element: <div>404</div>,
  },
])
