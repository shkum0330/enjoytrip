<template>
  <div class="attractioncard">
    <!-- card component start -->
    <div class="mt-5 position-relative">
      <div class="px-0 px-sm-1 px-md-3 px-lg-5 position-relative">
        <!-- 카드리스트 -->
        <div class="mt-4">
          <div class="d-flex list-unstyled scrollList" id="scrollListAttr" ref="scrollList">
            <div
              v-for="attraction in attractionList"
              :key="attraction.contentId"
              class="card mainCard mainCardAttr"
              :style="{
                'background-image': `url('${attraction.firstImage}')`,
                'border-radius': '20px',
              }"
            >
              <div class="p-0 card-body">
                <div class="mainCardAttrInner mainCardAttrTitle">
                  {{ attraction.title }}
                </div>
                <button
                  class="mainCardAttrInner btn btn-primary btn-xl mastheadInner border-1 border-dark mainCardAttrBtn"
                  @click="cardDetail(attraction)"
                >
                  자세히 보기
                </button>
              </div>
            </div>
          </div>
        </div>
        <!-- 카드리스트 end -->

        <!-- 버튼 -->
        <button
          class="btn btnScroll btnScrollLeftClass start-0"
          id="btnScrollLeftAttr"
          @click="scrollLeft"
        ></button>
        <button
          class="btn btnScroll btnScrollRightClass end-0"
          id="btnScrollRightAttr"
          @click="scrollRight"
        ></button>
        <!-- 버튼 end -->
      </div>
    </div>
    <!-- card component end -->
    <!-- CardDetail start -->
    <div class="card-detail-wrapper" v-if="showDetail">
      <card-detail
        v-if="showDetail"
        :cardInfo="selectedCardInfo"
        ref="CardDetail"
        @close="closeDetail()"
      ></card-detail>
    </div>
    <!-- CardDetail end -->
  </div>
</template>

<script>
import http from "@/api/http.js";
import CardDetail from "@/components/attraction/CardDetail.vue";
export default {
  name: "AttractionCard",
  components: { CardDetail },
  data() {
    return {
      attractionList: [],
      showDetail: false,
      selectedCardInfo: "",
    };
  },

  mounted() {
    http.get(`/`).then((response) => {
      this.attractionList = response.data;
    });
  },

  methods: {
    scrollLeft() {
      const scrollList = this.$refs.scrollList;
      scrollList.scrollLeft -= 200; // Adjust the scroll distance as needed
    },
    scrollRight() {
      const scrollList = this.$refs.scrollList;
      scrollList.scrollLeft += 200; // Adjust the scroll distance as needed
    },
    cardDetail(content) {
      this.selectedCardInfo = content;
      this.showDetail = true;
      this.$nextTick(() => {
        this.$refs.CardDetail.setData(content);
      });
    },
    closeDetail() {
      this.showDetail = false;
      this.openValue = !this.openValue;
    },
  },
};
</script>

<style>
.card-slide-enter-active,
.card-slide-leave-active {
  transition: transform 0.5s ease;
}

.card-slide-enter,
.card-slide-leave-to {
  transform: translateX(100%);
}
</style>
