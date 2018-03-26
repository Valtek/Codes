import axios from 'axios'

export const actions = {

  async getAllRealtors({ commit }) {
    let response = await axios.get('/api/realtor')
    if (response && response.data) {
      let data = response.data
      commit('loadRealtors', data)
    }
  },

  async getRealtor({ commit }, id) {
    let response = await axios.get('/api/realtor/' + id)
    if (response && response.data) {
      let data = response.data
      commit('loadRealtor', data)
    }
  },

  async createRealtor({ dispatch }, data) {
    await axios.post('/api/realtor', data)
    await dispatch('getAllRealtors')
  },

  async updateRealtor({ dispatch }, data) {
    await axios.put('/api/realtor/' + data.id, data)
    await dispatch('getAllRealtors')
  },

  async deleteRealtor({ dispatch }, id) {
    await axios.delete('/api/realtor/' + id)
    await dispatch('getAllRealtors')
  }
}
