import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import '@/assets/css/global.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import VueParticles from 'vue-particles'

createApp(App).use(store).use(router).use(VueParticles).use(ElementPlus,{locale: zhCn,}).mount('#app')

// // 统一导入el-icon图标
// import * as ElIconModules from '@element-plus/icons-vue'
//
// // 统一注册el-icon图标
// for(let iconName in ElIconModules){
//     app.component(iconName,ElIconModules[iconName])
// }

router.beforeEach((to, from, next) => {
        if (to.meta.requireAuth) {  //判断该路由是否需要登陆访问
            //判断是否已登录
            if (sessionStorage.getItem("isLogin")==="true"){
                //已登陆，放行
                next()
            }else {
                alert("您未登陆，请先登陆！！")
                //未登录则跳转到登陆页
                next({path: '/'})
            }
        }else {
            //不需要登陆也能访问的页面，放行
            next()
        }
    }
)
