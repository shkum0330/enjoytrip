<template>
  <div class="manage">
    <nav><header-nav></header-nav></nav>
    <div class="header-image-container">
      <header-image></header-image>
      <div class="container mt-5">
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
    </div>
    <!-- content start -->
    <div class="container mt-5">
      <div class="row justify-content-center">
        <!-- user infos start -->
        <div v-for="(user, index) in users" :key="user.userId" class="col-md-3">
          <div :class="{ 'card-admin': user.role === 'ADMIN', card: user.role === 'USER' }">
            <div class="card-header text-center">
              <strong>{{ index }} </strong>
            </div>
            <div class="card-body row text-center">
              <strong>userId : {{ user.userId }} </strong>
            </div>
            <div class="card-body row">
              <div class="d-flex justify-content-center" style="margin-top: 10px">
                <button type="button" class="btn btn-primary" @click="openDetail(user.userId)">
                  정보 보기
                </button>
              </div>
            </div>
          </div>
        </div>
        <div class="d-flex justify-content-center mt-4">
          <b-pagination
            v-model="curPage"
            :total-rows="totalRows"
            :per-page="perPage"
            :emit-immediately="true"
            @input="pageChanged"
            size="lg"
          ></b-pagination>
        </div>
      </div>

      <!-- user infos end-->

      <!-- detail component start -->
      <div class="card-detail-wrapper" v-if="showDetail">
        <transition
          name="fade"
          enter-active-class="fade-enter-active"
          leave-active-class="fade-leave-active"
        >
          <member-manage-detail
            v-if="showDetail"
            :userId="selectedUserId"
            :showDetail="showDetail"
            @close="closeDetail()"
            ref="memberManageDetail"
          ></member-manage-detail>
        </transition>
      </div>
      <!-- detail component end -->
    </div>
    <my-footer></my-footer>
  </div>
</template>

<script>
import HeaderNav from "@/components/common/HeaderNav.vue";
import HeaderImage from "@/components/common/HeaderImage.vue";
import MemberManageDetail from "@/components/member/MemberManageDetail.vue";
import http from "@/api/http";
import MyFooter from "@/components/common/MyFooter.vue";

export default {
  name: "AdminView",
  props: {
    userId: {
      type: String,
    },
  },
  components: {
    HeaderNav,
    HeaderImage,
    MemberManageDetail,
    MyFooter,
  },
  data() {
    return {
      heading: "엣-헴!",
      subHeading: "관리자 페이지 입니다.",
      users: [],
      allUsers: [], // 추가: 모든 사용자 정보를 저장하는 배열
      showDetail: false,
      selectedUserId: "",
      openValue: false,
      curPage: 1,
      perPage: 4,
      totalRows: 0,
    };
  },
  created() {
    this.fetchUsersInfo();
  },
  computed: {
    animatedHeading() {
      return this.heading.split("");
    },
    animatedSubHeading() {
      return this.subHeading.split("");
    },
  },

  methods: {
    pageChanged(page) {
      this.curPage = page;
      this.updateArticles();
    },
    updateArticles() {
      let start = (this.curPage - 1) * this.perPage;
      let end = start + this.perPage;
      this.users = this.allUsers.slice(start, end);
    },
    fetchUsersInfo() {
      http
        .get(`/admin/manageMem`, {
          headers: {
            "Access-Token": this.$store.state.token.data.accessToken,
          },
        })
        .then((response) => {
          this.allUsers = response.data;

          this.allUsers.sort((a, b) => {
            if (a.role === "ADMIN" && b.role !== "ADMIN") {
              return -1; // a가 ADMIN일 경우 우선순위를 높게 설정
            } else if (a.role !== "ADMIN" && b.role === "ADMIN") {
              return 1; // b가 ADMIN일 경우 우선순위를 높게 설정
            } else {
              return 0; // role이 동일한 경우 유지
            }
          });

          this.totalRows = this.allUsers.length;
          this.updateArticles();
          console.log(response);
        })
        .catch((error) => {
          console.error(error);
        });
    },

    openDetail(value) {
      if (!this.openValue) {
        this.showDetail = true;
        this.selectedUserId = value;
        this.openValue = !this.openValue;
      } else {
        this.closeDetail();
      }
    },
    closeDetail() {
      this.showDetail = false;
      this.openValue = !this.openValue;
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
