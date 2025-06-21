// 对外暴露的常量路由
export const constantRoute = [
  {
    path: '/login',
    component: () => import('@/views/login/login.vue'),
    name: 'login',
  },
  {
    path: '/register',
    component: () => import('@/views/login/register.vue'),
    name: 'register',
  },
  {
    path: '/home',
    component: () => import('@/views/home/index.vue'),
    name: 'layout',
  },
  {
    path: '/404',
    component: () => import('@/views/404/index.vue'),
    name: '404',
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/404',
    name: 'any',
  },
]
