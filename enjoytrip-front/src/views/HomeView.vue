<template>
  <div class="home">
    <nav>
      <header-nav></header-nav>
    </nav>

    <!-- header-->
    <div class="header-image-container">
      <header-image></header-image>
      <div class="container mt-5">
        <div class="row justify-content-center content-overlay">
          <h1 class="masthead-subheading mastheadInner">
            <span
              v-for="(char, index) in animatedHeading"
              :key="index"
              :style="{ animationDelay: index / 10 + 's' }"
              >{{ char }}</span
            >
          </h1>
          <p class="masthead-heading mastheadInner">
            <span
              v-for="(char, index) in animatedSubHeading"
              :key="index"
              :style="{ animationDelay: index / 10 + 's' }"
              >{{ char }}</span
            >
          </p>
        </div>
      </div>
    </div>

    <!-- card start -->

    <attraction-card></attraction-card>

    <div class="content-container"></div>
    <!-- footer -->
    <my-footer></my-footer>
  </div>
</template>

<script>
import HeaderNav from "@/components/common/HeaderNav.vue";
import HeaderImage from "@/components/common/HeaderImage.vue";
import MyFooter from "@/components/common/MyFooter.vue";
import AttractionCard from "@/components/attraction/AttractionCard.vue";

export default {
  name: "HomeView",
  components: {
    HeaderNav,
    HeaderImage,
    AttractionCard,
    MyFooter,
  },
  data() {
    return {
      heading: "여행을 떠나봐요",
      subHeading: "다양한 여행지를 소개합니다.",
    };
  },
  computed: {
    animatedHeading() {
      return this.heading.split("");
    },
    animatedSubHeading() {
      return this.subHeading.split("");
    },
  },
};
</script>

<style>
.mastheadInner span {
  opacity: 0;
  animation: appear 0.5s forwards;
}

@keyframes appear {
  to {
    opacity: 1;
  }
}
</style>
