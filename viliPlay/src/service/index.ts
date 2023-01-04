import ApiRequest from './request'
import localCache from '@/utils/cache'

const apiRequest = new ApiRequest({
  baseURL: import.meta.env.VITE_BASE_URL,
  timeout: import.meta.env.VITE_TIMEOUT,
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded'
  },
  interceptors: {
    requestInterceptor: config => {
      //携带token拦截
      //解决Object is possibly 'undefined'.
      if (config.headers === undefined) {
        config.headers = {}
      }
      // 从localStorage中获取token
      const token = localCache.getCache('token')
      if (token) {
        config.headers.Authorization = `Bearer ${token}`
      }
      return config
    },
    requestInterceptorCatch: error => {
      return error
    },
    responseInterceptor: res => {
      return res
    },
    responseInterceptorCatch: error => {
      return error
    }
  }
})

export default apiRequest
