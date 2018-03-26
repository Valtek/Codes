<template>
  <div id="app">
    <nav-menu></nav-menu>
    <div id="content">
      <transition name="fade" mode="out-in">
        <router-view></router-view>
      </transition>
    </div>
  </div>
</template>

<script>
import NavMenu from './NavMenu.vue'

export default {
  name: 'app',
  data () {
    return {

    }
  },
  components: {
    NavMenu
  }
}
</script>

<style>
#content {
  width: 58%;
  margin: auto;
  margin-top: 20px;
}

.fade-enter-active, .component-fade-leave-active {
  transition: opacity .2s ease;
}
.fade-enter, .component-fade-leave-to {
  opacity: 0;
}

</style>
