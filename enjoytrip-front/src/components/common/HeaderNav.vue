<template>
  <div class="header">
    <!-- Navigation Start -->
    <nav
      class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top"
      id="mainNav"
    >
      <div class="container">
        <router-link to="/" class="navbar-brand">
          <img src="@/assets/img/logos/enjoytrip_logo.png" class="p-0 w-100" />
        </router-link>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarResponsive"
          aria-controls="navbarResponsive"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
            <li class="nav-item blink">
              <router-link class="nav-link" :to="{ name: `searchRegion` }"
                >지역별여행지</router-link
              >
            </li>
            <li class="nav-item blink">
              <router-link class="nav-link" to="#">나의여행계획</router-link>
            </li>
            <li class="nav-item blink">
              <router-link class="nav-link" :to="{ name: 'list' }"
                >핫플게시판</router-link
              >
            </li>

            <!-- 로그인 했을 경우 -->
            <li class="nav-item dropdown blink" id="navMemLogIn" v-if="isLogin">
              <router-link
                to="#"
                class="btn dropdown-toggle nav-link text-end"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                [{{ getUserName }}]님 안녕하세요.
              </router-link>
              <div class="dropdown-menu dropdown-menu-end" role="menu">
                <router-link
                  :to="{ name: 'detail' }"
                  id="myPageBtn"
                  class="dropdown-item"
                >
                  마이페이지
                </router-link>
                <button
                  type="button"
                  class="dropdown-item navLogOutBtn pointer-event"
                  @click="logout"
                >
                  로그아웃
                </button>
              </div>
            </li>

            <!-- admin 계정인 경우 -->
            <li
              class="nav-item dropdown blink"
              id="navAdminLogIn"
              v-if="isAdmin"
            >
              <router-link class="nav-link text-end" :to="{ name: 'admin' }"
                >회원관리</router-link
              >
            </li>

            <!-- 로그인 안했을 경우 -->
            <li
              class="nav-item dropdown blink"
              id="navNonLogIn"
              v-if="!isLogin"
            >
              <!-- v-if="isLoggedIn == false"-->
              <a
                class="btn dropdown-toggle nav-link text-end"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                로그인/회원가입
              </a>
              <div class="dropdown-menu dropdown-menu-end" role="menu">
                <router-link
                  :to="{ name: 'login' }"
                  id="navRegBtn"
                  class="dropdown-item"
                >
                  로그인
                </router-link>
                <router-link
                  :to="{ name: 'regist' }"
                  id="navRegBtn"
                  class="dropdown-item"
                >
                  회원가입
                </router-link>
                <router-link
                  :to="{ name: 'findPass' }"
                  id="navFindPassBtn"
                  class="dropdown-item"
                >
                  비밀번호 찾기
                </router-link>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <!-- Navigation End -->
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  name: "HeaderNav",
  components: {},

  data() {
    return {};
  },
  computed: {
    ...mapGetters(["isLogin", "getUserName", "getUserRole", "isAdmin"]),
  },
  methods: {
    ...mapActions({
      logout: "logout",
    }),

    logout() {
      if (this.isLogin) {
        alert(
          "로그아웃되었습니다.\n" + this.getUserName + "님 안녕히가세요.\n"
        );
        this.$store.dispatch("logout");
        window.location.href = "/"; // 홈페이지로 리다이렉트
      } else {
        alert("로그인 되어있지 않습니다. ==> 로직 수정해야합니다!");
      }
    },
  },
};
</script>
