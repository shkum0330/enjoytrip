<template>
  <div class="searchregion">
    <!-- 중앙 content start -->
    <div class="container" style="max-width: 70% !important">
      <div style="height: 70px"></div>
      <div class="alert alert-primary mt-3 text-center fw-bold" role="alert">전국 관광지 정보</div>
      <div class="row">
        <div class="row align-content-center">
          <!-- 관광지 검색 start -->
          <form
            class="d-flex my-3"
            onsubmit="return false;"
            role="search"
            @submit.prevent="submitSearchForm()"
          >
            <select
              id="search-sido"
              class="form-select mx-1"
              v-model="selectedSidoCode"
              @change="handleSelectSidoCode(selectedSidoCode)"
            >
              <option value="">시/도</option>
              <option v-for="sido in sidoList" :key="sido.sidoCode" :value="sido.sidoCode">
                {{ sido.sidoName }}
              </option>
            </select>
            <select
              id="search-gugun"
              class="form-select mx-1"
              v-model="selectedGugunCode"
              @change="handleSelectGugunCode(selectedGugunCode)"
            >
              <option value="">시/군/구</option>
              <option v-for="gugun in gugunList" :key="gugun.gugunCode" :value="gugun.gugunCode">
                {{ gugun.gugunName }}
              </option>
            </select>
            <select
              id="search-content-id"
              class="form-select mx-1"
              v-model="selectedContentTypeId"
              @change="handleSelectContentTypeId(selectedContentTypeId)"
            >
              <option value="" selected>관광지 유형 선택</option>
              <option value="12">관광지</option>
              <option value="14">문화시설</option>
              <option value="15">축제공연행사</option>
              <option value="25">여행코스</option>
              <option value="28">레포츠</option>
              <option value="32">숙박</option>
              <option value="38">쇼핑</option>
              <option value="39">음식점</option>
            </select>

            <input
              id="search-keyword"
              class="form-select mx-1"
              type="search"
              placeholder="검색어"
            />
            <button
              id="btn-search"
              class="btn btn-outline-success w-25 mx-1"
              type="button"
              @click="submitSearchForm()"
            >
              검색
            </button>
          </form>
        </div>
        <!-- 관광지 검색 end -->

        <!-- kakao map start -->
        <div class="row align-content-center">
          <div id="map" style="width: 100%; height: 600px"></div>
        </div>
        <!-- kakao map end -->

        <!-- attraction list start -->
        <div class="row m-3">
          <table class="table table-striped">
            <thead>
              <tr>
                <th>이미지</th>
                <th>관광지명</th>
                <th>주소</th>
                <th class="col-md-2">기능1</th>
              </tr>
            </thead>
            <tbody id="trip-list">
              <tr v-for="attraction in attractionList" :key="attraction.contentId">
                <td>
                  <img
                    :src="`${attraction.firstImage}`"
                    class="img-fluid"
                    style="max-width: 100px; max-height: 100px"
                  />
                </td>
                <td>{{ attraction.title }}</td>
                <td>{{ attraction.addr1 }}</td>
                <td>
                  <button type="button" class="btn btn-primary" @click="cardDetail(attraction)">
                    자세히
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- attraction list end -->

        <!-- CardDetail start -->
        <div class="card-detail-wrapper" v-if="showDetail">
          <transition name="fade">
            <card-detail
              :cardInfo="selectedCardInfo"
              ref="CardDetail"
              @close="closeDetail()"
            ></card-detail>
          </transition>
        </div>
        <!-- CardDetail end -->
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/api/http.js";
import CardDetail from "@/components/attraction/CardDetail.vue";
import { mapActions } from "vuex";

export default {
  name: "SearchRegion",
  components: { CardDetail },
  data() {
    return {
      // 검색 조건에 필요한 data
      sidoList: [
        {
          sidoCode: "",
          sidoName: "",
        },
      ],
      gugunList: [
        {
          gugunCode: "",
          gugunName: "",
        },
      ],

      selectedSidoCode: "",
      selectedGugunCode: "",
      selectedContentTypeId: "",

      // list 화면 출력에 필요한 data
      attractionList: [],

      // Card Detail 컴포넌트를 띄우는데 필요한 data
      selectedCardInfo: "",
      showDetail: false,
    };
  },
  mounted() {
    http
      .get("/attraction/sido")
      .then((response) => {
        this.sidoList = response.data;
      })
      .catch((error) => {
        alert(error);
      });
  },
  methods: {
    ...mapActions({
      readAttractionList: "readAttractionList",
    }),
    handleSelectSidoCode(value) {
      http
        .get(`/attraction/gugun?sidoCode=${value}`)
        .then((response) => {
          this.gugunList = response.data;
        })
        .catch((error) => {
          alert(error);
        });
    },
    handleSelectGugunCode(value) {
      this.selectedGugunCode = value;
    },
    handleSelectContentTypeId(value) {
      this.selectedContentTypeId = value;
    },
    submitSearchForm() {
      const searchCondition = {
        sido: this.selectedSidoCode,
        gugun: this.selectedGugunCode,
        contentTypeId: this.selectedContentTypeId,
      };

      http
        .post("/attraction/search", searchCondition)
        .then((response) => {
          this.attractionList = response.data;
          this.$store.dispatch("readAttractionList", response);
        })
        .catch((error) => {
          alert(error);
        });
    },

    cardDetail(attraction) {
      this.selectedCardInfo = attraction;
      this.showDetail = true;
      this.$nextTick(() => {
        this.$refs.CardDetail.setData(attraction);
      });
    },

    closeDetail() {
      this.showDetail = false;
    },

    addMyPlan(attraction) {},
  },
};
</script>
