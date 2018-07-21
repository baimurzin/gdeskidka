import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import user from './modules/user'
import switcher from './modules/switcher'
import shop from './modules/shop'
import getters from './getters'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    user,
    switcher,
    shop
  },
  getters
})

export default store
