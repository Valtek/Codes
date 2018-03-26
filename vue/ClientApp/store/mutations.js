import router from '../router'

export const state = {
  allRealtors: [],
  choosenRealtor: {}
}

export const mutations = {
  loadRealtors (state, data){
    state.allRealtors = data || []
  },

  loadRealtor (state, data){
    state.choosenRealtor = data || {}
  }
}
