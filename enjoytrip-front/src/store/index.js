import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    token: null,
    userInfo: {
      userId : "",
      userName: "",
      role: "",
    },
    attractionList: [],
  },
  getters: {
    isLogin: (state) => {
      return state.token !== null;
    },
    isAdmin: (state) => {
      return state.userInfo.role == "ADMIN";
    },
    getUserId: (state) => {
      return state.userInfo.userId;
    },
    getUserName: (state) => {
      return state.userInfo.userName;
    },
    getUserRole: (state) => {
      return state.userInfo.role;
    },
  },
  mutations: {
    setToken(state, _token) {
      state.token = _token;
    },

    setUserInfo(state, response) {
      state.userInfo.userId = response.data.userId;
      state.userInfo.userName = response.data.userName;
      state.userInfo.role = response.data.role;
    },
    clearUserInfo(state) {
      state.userInfo.userId = "";
      state.userInfo.userName = "";
      state.userInfo.role = "";
    },

    setAttractionList(state, response) {
      state.attractionList = response.data;
    },
  },
  actions: {
    setToken({ commit }, _token) {
      commit("setToken", _token);
    },
    setUserInfo({ commit }, response) {
      commit("setUserInfo", response);
    },
    logout({ commit }) {
      commit("clearUserInfo");
      commit("setToken", null);
    },
    readAttractionList({ commit }, response) {
      commit("setAttractionList", response);
    },
  },
  plugins: [
    createPersistedState({
      storage: window.sessionStorage,
    }),
  ],
});
