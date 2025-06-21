import request from '@/utils/request'

// 注册
export const reqRegister = (data: any) => request.post('/user/register', data)

// 登录
export const reqLogin = (data: any) => request.post('/user/login', data)

// 修改密码
export const reqUpdatePassword = (data: any) =>
  request.post('/user/updatePassword', data)

// 获取用户信息
export const reqUserInfo = (data: any) => request.post('/user/get', data)

// 退出登录
// export const reqLogout = () => request.post<any, any>()

// 学生认证
export const reqStudentAuth = (data: any) =>
  request.post('/user/studentAuth', data)
