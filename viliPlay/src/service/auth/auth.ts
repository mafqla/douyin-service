import apiRequest from '../index'
import type { ILogin, ILoginResult } from './AuthType'
import type { IDataType } from '../types'
enum AuthApi {
  //登录
  AuthLogin = '/auth/login',
  //退出登录
  AuthLogout = '/auth/logout',
  //注册
  AuthRegister = '/auth/register',
  //发送验证码
  AuthSendCode = '/auth/sendCode',
  //重置密码
  AuthResetPassword = '/auth/resetPassword',
  //修改头像
  AuthUpdateAvatar = '/auth/updateAvatar'
}

/**
 * 登录
 * @param ILogin
 */
export function AuthLogin(login: ILogin) {
  return apiRequest.post<IDataType<ILoginResult>>({
    url: AuthApi.AuthLogin,
    data: login
  })
}

export function AuthLogut() {}

export function AuthRegister() {}

export function AuthSendCode() { }

export function AuthResetPassword() { }

export function AuthUpdateAvatar() {}
