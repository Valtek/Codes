<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
      <router-link to="/">
        <a class="navbar-brand" href="/">Риэлторы</a>
      </router-link>
      <button class="navbar-toggler" data-toggle="collapse" type="button" @click="toggleCollapsed">
        <span class="navbar-toggler-icon"></span>
      </button>
      <transition name="slide">
        <div :class="'collapse navbar-collapse' + (!collapsed ? ' show':'')" v-show="!collapsed">
          <ul class="navbar-nav mr-auto">
            <router-link to="/" class="routerLink">
              <li class="nav-item">
                <a class="nav-link" href="">Список риэлторов</a>
              </li>
            </router-link>
            <router-link to="/create" class="routerLink">
              <li class="nav-item">
                <a class="nav-link" href="">Новый риэлтор</a>
              </li>
            </router-link>
          </ul>
          <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="search" placeholder="Поиск риэлтора" aria-label="Search">
            <button class="btn btn-success my-2 my-sm-0" type="submit">Поиск</button>
          </form>
        </div>
      </transition>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'navMenu',
  data: () => ({
    collapsed: true
  }),
  methods: {
      toggleCollapsed(event){
          this.collapsed = !this.collapsed;
      }
  }
}
</script>

<style scoped>

.routerLink {
  text-decoration: none;
}

.slide-enter-active, .slide-leave-active {
  transition: max-height .35s
}
.slide-enter, .slide-leave-to {
  max-height: 0px;
}
.slide-enter-to, .slide-leave {
  max-height: 20em;
}
</style>
