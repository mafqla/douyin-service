export interface ILogin {
  email: string
  password: string
}

export interface ILoginResult {
  userId: number
  username: string
  token: string
  type: string
  userAvatar: string
  email: string
  phone: string
  userNum: string
  ipLocation: string
  gender: string
  birthdate: string
  signature: string
    school: string
    location: string
  createTime: string
}
