import apiRequest from '../index'
import type { IDataType } from '../types'

enum AuthApi {
  //分类列表
  AuthLogin = '/categories/getCategoriesList'
}

// 获取分类列表
export const getCategoriesList = () => {
  return apiRequest.get<IDataType>({
    url: AuthApi.AuthLogin
  })
}
