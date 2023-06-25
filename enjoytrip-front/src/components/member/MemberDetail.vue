<template>
  <div class="detail">
    <!-- content start -->
    <div class="container mt-5">
      <div class="row justify-content-center content-overlay">
        <div class="col-md-6">
          <form id="ModifyForm" @submit.prevent="submitModifyForm">
            <div class="row card">
              <div class="card-header bg-primary text-white text-center">
                <strong>{{ userName }} </strong> 님의 정보
              </div>
              <div class="card-body row">
                <div class="card-body col-md-0">
                  <div class="form-group text-end">
                    <label><strong>이름</strong></label>
                  </div>
                  <div class="form-group text-end">
                    <label><strong>아이디</strong></label>
                  </div>
                  <div class="form-group text-end">
                    <label><strong>비밀번호</strong></label>
                  </div>
                  <div class="form-group text-end">
                    <label><strong>이메일</strong></label>
                  </div>
                  <div class="form-group text-end">
                    <label><strong>가입일</strong></label>
                  </div>
                  <div class="form-group text-end">
                    <label><strong>등급</strong></label>
                  </div>
                </div>

                <div class="card-body col-md-6">
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control"
                      id="userName"
                      v-model="userName"
                      :disabled="isDisabled"
                      required
                    />
                  </div>
                  <div class="form-group">
                    <input type="text" class="form-control" id="userId" v-model="userId" disabled />
                  </div>
                  <div class="form-group">
                    <input
                      type="password"
                      class="form-control"
                      id="userPassword"
                      v-model="userPassword"
                      :disabled="isDisabled"
                      required
                    />
                  </div>
                  <div class="form-group">
                    <div class="d-flex justify-content-between">
                      <input
                        style="width: 40%"
                        type="text"
                        class="form-control"
                        id="emailidRegist"
                        name="emailId"
                        placeholder="이메일"
                        v-model="emailId"
                        :disabled="isDisabled"
                        required
                      />
                      <p class="text-center">@&nbsp;&nbsp;&nbsp;&nbsp;</p>
                      <select
                        style="width: 50%"
                        class="form-select"
                        id="emaildomainRegist"
                        name="emailDomain"
                        v-model="emailDomain"
                        :disabled="isDisabled"
                        required
                      >
                        <option value="naver.com">naver.com</option>
                        <option value="google.com">google.com</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control"
                      id="joinDate"
                      v-model="joinDate"
                      disabled
                    />
                  </div>
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control"
                      id="userRole
              "
                      :value="userRole"
                      disabled
                    />
                  </div>
                  <div
                    v-if="isDisabled"
                    class="d-flex justify-content-end"
                    style="margin-top: 10px"
                  >
                    <button type="button" class="btn btn-secondary" @click="home">홈으로</button>
                    <button type="button" class="btn btn-blue" @click="modify">수정하기</button>
                  </div>

                  <div
                    v-if="isDisabled == false"
                    class="d-flex justify-content-end"
                    style="margin-top: 10px"
                  >
                    <button type="button" class="btn btn-danger" @click="deleteMember()">
                      회원탈퇴
                    </button>
                    <button type="button" class="btn btn-secondary" @click="cancel()">취소</button>
                    <button type="submit" class="btn btn-blue">확인</button>
                  </div>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderNav from "@/components/common/HeaderNav.vue";
import HeaderImage from "@/components/common/HeaderImage.vue";
import { mapState, mapActions } from "vuex";
import http from "@/api/http";
export default {
  name: "MemberDetail",
  components: {
    HeaderNav,
    HeaderImage,
  },
  data() {
    return {
      userId: "",
      userPassword: "",
      userName: "",
      emailId: "",
      emailDomain: "",
      joinDate: "",
      userRole: "",
      isDisabled: true,
    };
  },
  computed: {
    ...mapState({
      userInfo: (state) => state.userInfo,
    }),
  },
  created() {
    this.fetchUserInfo();
  },
  methods: {
    ...mapActions({
      setUserInfo: "setUserInfo",
      logout: "logout",
    }),
    deleteMember() {
      const confirmMessage = "정말로 회원탈퇴를 하시겠습니까?";
      if (confirm(confirmMessage)) {
        http
          .get(`/member/delete`, {
            headers: {
              "Access-Token": this.$store.state.token.data.accessToken,
            },
          })
          .then((response) => {
            console.log(response);
            this.$store.dispatch("logout");
            const alertMessage = "회원탈퇴에 성공하였습니다람쥐🐿.";
            alert(alertMessage);
            this.$router.push("/");
          })
          .catch((error) => {
            alert(error.response.data.message);
          });
      }
    },
    home() {
      this.$router.push("/");
    },
    cancel() {
      this.isDisabled = true;
    },

    modify() {
      this.isDisabled = false;
    },
    submitModifyForm() {
      // 서버로 보낼 데이터 준비
      const userInfo = {
        userPassword: this.userPassword,
        userName: this.userName,
        emailId: this.emailId,
        emailDomain: this.emailDomain,
      };

      // API 호출
      http
        .post(`member/update`, userInfo, {
          headers: {
            "Access-Token": this.$store.state.token.data.accessToken,
          },
        })
        .then((response) => {
          // userInfo로 update 요청
          if (response.data != "") {
            const alertMessage = "정보가 수정되었습니다.";
            alert(alertMessage);
            this.isDisabled = true;
          }
        })
        .catch((error) => {
          // update 실패 처리
          alert(error.response.data.message);
          this.isDisabled = true;
        });
    },

    fetchUserInfo() {
      http
        .get("/member/details", {
          headers: {
            "Access-Token": this.$store.state.token.data.accessToken,
          },
        })
        .then((response) => {
          this.userId = response.data.userId;
          this.userPassword = response.data.userPassword;
          this.userName = response.data.userName;
          this.emailId = response.data.emailId;
          this.emailDomain = response.data.emailDomain;
          this.joinDate = response.data.joinDate;
          this.userRole = response.data.role;
        })
        .catch((error) => {
          console.log(error.response.data.message);
        });
    },
  },
};
</script>

<style>
.fade-enter-active,
.fade-leave-active {
  opacity: 0;
  transition: opacity 0.3s ease-in;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
  transition: opacity 0.3s ease-in;
}
</style>
