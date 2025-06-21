/// <reference types="vite/client" />

// 为虚拟模块添加类型声明
//（virtual:svg-icons-register 是 vite-plugin-svg-icons 插件在构建时动态生成的虚拟模块，不是真实存在的文件。）
declare module 'virtual:svg-icons-register' {
  const content: () => void
  export default content
}
