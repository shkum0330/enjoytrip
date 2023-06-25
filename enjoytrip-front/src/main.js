import Vue from "vue";
import Vuex from "vuex";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "bootstrap";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import "@/assets/css/styles.css";
import VueCookies from "vue-cookies";

Vue.use(Vuex);
Vue.config.productionTip = false;
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);
Vue.use(VueCookies);

Vue.prototype.$serviceKey = "ec6309dfd731b4a45497463ae60ffe92";

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
