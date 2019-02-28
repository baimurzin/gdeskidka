import { getShops, createShop } from '@/api/shop'
import { removeDuplicates } from '../../utils/array'

const SET_LOADING = 'SET_LOADING'
const SHOP_CREATED = 'SHOP_CREATED'
const UPDATE_SHOPS = 'UPDATE_SHOPS'

const shop = {
  state: {
    shops: [],
    loading: false
  },
  mutations: {
    [SHOP_CREATED]: (state, shop) => {
      state.shops.push(shop)
    },
    [SET_LOADING]: (state, loading) => {
      state.loading = loading
    },
    [UPDATE_SHOPS]: (state, shops) => {
      state.shops = removeDuplicates(state.shops.concat(shops), 'id')
    }
  },
  actions: {
    GetShops({ commit, state }) {
      commit(SET_LOADING, true)
      return new Promise((resolve, reject) => {
        getShops().then(response => {
          console.log(response)
          commit(SET_LOADING, false)
          commit(UPDATE_SHOPS, response)
        })
      })
    },
    CreateShop({ commit, state }, shopInfo) {
      return new Promise((resolve, reject) => {
        createShop(shopInfo).then(response => {
          commit(SHOP_CREATED, response)
        })
      })
    }
  }
}

export default shop
