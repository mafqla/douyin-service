import axios, { type AxiosInstance } from 'axios'
import { ElMessage } from 'element-plus'
import type { ApiRequestConfig, ApiRequestInterceptors } from './type'

const DEAFULT_LOADING = true

class ApiRequest {
  instance: AxiosInstance
  interceptors?: ApiRequestInterceptors
  showLoading: boolean
  constructor(config: ApiRequestConfig) {
    // 创建axios实例
    this.instance = axios.create(config)

    // 保存基本信息
    this.showLoading = config.showLoading ?? DEAFULT_LOADING
    this.interceptors = config.interceptors

    // 使用拦截器
    // 1.从config中取出的拦截器是对应的实例的拦截器
    this.instance.interceptors.request.use(
      this.interceptors?.requestInterceptor,
      this.interceptors?.requestInterceptorCatch
    )

    this.instance.interceptors.response.use(
      this.interceptors?.responseInterceptor,
      this.interceptors?.responseInterceptorCatch,
    )

    //添加所有的拦截器
    this.instance.interceptors.request.use(
      config => {
        if (this.showLoading) {
          // this.loading = ElLoading.service({
          //   lock: true,
          //   text: '加载中...',
          //   background: 'rgba(0, 0, 0, 0.7)'
          // })
        }
        return config
      },
      error => {
        return error
      }
    )
    this.instance.interceptors.response.use(
      res => {
        // 将loading关闭
        // this.loading?.close()
        //@ts-ignore
        if (res.message === 'Network Error') {
          ElMessage({
            message: '网络错误',
            type: 'error'
          })
        }

        const data = res.data
        if (data.returnCode === '-1001') {
          console.log('请求失败')
        } else {
          return data
        }
      },
      error => {
        // 将loading组件关闭
        // this.loading?.close()

        if (error.response.status === 404) {
          console.log('页面不存在')
        }
        return error
      }
    )
  }

  request<T>(config: ApiRequestConfig<T>): Promise<T> {
    return new Promise((resolve, reject) => {
      // 1.单个请求对请求config的处理
      if (config.interceptors?.requestInterceptor) {
        config = config.interceptors.requestInterceptor(config)
      }
      // 2.判断是否需要显示loading
      if (config.showLoading === false) {
        this.showLoading = config.showLoading
      }
      this.instance
        .request<any, T>(config)
        .then(res => {
          // 1.单个请求对数据的处理
          if (config.interceptors?.responseInterceptor) {
            res = config.interceptors.responseInterceptor(res)
          }
          // 2.将showLoading设置true, 这样不会影响下一个请求
          this.showLoading = DEAFULT_LOADING

          // 3.将结果resolve返回出去
          resolve(res)
        })
        .catch(err => {
          this.showLoading = DEAFULT_LOADING
          reject(err)
          return err
        })
    })
  }

  get<T>(config: ApiRequestConfig<T>): Promise<T> {
    return this.request<T>({ ...config, method: 'GET' })
  }

  post<T>(config: ApiRequestConfig<T>): Promise<T> {
    return this.request<T>({ ...config, method: 'POST' })
  }

  delete<T>(config: ApiRequestConfig<T>): Promise<T> {
    return this.request<T>({ ...config, method: 'DELETE' })
  }

  patch<T>(config: ApiRequestConfig<T>): Promise<T> {
    return this.request<T>({ ...config, method: 'PATCH' })
  }
}

export default ApiRequest
