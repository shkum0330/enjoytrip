<template>
  <div class="login">
    <transition name="fade-memberView">
      <header-nav />
    </transition>

    <div class="header-image-container">
      <header-image />
      <transition name="fade-memberView">
        <router-view />
      </transition>
    </div>
    <div class="content-container"></div>
    <my-footer></my-footer>
  </div>
</template>

<script>
import HeaderNav from "@/components/common/HeaderNav.vue";
import HeaderImage from "@/components/common/HeaderImage.vue";
import MyFooter from "@/components/common/MyFooter.vue";
export default {
  name: "MemberView",

  components: {
    HeaderNav,
    HeaderImage,
    MyFooter,
  },

  data() {
    return {};
  },
};
</script>
