<template>
  <div class="w-50 mx-auto">
    <div class="d-flex align-items-center">
      <h4>Редактировать данные риэлтора</h4>
      <button type="button" class="btn btn-danger ml-auto" @click="showModal">Удалить риэлтора</button>
    </div>
    <form class="pt-3" @submit.prevent="updateRealtor(); backToList()">
      <div class="form-group">
        <label for="lastName">Фамилия</label>
        <input type="text" class="form-control" id="lastName" v-model="realtorData.lastName" placeholder="Введите фамилию риэлтора" required>
      </div>
      <div class="form-group pt-1">
        <label for="firstName">Имя</label>
        <input type="text" class="form-control" id="firstName" v-model="realtorData.firstName" placeholder="Введите имя риэлтора" required>
      </div>
      <div class="form-group pt-1">
        <label for="division">Подразделение</label>
          <model-select :options="divisions" :selected-option="realtorData.division" v-model="realtorData.division" placeholder="Введите подразделение риэлтора"></model-select>
      </div>
      <div class="form-group pt-1">
        <label for="registerDate">Дата регистрации</label>
        <datepicker input-class="form-control bg-light" language="ru" format="dd.MM.yyyy"
          monday-first placeholder="Укажите дату регистрации" v-model="realtorData.registerDate"></datepicker>
      </div>
      <div class="d-flex pt-3">
        <button type="submit" class="btn btn-success">Сохранить</button>
        <button type="button" class="btn ml-auto" @click="backToList()">Отмена</button>
      </div>
    </form>
    <modal v-if="isModalVisible" @close="closeModal"></modal>
  </div>
</template>

<script>
import Modal from './Modal.vue'

export default {
  name: 'edit',
  data: () => ({
    divisions: [
      {value: '1', text:'Альфа'},
      {value: '2', text:'Браво'},
      {value: '3', text:'Чарли'},
      {value: '4', text:'Дельта'},
      {value: '5', text:'Эхо'}
    ],
    isModalVisible: false
  }),
  mounted() {
    this.$store.dispatch('getRealtor', this.$route.params.realtorId)
  },
  components: {
    Modal
  },
  computed: {
    realtorData () {
      return this.$store.state.choosenRealtor
    }
  },
  methods: {
    updateRealtor() {
      this.$store.dispatch('updateRealtor', this.realtorData)
    },
    showModal() {
      this.isModalVisible = true;
    },
    closeModal() {
      this.isModalVisible = false;
    }
  }
}
</script>

<style scoped>
</style>
