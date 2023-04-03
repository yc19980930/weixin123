import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

// ---------------------------------------------- 
  
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: '校园设备报修管理系统',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '校园设备报修管理系统', icon: 'dashboard' }
    }]
  },
  {
    path: '/equipment',
    component: Layout,
    name: '设备管理',
    alwaysShow: true,
    meta: { title: '设备管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'equipment',
        name: '设备信息',
        component: () => import('@/views/equipment/equipment'),
        meta: { title: '设备信息', icon: 'table' }
      }
    ]
  },

  {
    path: '/repair',
    component: Layout,
    name: '报修管理',
    alwaysShow: true,
    meta: { title: '报修管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'repair',
        name: '报修记录',
        component: () => import('@/views/repair/record'),
        meta: { title: '报修记录', icon: 'table' }
      }]
  },

  {
    path: '/system',
    component: Layout,
    redirect: '/system/student',
    name: '系统管理',
    meta: { title: '系统管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'student',
        name: '学生管理',
        component: () => import('@/views/system/student'),
        meta: { title: '学生管理', icon: 'table' }
      },
      {
        path: 'admin',
        name: '管理员管理',
        component: () => import('@/views/system/admin'),
        meta: { title: '管理员管理', icon: 'tree' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
