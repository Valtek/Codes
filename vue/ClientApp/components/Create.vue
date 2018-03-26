<template>
  <div class="w-50 mx-auto">
    <h4>Добавить нового риэлтора</h4>
    <form class="pt-3" @submit.prevent="createRealtor(); backToList()">
      <div class="form-group">
        <label for="lastName">Фамилия</label>
        <input type="text" class="form-control" id="lastName" v-model="newRealtorData.lastName" placeholder="Введите фамилию риэлтора" required>
      </div>
      <div class="form-group pt-1">
        <label for="firstName">Имя</label>
        <input type="text" class="form-control" id="firstName" v-model="newRealtorData.firstName" placeholder="Введите имя риэлтора" required>
      </div>
      <div class="form-group pt-1">
        <label for="division">Подразделение</label>
        <model-select :options="divisions" v-model="newRealtorData.division" placeholder="Введите подразделение риэлтора"></model-select>
      </div>
      <div class="form-group pt-1">
        <label for="registerDate">Дата регистрации</label>
        <datepicker input-class="form-control bg-light" language="ru" format="dd.MM.yyyy"
          monday-first placeholder="Укажите дату регистрации" v-model="newRealtorData.registerDate"></datepicker>
      </div>
      <div class="d-flex pt-3">
        <button type="submit" class="btn btn-success">Добавить</button>
        <button type="button" class="btn ml-auto" @click="backToList()">Отмена</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: 'create',
  data: () => ({
    newRealtorData: {
      lastName: '',
      firstName: '',
      division: '',
      registerDate: new Date()
    },
    divisions: [
      {value: '1', text:'Альфа'},
      {value: '2', text:'Браво'},
      {value: '3', text:'Чарли'},
      {value: '4', text:'Дельта'},
      {value: '5', text:'Эхо'}
    ]
  }),
  methods: {
    createRealtor() {
      this.$store.dispatch('createRealtor', this.newRealtorData)
    }
  }
}
</script>

<style scoped>
</style>
