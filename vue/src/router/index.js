import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Layout from '../layout/Layout.vue'

const routes = [
  //  写在layout中的都是在框架内显示的页面，如果想整个页面显示，如login，不能是layout的children
//   meta: { requireAuth: true }代表此页面需要验证是否登录
  {
    path: '/',
    name: 'layout',
    component: Layout,
    redirect: '/login',
    children: [
      {
        path: '/home',
        name: 'home',
        component: () => import('../views/Home'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: '/welcome',
        name: 'Welcome',
        component: () => import('../views/Welcome'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/emp",
        name: "emp",
        component: () => import('../views/personnel/EmployeeInfo'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/dept",
        name: "dept",
        component: () => import('../views/personnel/Department'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/salary",
        name: "salary",
        component: () => import('../views/salary/SalaryInfo'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/talent",
        name: "talent",
        component: () => import('../views/recruitment/TalentInfo'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/recruitment",
            name: "recruitment",
          component: () => import('../views/recruitment/RecruitmentInfo'),
          meta: {
        requireAuth: true
        }
      },
      {
        path: "/apply",
        name: "apply",
        component: () => import('../views/recruitment/ApplyInfo'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/trainingPlan",
        name: "trainingPlan",
        component: () => import('../views/training/TrainingPlan'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/trainingPerform",
        name: "trainingPerform",
        component: () => import('../views/training/TrainingPerform'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/trainingFiles",
        name: "trainingFiles",
        component: () => import('../views/training/TrainingFiles'),
        meta: {
          requireAuth: true
        }
      },

    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login')
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
