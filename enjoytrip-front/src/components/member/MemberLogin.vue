<template>
  <div class="login">
    <!-- content start -->
    <div class="container mt-5">
      <div class="row justify-content-center content-overlay">
        <div class="col-md-4">
          <div class="card">
            <div class="card-header bg-primary text-white text-center">
              {{ loginHeader }}
            </div>
            <div class="card-body">
              <form id="LoginForm" @submit.prevent="submitLoginForm">
                <div class="form-group">
                  <input
                    type="text"
                    class="form-control"
                    id="userId"
                    placeholder="아이디"
                    v-model="userId"
                    required
                  />
                </div>

                <div class="form-group">
                  <input
                    type="password"
                    class="form-control"
                    id="userPassword"
                    placeholder="비밀번호"
                    v-model="userPassword"
                    required
                  />
                </div>
                <div class="d-flex justify-content-end" style="margin-top: 10px">
                  <button type="button" class="btn btn-blue" @click="regist">회원가입</button>
                  <button type="button" class="btn btn-danger" @click="cancel">취소</button>
                  <button type="button" class="btn btn-secondary" @click="reset">
                    내용 지우기
                  </button>
                  <button type="submit" class="btn btn-primary">
                    {{ loginButton }}
                  </button>
                </div>
                <div class="d-flex justify-content-center" style="margin-top: 10px">
                  <router-link :to="{ name: 'findPass' }"
                    ><strong>비밀번호를 잊으셨나요?</strong></router-link
                  >
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/api/http.js";
import { mapActions, mapGetters } from "vuex";
export default {
  name: "MemberLogin",
  components: {},
  data() {
    return {
      userId: "",
      userPassword: "",
      userName: "",
      role: "",
      loginHeader: "로그인",
      useridLabel: "아이디",
      passwordLabel: "비밀번호",
      loginButton: "로그인",
    };
  },
  computed: {
    ...mapGetters(["isLogin", "getUserName", "getUserRole"]),
  },
  methods: {
    reset() {
      this.userId = "";
      this.userPassword = "";
    },
    cancel() {
      this.$router.push("/");
    },
    regist() {
      this.$router.push("regist");
    },

    ...mapActions({
      setUserInfo: "setUserInfo",
    }),

    submitLoginForm() {
      // 서버로 보낼 데이터 준비
      const userInfo = {
        userId: this.userId,
        userPassword: this.userPassword,
      };

      // API 호출
      http
        .post(`/login`, userInfo)
        .then((response) => {
          // 로그인 성공 시 처리
          try {
            const alertMessage = "로그인에 성공하였습니다. 환영합니다!";
            alert(alertMessage);
            this.$store.dispatch("setToken", response);
            this.storeUserInfo();
            this.$router.push("/");
          } catch (error) {
            alert(response.message);
          }
        })
        .catch((error) => {
          // 로그인 실패 시 처리
          alert(error.response.data.message);
        });
    },
    storeUserInfo() {
      http
        .get("/member/details", {
          headers: {
            "Access-Token": this.$store.state.token.data.accessToken,
          },
        })
        .then((response) => {
          this.$store.dispatch("setUserInfo", response);
          console.log(response);
        })
        .catch((error) => {
          console.log(error.response.data.message);
        });
    },
  },
};
</script>
