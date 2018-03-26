<template>
  <transition name="modal">
    <div class="modal-mask" @click="close">
      <div class="modal-dialog" @click.stop>
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Удаление риэлтора</h5>
            <button type="button" class="close" @click="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <p>Вы действительно хотите удалить риэлтора?</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" @click="deleteRealtor(); close(); backToList()">Удалить</button>
            <button type="button" class="btn btn-secondary" data-dismiss="modal" @click="close">Отмена</button>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
  export default {
    name: 'modal',
    data: () => ({

    }),
    methods: {
      close() {
        this.$emit('close');
      },
      deleteRealtor() {
        this.$store.dispatch('deleteRealtor', this.$route.params.realtorId)
      }
    }
  }
</script>

<style scoped>
.modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .5);
    transition: opacity .3s ease;
}

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>
