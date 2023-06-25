<template>
  <div class="findpassword">
    <!-- content start -->
    <div class="container mt-5">
      <div class="row justify-content-center content-overlay">
        <div class="col-md-4">
          <div class="card">
            <div class="card-header bg-primary text-white text-center">비밀번호 찾기</div>
            <div class="card-body">
              <form id="findPassForm" @submit.prevent="submitFindPassForm">
                <div class="form-group">
                  <input
                    type="text"
                    class="form-control"
                    id="useridRegist"
                    name="userId"
                    placeholder="아이디"
                    v-model="userId"
                  />
                </div>

                <div class="d-flex justify-content-end" style="margin-top: 10px">
                  <button type="button" class="btn btn-danger" @click="cancel">취소</button>
                  <button type="button" class="btn btn-secondary" @click="reset">
                    내용 지우기
                  </button>
                  <button type="submit" class="btn btn-primary">비밀번호 찾기</button>
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
import HeaderNav from "@/components/common/HeaderNav.vue";
import HeaderImage from "@/components/common/HeaderImage.vue";
import http from "@/api/http.js";
export default {
  name: "MemberFindPassword",
  components: {
    HeaderNav,
    HeaderImage,
  },
  data() {
    return {
      userName: "",
      userId: "",
      userPassword: "",
      emailId: "",
      emailDomain: "",
    };
  },

  computed: {
    canSubmitFindPassForm() {
      return this.userId.length > 0 && this.userPassword.length > 0;
    },
  },

  methods: {
    reset() {
      this.userName = "";
      this.userId = "";
      this.userPassword = "";
      this.emailId = "";
      this.emailDomain = "";
    },

    cancel() {
      this.$router.push("/");
    },
    submitFindPassForm() {
      alert("죄송합니다.\n기능을 준비중입니다.");
      this.$router.push({ name: "login" });

      // 서버로 보낼 데이터 준비
      // const userInfo = {
      //   ...this.$data,
      // };

      // // API 호출
      // console.log(this.userId);
      // http
      //   .get(`/${this.userInfo.userId}/findById`)
      //   .then((response) => {
      //     alert("userId: " + this.userId + "\n비밀번호: " + response.data.userPassword);
      //   })
      //   .catch((error) => {
      //     alert("에러발생!!!!\n에러메세지 : " + error);
      //     console.error(error);
      //     this.$router.push("/");
      //   });
    },
  },
};
</script>
