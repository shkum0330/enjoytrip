<template>
  <div class="manageDetail">
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
                      :disabled="formDisabled"
                      required
                    />
                  </div>
                  <div class="form-group">
                    <input
                      type="text"
                      class="form-control"
                      id="internalUserId"
                      v-model="internalUserId"
                      disabled
                    />
                  </div>
                  <div class="form-group">
                    <input
                      type="password"
                      class="form-control"
                      id="userPassword"
                      v-model="userPassword"
                      :disabled="formDisabled"
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
                        :disabled="formDisabled"
                        required
                      />
                      <p class="text-center">@&nbsp;&nbsp;&nbsp;&nbsp;</p>
                      <select
                        style="width: 50%"
                        class="form-select"
                        id="emaildomainRegist"
                        name="emailDomain"
                        v-model="emailDomain"
                        :disabled="formDisabled"
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
                      id="role"
                      :value="role"
                      disabled
                    />
                  </div>

                  <div
                    v-if="formDisabled"
                    class="d-flex justify-content-end"
                    style="margin-top: 10px"
                  >
                    <button
                      type="button"
                      class="btn btn-danger"
                      @click="deleteMember(userId)"
                      v-if="role != 'ADMIN'"
                    >
                      회원탈퇴
                    </button>
                    <button
                      type="button"
                      class="btn btn-secondary"
                      @click="cancel()"
                    >
                      취소
                    </button>
                    <button
                      type="button"
                      class="btn btn-blue"
                      @click="enableForm()"
                    >
                      수정하기
                    </button>
                  </div>
                  <div
                    v-if="!formDisabled"
                    class="d-flex justify-content-end"
                    style="margin-top: 10px"
                  >
                    <button
                      type="button"
                      class="btn btn-secondary"
                      @click="disableForm()"
                    >
                      취소
                    </button>
                    <button
                      type="button"
                      class="btn btn-blue"
                      @click="submitModifyForm(internalUserId)"
                    >
                      확인
                    </button>
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
import http from "@/api/http";

export default {
  name: "MemberManageDetail",
  data() {
    return {
      internalUserId: "",
      userPassword: "",
      userName: "",
      emailId: "",
      emailDomain: "",
      joinDate: "",
      role: "",
      formDisabled: true,
    };
  },

  props: {
    userId: {
      type: String,
      required: true,
    },
    isDisabled: {
      type: Boolean,
      required: false,
    },
  },
  mounted() {
    this.fetchUserInfo(this.userId);
    this.internalUserId = this.userId;
  },

  methods: {
    deleteMember(value) {
      http
        .get(`/admin/modifyMem/${value}`)
        .then((response) => {
          console.log(response);
          ("");
          const alertMessage = "회원탈퇴에 성공하였습니다람쥐🐿.";
          alert(alertMessage);
          window.location.reload();
        })
        .catch((error) => {
          alert(error);
        });
    },
    cancel() {
      this.disableForm();
      this.$emit("close");
    },

    disableForm() {
      this.formDisabled = true;
    },
    enableForm() {
      this.formDisabled = false;
    },

    submitModifyForm(userid) {
      // 서버로 보낼 데이터 준비
      const userInfo = {
        userPassword: this.userPassword,
        userName: this.userName,
        emailId: this.emailId,
        emailDomain: this.emailDomain,
      };
      console.log(userInfo);

      // API 호출
      http
        .post(`/admin/modifyMem/${userid}`, userInfo)
        .then((response) => {
          // userInfo로 update 요청
          if (response.data != "") {
            const alertMessage = "정보가 수정되었습니다.";
            alert(alertMessage);
            this.formDisabled = true;
          } else {
            const alertMessage = "서버로부터 데이터를 요청받을 수 없습니다.";
            alert(alertMessage);
            this.formDisabled = true;
          }
        })
        .catch((error) => {
          // update 실패 처리
          const alertMessage = "서버로부터 데이터를 받지 못하였습니다.ㅜㅜ";
          alert(alertMessage);
          console.log(error);
          this.formDisabled = true;
        });
      this.disableForm();
    },

    fetchUserInfo(value) {
      console.log(value);
      http
        .get(`/admin/manageMem/${value}`, {
          headers: {
            "Access-Token": this.$store.state.token.data.accessToken,
          },
        })
        .then((response) => {
          const user = response.data;
          this.internalUserId = user.userId;
          this.userPassword = user.userPassword;
          this.userName = user.userName;
          this.emailId = user.emailId;
          this.emailDomain = user.emailDomain;
          this.joinDate = user.joinDate;
          this.role = user.role;
          this.formDisabled = true;
        })
        .catch((error) => {
          alert("에러발생!!!!\n에러메세지 : " + error);
          console.error(error);
          this.$router.push("/");
        });
    },
  },
};
</script>
