import request from '@/utils/request'
import type { loginForm } from './type'

enum API {
  LOGIN_URL = '/user/login',
  USERFINFO_url = '/user/get',
}

export const reqLogin = (data: loginForm) => request.post(API.LOGIN_URL, data)

export const reqUserInfo = () => request.get(API.USERFINFO_url)
