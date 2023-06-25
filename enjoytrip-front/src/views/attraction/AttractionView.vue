<template>
  <div class="attractionview">
    <header-nav></header-nav>

    <div class="header-image-container">
      <header-image></header-image>
      <div class="container mt-5">
        <div class="row justify-content-center content-overlay">
          <div class="masthead-subheading mastheadInner">여행을 떠나봐요</div>
          <div class="masthead-heading mastheadInner">가보고싶은 지역이 있나요?</div>
        </div>
      </div>
    </div>
    <search-region />
    <kakao-map />

    <my-footer></my-footer>
  </div>
</template>

<script>
import HeaderNav from "@/components/common/HeaderNav.vue";
import HeaderImage from "@/components/common/HeaderImage.vue";
import MyFooter from "@/components/common/MyFooter.vue";
import SearchRegion from "@/components/attraction/SearchRegion.vue";
import KakaoMap from "@/components/attraction/KakaoMap.vue";

export default {
  name: "AttractionView",
  components: { HeaderNav, HeaderImage, MyFooter, SearchRegion, KakaoMap },
};
</script>
