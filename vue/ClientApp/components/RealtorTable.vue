<template>
  <div>
    <div id="filters" class="d-flex justify-content-between">
      <datepicker input-class="form-control bg-light border-primary" language="ru" format="dd.MM.yyyy"
        monday-first placeholder="Фильтр по дате" v-model="dateFilter"></datepicker>
      <div class="d-flex ml-5">
        <label class="pt-1">Количество записей на странице:</label>
        <input class="form-control ml-2 perPage" type="text" v-model="perPage">
      </div>
      <input class="form-control ml-auto w-25 border-success" type="text" v-model="lastNameInput" placeholder="Фильтрация по фамилии">
    </div>
    <table class="table table table-bordered table-striped table-hover">
      <thead class="thead-light">
        <tr>
          <th scope="col">Id</th>
          <th scope="col">Guid</th>
          <th scope="col">Фамилия</th>
          <th scope="col">Имя</th>
          <th scope="col">Подразделение</th>
          <th scope="col">Дата регистрации</th>
        </tr>
      </thead>
      <tbody>
       <tr class="record" v-for="item in showRecords" :key="item.id" v-if="lastNameFilter(item.lastName)" @dblclick="editRecord(item.id)">
         <td class="recordId">{{ item.id }}</td>
         <td class="recordGuid">{{ item.guid }}</td>
         <td class="recordLastName">{{ item.lastName }}</td>
         <td class="recordFirstName">{{ item.firstName }}</td>
         <td class="recordDivision">{{ item.division }}</td>
         <td class="recordDate">{{ item.registerDate }}</td>
       </tr>
      </tbody>
    </table>
    <nav v-if="totalPages > 1">
      <ul class="pagination justify-content-center">
        <li class="page-item" @click="prevPage">
          <a class="page-link">Предыдущая</a>
        </li>
        <li class="page-item" v-for="item in totalPages" @click="currentPage=item"><a class="page-link">{{ item }}</a></li>
        <li class="page-item" @click="nextPage"><a class="page-link">Следующая</a></li>
      </ul>
    </nav>
  </div>
</template>

<script>
export default {
  name: 'realtorTable',
  data: () => ({
    lastNameInput: '',
    dateFilter: '',
    currentPage: 1,
    perPage: 10
  }),
  mounted() {
    this.$store.dispatch('getAllRealtors')
  },
  computed: {
    allRealtors () {
      return this.$store.state.allRealtors
    },
    totalPages() {
      return Math.ceil(this.allRealtors.length / this.perPage)
    },
    showRecords() {
      var res = []
      var limit
      if(this.perPage * this.currentPage > this.allRealtors.length){
        limit = this.allRealtors.length
      }else{
        limit = this.perPage * this.currentPage
      }

      for(var i = (this.currentPage - 1) * this.perPage; i < limit; i++){
        res.push(this.allRealtors[i])
      }
      return res
    }
  },
  methods: {
    lastNameFilter(item) {
      return item.toLowerCase().includes(this.lastNameInput.toLowerCase())
    },
    editRecord(realtorId) {
      this.$router.push('/edit/' + realtorId)
    },
    nextPage() {
      if(this.perPage * this.currentPage < this.allRealtors.length){
        this.currentPage += 1
      }
    },
    prevPage() {
      if(this.currentPage > 1){
        this.currentPage -= 1
      }
    },
  }
}
</script>

<style scoped>
#filters {
  margin-bottom: 20px;
}

.perPage {
  width: 70px;
  text-align: center;
  vertical-align: middle;
}

.record:hover {
  cursor: pointer;
}

.recordId {
  width: 50px;
  text-align: center;
}

.recordGuid {
  width: 340px;
}

.recordDivision {
  width: 150px;
  text-align: center;
}

.recordDate {
  width: 180px;
  text-align: center;
}

</style>
