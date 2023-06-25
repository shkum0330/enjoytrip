<template>
  <div class="comments-section p-4 mb-4">
    <div class="row justify-content-center">
      <div class="col-lg-8 col-md-8 col-sm-12" style="margin-top: 10%">
        <div v-if="comments.length == 0">
          <h1 class="card-title text-center">등록된 댓글이 없습니다.</h1>
        </div>
        <div v-else>
          <h1 class="card-title text-center">댓글</h1>
        </div>
        <ul class="list-group list-group-flush shadow">
          <li
            class="list-group-item"
            v-for="(comment, index) in comments"
            :key="index"
          >
            <div class="d-flex align-items-center justify-content-start mt-3">
              <div class="badge bg-primary text-white">
                {{ comment.userId }}
              </div>
              <div
                v-if="comment.userId == getUserId"
                style="color: red; font-weight: auto"
              >
                이거 내가 작성함 ㅋㅋㅋ
              </div>
              <div style="margin-left: 5px">
                작성일 : {{ comment.registerTime }}
              </div>
            </div>
            <p
              class="mt-2"
              v-if="!comment.editing"
              style="
                width: 50%;
                margin-left: 10px;
                white-space: pre-wrap;
                font-weight: bold;
              "
            >
              {{ comment.content }}
            </p>
            <textarea
              class="form-control mt-2"
              v-else
              v-model="comment.content"
              rows="3"
            ></textarea>
            <p
              type="button"
              class="btn btn-like"
              :class="{
                'btn-primary': !comment.isLike,
                'btn-blue': comment.isLike,
              }"
              @click="toggleLike(comment)"
            >
              <i v-if="comment.isLike" class="fa fa-heart"></i>
              <i v-else class="fa fa-heart-o"></i>
              <span>{{ !comment.isLike ? "좋아요" : "싫어요" }}</span>
              <i class="fas fa-heart heart-animation"></i>
              <span style="margin-left: 5px; font-weight: bold">{{
                comment.likes
              }}</span>
            </p>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/api/http.js";
import { mapGetters } from "vuex";

export default {
  name: "HotPlaceCommentList",
  components: {},
  data() {
    return {
      articleNo: "",
      comments: [],
    };
  },

  beforeRouteEnter(to, from, next) {
    const articleNo = to.params.articleNo;
    next((vm) => {
      vm.initCommentInfos(articleNo);
    });
  },
  beforeRouteUpdate(to, from, next) {
    const articleNo = to.params.articleNo;

    this.initCommentInfos(articleNo);
    next();
  },
  computed: {
    ...mapGetters(["getUserId"]),
  },
  methods: {
    initCommentInfos(articleNo) {
      this.articleNo = articleNo;
      http
        .get(`/hotPlaceBoard/comment/findByArticleNo/${articleNo}`)
        .then((response) => {
          console.log(response);
          this.comments = response.data;
          console.log(this.comments);

          for (var i = 0; i < this.comments.length; i++) {
            // Vue.js의 반응형 시스템은 기본적으로 객체의 속성을 추적하고 변화를 감지합니다.
            // 그러나 이미 생성된 객체에 새로운 속성을 동적으로 추가하면 Vue는 그 변화를 자동으로 감지하지 못합니다.
            // 이러한 경우, Vue.set() 또는 this.$set() 메소드를 사용하여 객체에 반응형 속성을 추가해야 합니다.
            this.$set(this.comments[i], "editing", false); // 'editing' 속성 추가
            this.$set(this.comments[i], "isLike", false); // 'like' 속성 추가
          }
        })
        .catch((error) => {
          alert(error.response.data.message);
        });
    },
    modifyComment(comment) {
      comment.editing = !comment.editing;
      if (!comment.editing) {
        // editing이 false라면, 즉 수정이 완료되었다면
        this.saveChanges(comment);
      }
    },
    saveChanges(comment) {
      if (this.$store.state.token == null) {
        alert("로그인 하세요.");
        this.$router.push({ name: "login" });
      } else {
        http
          .post(`/hotPlaceBoard/comment/modify/${comment.commentId}`, comment, {
            headers: {
              "Access-Token": this.$store.state.token.data.accessToken,
            },
          })
          .then((response) => {
            if (response.status == 200) alert("댓글이 수정되었습니다.");
            window.location.reload();
          })
          .catch((error) => {
            alert(error.response.data.message);
            window.location.reload();
          });
      }
    },
    deleteComment(comment) {
      if (this.$store.state.token == null) {
        alert("로그인 하세요.");
        this.$router.push({ name: "login" });
      } else {
        const confirmMessage = "수정하시겠습니까?";
        if (confirm(confirmMessage)) {
          http
            .get(`/hotPlaceBoard/comment/delete/${comment.commentId}`, {
              headers: {
                "Access-Token": this.$store.state.token.data.accessToken,
              },
            })
            .then((response) => {
              if (response.status == 200) alert("댓글이 삭제되었습니다.");
              window.location.reload();
            })
            .catch((error) => {
              alert(error.response.data.message);
              window.location.reload();
            });
        }
      }
    },
    toggleLike(comment) {
      if (this.$store.state.token == null) {
        alert("로그인 하세요.");
        this.$router.push({ name: "login" });
      } else {
        http
          .get(`hotPlaceBoard/comment/like/${comment.commentId}`, {
            headers: {
              "Access-Token": this.$store.state.token.data.accessToken,
            },
          })
          .then((response) => {
            if (!comment.isLike && response.data == true) {
              // 좋아요 눌렀는데 처음 좋아요 누른 경우
              comment.isLike = !comment.isLike;
              comment.likes += 1;
            } else if (!comment.isLike && response.data == false) {
              // 좋아요 눌렀는데 두번째 누른 경우
              alert("이미 좋아요 한 게시물입니다. 좋아요를 취소합니다.");
              // comment.isLike = !comment.isLike;
              comment.likes -= 1;
            } else if (comment.isLike && response.data == true) {
              // 위에 오류 떴다가 취소 누른 경우
              comment.isLike = !comment.isLike;
              // comment.likes -= 1;
            } else {
              // 그냥 취소하는 경우
              comment.isLike = !comment.isLike;
              comment.likes -= 1;
            }
          })
          .catch((error) => {
            alert(error.response.data.message);
          });
      }
    },
  },
};
</script>

<style scoped>
.btn-like {
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.25rem 0.75rem;
  border-radius: 9999px;
  background-color: #fff;
  color: #333;
  font-weight: bold;
  font-size: 0.9rem;
  border: 1px solid #ddd;
  cursor: pointer;
}

.btn-like i {
  font-size: 1rem;
  color: #333;
}

.btn-like:hover {
  background-color: #f5f5f5;
}

.btn-like:hover i {
  color: #ff5a5f;
}

.btn-like span {
  white-space: nowrap;
}
.btn-primary {
  background-color: #53dae4e1;
  color: #fff;
  border-color: #53dae4e1;
}

.btn-blue {
  background-color: #007bff;
  color: #fff;
  border-color: #007bff;
}
@keyframes bounceHeart {
  0% {
    transform: translateX(0) scale(1);
  }
  30% {
    transform: translateX(0) scale(0.9);
  }
  60% {
    transform: translateX(0) scale(1.1);
  }
  100% {
    transform: translateX(0) scale(1);
  }
}

.heart-animation {
  color: red;
  animation: bounceHeart 1s ease-in-out infinite;
}
</style>
