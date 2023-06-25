<template>
  <div class="hotPlaceBoardView">
    <header-nav></header-nav>

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
    <router-view></router-view>
    <router-view name="BoardView"></router-view>
    <router-view name="HotPlaceCommentList"></router-view>
    <router-view name="HotPlaceComment"></router-view>
    <div class="content-container"></div>
    <my-footer></my-footer>
  </div>
</template>

<script>
import HeaderNav from "@/components/common/HeaderNav.vue";
import HeaderImage from "@/components/common/HeaderImage.vue";
import MyFooter from "@/components/common/MyFooter.vue";

export default {
  name: "HotPlaceBoardView",
  components: { HeaderNav, HeaderImage, MyFooter },

  data() {
    return {
      heading: "안녕하세요",
      subHeading: "게시판에 오신걸 환영합니다.",
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

.mastheadInner span { opacity: 0; transform: translateX(100%); animation:
slide-in 1s forwards; } @keyframes slide-in { to { opacity: 1; transform:
translateX(0); } }
