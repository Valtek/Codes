import Vue from 'vue'
import Router from 'vue-router'
import store from './store'
import RealtorTable from './components/RealtorTable.vue'
import Create from './components/Create.vue'
import Edit from './components/Edit.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: __dirname,
  routes: [
    { path: '/', component: RealtorTable },
    { path: '/create', component: Create },
    { path: '/edit/:realtorId', component: Edit }
  ]
})
