import Vue from 'vue'
import App from './components/App.vue'
import router from './router'
import store from './store'
import { sync } from 'vuex-router-sync'
import Datepicker from 'vuejs-datepicker'
import { ModelSelect } from 'vue-search-select'

sync(store, router)

Vue.component('datepicker', Datepicker)
Vue.component('model-select', ModelSelect)

Vue.mixin({
  methods: {
    backToList() {
      this.$router.push('/')
    }
  }
})

new Vue({
  el: '#app',
  store,
  router,
  render: h => h(App)
})
