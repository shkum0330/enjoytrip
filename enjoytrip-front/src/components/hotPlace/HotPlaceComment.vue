<template>
  <div class="hotplaceBoardComment mt-4 p-4">
    <div class="row justify-content-center">
      <div class="col-lg-8 col-md-8 col-sm-12" style="margin-top: 10%">
        <form id="commentForm" @submit.prevent="submitCommentForm">
          <h2 class="text-center">댓글 등록</h2>
          <div class="d-flex align-items-center justify-content-start mb-4">
            <label for="userId" class="form-label" style="margin-right: 0px"
              >작성자</label
            >
            <input
              type="text"
              class="form-control"
              id="userId"
              v-model="userName"
              style="width: 20%; margin-left: 10px"
              readonly
            />
          </div>
          <label for="comment" class="form-label">내용</label>
          <textarea
            class="form-control"
            id="comment"
            v-model="comment.content"
            rows="3"
            required
          ></textarea>

          <div class="d-flex justify-content-end mt-3">
            <button
              type="button"
              class="btn btn-blue"
              @click="submitCommentForm()"
            >
              댓글 등록
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/api/http.js";
import { mapGetters } from "vuex";

export default {
  name: "HotPlaceComment",
  components: {},
  data() {
    return {
      userName: this.$store.state.userInfo.userName,
      comment: {
        content: "",
      },
    };
  },

  computed: {
    ...mapGetters(["isLogin"]),
  },
  created() {
    this.articleNo = this.$route.params.articleNo; // Add this line
  },

  methods: {
    submitCommentForm() {
      const confirmMessage = "댓글을 등록하시겠습니까?";
      if (confirm(confirmMessage)) {
        if (this.$store.state.token == null) {
          alert("로그인 하세요.");
          this.$router.push({ name: "login" });
        } else {
          http
            .post(
              `/hotPlaceBoard/comment/write/${this.articleNo}`,
              this.comment,
              {
                headers: {
                  "Access-Token": this.$store.state.token.data.accessToken,
                },
              }
            )
            .then((response) => {
              console.log(response);
              if (response.status == 200) {
                alert("댓글이 등록되었습니다.");
                window.location.reload();
              }
            })
            .catch((error) => {
              alert(error);
            });
        }
      }
    },
  },
};
</script>

<style scoped>
.hotplaceBoardComment {
  border-radius: 1rem;
  background-color: #fff;
}

.hotplaceBoardComment .form-label {
  font-weight: bold;
  color: #ffd700;
}

.hotplaceBoardComment .btn {
  background-color: #ffd700;
  border: none;
  color: #000;
}

.hotplaceBoardComment .btn:hover {
  background-color: #ffc107;
}
</style>
