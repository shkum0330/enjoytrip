<template>
  <div class="regist">
    <!-- content start -->
    <div class="container mt-5">
      <div class="row justify-content-center content-overlay">
        <div class="col-md-4">
          <div class="card">
            <div class="card-header bg-primary text-white text-center">
              {{ registHeader }}
            </div>
            <div class="card-body">
              <form id="RegistForm" @submit.prevent="submitRegistForm">
                <div class="form-group">
                  <input
                    type="text"
                    class="form-control"
                    id="userNameRegist"
                    name="userName"
                    placeholder="이름"
                    v-model="userName"
                    required
                  />
                  <p class="text-danger" v-if="userNameError">{{ userNameError }}</p>
                  <p class="text-possible" v-if="!userNameError && userName != ''">
                    사용 가능합니다.
                  </p>
                </div>
                <div class="form-group">
                  <input
                    type="text"
                    class="form-control"
                    id="useridRegist"
                    name="userId"
                    placeholder="아이디"
                    v-model="userId"
                    required
                  />
                  <p class="text-danger" v-if="userIdError">{{ userIdError }}</p>
                  <p class="text-possible" v-if="!userIdError && userId != ''">사용 가능합니다.</p>
                </div>

                <div class="form-group">
                  <input
                    type="password"
                    class="form-control"
                    id="userPasswordRegist"
                    name="userPassword"
                    placeholder="비밀번호"
                    v-model="userPassword"
                    required
                  />
                  <p class="text-danger" v-if="userPasswordError">{{ userPasswordError }}</p>
                  <p class="text-possible" v-if="!userPasswordError && userPassword != ''">
                    사용 가능합니다.
                  </p>
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
                      required
                    />
                    <p class="text-center">@&nbsp;&nbsp;&nbsp;&nbsp;</p>
                    <select
                      style="width: 50%"
                      class="form-select"
                      id="emaildomainRegist"
                      name="emailDomain"
                      v-model="emailDomain"
                      required
                    >
                      <option value="naver.com">naver.com</option>
                      <option value="google.com">google.com</option>
                    </select>
                    <p class="text-danger" v-if="emailIdError">{{ emailIdError }}</p>
                    <p class="text-possible" v-if="!emailIdError && emailId != ''">
                      사용 가능합니다.
                    </p>
                  </div>
                </div>
                <div class="d-flex justify-content-end" style="margin-top: 10px">
                  <button type="button" class="btn btn-danger" @click="cancel">취소</button>
                  <button type="button" class="btn btn-secondary" @click="reset">
                    내용 지우기
                  </button>
                  <button type="submit" class="btn btn-primary">회원가입</button>
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
  name: "MemberRegist",
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
      emailDomain: "naver.com",
      registHeader: "회원가입",
      useridLabel: "아이디",
      passwordLabel: "비밀번호",
      userNameLabel: "이름",
      emailIdLabel: "이메일",
      registButton: "회원가입",
      passwordConfirm: "",
      useridPattern: /^[a-z0-9_-]{5,30}$/,
      passwordPattern: /^(?=.*[a-z])(?=.*\d)[A-Za-z\d]{4,20}$/,
      emailPattern: /^\S+@\S+\.\S+$/,
      userNamePattern: /^[ㄱ-ㅎ가-힣0-9_-]{3,20}$/,
      userNameError: "",
      userIdError: "",
      userPasswordError: "",
      emailIdError: "",
    };
  },
  watch: {
    userName(value) {
      if (!this.userNamePattern.test(value)) {
        this.userNameError =
          "이름은 3-20자의 한글, 숫자, 언더스코어(_) 혹은 하이픈(-)만 사용할 수 있습니다.";
      } else {
        this.userNameError = "";
      }
    },

    userId(value) {
      if (!this.useridPattern.test(value)) {
        this.userIdError =
          "아이디는 5-30자의 영문자, 숫자, 언더스코어(_) 혹은 하이픈(-)만 사용할 수 있습니다.";
      } else {
        this.userIdError = "";
      }
    },

    userPassword(value) {
      if (!this.passwordPattern.test(value)) {
        this.userPasswordError =
          "비밀번호는 4-20자이며, 영문 소문자, 숫자를 각각 최소 1개 이상 포함해야 합니다.";
      } else {
        this.userPasswordError = "";
      }
    },

    emailId(value) {
      if (!this.emailPattern.test(value + "@" + this.emailDomain)) {
        this.emailIdError = "이메일 형식이 올바르지 않습니다.";
      } else {
        this.emailIdError = "";
      }
    },
  },

  computed: {
    canSubmitRegistForm() {
      return (
        this.useridPattern.test(this.userId) &&
        this.passwordPattern.test(this.userPassword) &&
        // this.userPassword === this.passwordConfirm &&
        this.emailPattern.test(this.emailId + "@" + this.emailDomain) &&
        this.userNamePattern.test(this.userName)
      );
    },
  },

  methods: {
    reset() {
      this.userName = "";
      this.userId = "";
      this.userPassword = "";
      this.emailId = "";
      this.emailDomain = "";
      this.passwordConfirm = "";
    },
    cancel() {
      this.$router.push("/");
    },
    submitRegistForm() {
      // Only submit if all form validation checks pass
      if (!this.canSubmitRegistForm) {
        alert("양식을 정확히 입력해주세요!");
        return;
      }

      // 서버로 보낼 데이터 준비
      const userInfo = {
        ...this.$data,
      };

      // API 호출
      console.log(userInfo);
      http
        .post(`/join`, userInfo)
        .then(() => {
          alert("회원가입에 성공하였습니다.!!");
          this.$router.push({ name: "login" });
        })
        .catch((error) => {
          alert(error.response.data.message);
        });
    },
  },
};
</script>

<style scoped>
.form-group {
  position: relative;
}

.text-danger {
  position: absolute;
  top: 100%;
  left: 0;
  font-size: 0.8em;
  transform: translateY(-5px);
}

.text-danger:before {
  content: "";
  display: block;
  position: absolute;
  width: 0;
  height: 0;
  border-left: 5px solid transparent;
  border-right: 5px solid transparent;
  border-bottom: 5px solid red;
  top: -5px;
  left: 10px;
}

.text-possible {
  position: absolute;
  top: 100%;
  left: 0;
  font-size: 0.8em;
  transform: translateY(-5px);
  color: blue;
}

.text-possible:before {
  content: "";
  display: block;
  position: absolute;
  width: 0;
  height: 0;
  border-left: 5px solid transparent;
  border-right: 5px solid transparent;
  border-bottom: 5px solid rgb(4, 0, 255);
  top: -5px;
  left: 10px;
}
</style>
