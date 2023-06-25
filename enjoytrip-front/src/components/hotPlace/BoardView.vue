<template>
  <div class="hotplaceboardview">
    <!-- view start -->
    <div class="row justify-content-center">
      <div class="col-lg-8 col-md-10 col-sm-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글 정보</mark>
        </h2>
        <div class="d-flex align-items-center justify-content-end mb-4">
          <button
            type="button"
            class="btn btn-secondary text-white"
            @click="goToList()"
          >
            목록으로
          </button>
        </div>
      </div>
      <div class="col-lg-8 col-md-10 col-sm-12 card mt-4 p-4">
        <form
          id="updateForm"
          enctype="multipart/form-data"
          @submit.prevent="submitUpdateForm"
        >
          <div class="form-group">
            <label for="userId" class="form-label">작성자</label>
            <input
              v-if="!isDisabled"
              type="text"
              class="form-control"
              id="userId"
              v-model="article.userId"
              readonly
            />
            <p v-else class="form-control-plaintext">
              {{ article.userId }}
            </p>
          </div>

          <div class="form-group">
            <label for="subject" class="form-label">제목</label>
            <input
              v-if="!isDisabled"
              type="text"
              class="form-control"
              id="subject"
              v-model="article.subject"
              readonly
            />
            <p v-else class="form-control-plaintext">
              {{ article.subject }}
            </p>
          </div>

          <div class="form-group">
            <label for="content" class="form-label">내용</label>
            <textarea
              v-if="!isDisabled"
              class="form-control"
              id="content"
              v-model="article.content"
              style="height: 100%"
              rows="10"
              required
            ></textarea>
            <p v-else class="form-control-plaintext">
              {{ article.content }}
            </p>
          </div>

          <!-- 파일 다운로드 -->
          <div
            v-if="updatedFiles && updatedFiles.length > 0 && isDisabled"
            class="mb-4"
          >
            <h5 class="form-label">첨부 파일</h5>
            <ul>
              <li
                v-for="(updatedFile, index) in updatedFiles"
                :key="updatedFile.saveFile"
              >
                <div v-if="isDisabled && !updatedFile.isDeleted">
                  <button
                    class="btn btn-link text-decoration-none"
                    @click.stop.prevent="downloadFile(updatedFile)"
                  >
                    <img src="@/assets/img/logos/download.svg" class="mr-1" />
                    파일 {{ index + 1 }}: {{ updatedFile.originalFile }}
                  </button>
                </div>
              </li>
            </ul>
          </div>

          <!-- 파일 추가 -->
          <div v-if="!isDisabled" class="mb-4">
            <h5>파일 추가</h5>
            <input
              type="file"
              class="form-control-file"
              ref="fileInput"
              @change="handleFileUpdate"
              multiple
            />
          </div>

          <!-- buttons -->
          <div v-if="isDisabled" class="d-flex justify-content-end mt-3">
            <button
              type="button"
              class="btn btn-danger mr-2"
              @click.stop.prevent="del(article.articleNo)"
            >
              삭제하기
            </button>
            <button
              type="button"
              class="btn btn-primary"
              @click.stop.prevent="modify()"
            >
              수정하기
            </button>
          </div>

          <div v-else class="d-flex justify-content-end mt-3">
            <button
              type="button"
              class="btn btn-danger mr-2"
              @click.stop.prevent="cancel()"
            >
              취소
            </button>
            <button
              type="button"
              class="btn btn-secondary mr-2"
              @click.stop.prevent="reset()"
            >
              내용 지우기
            </button>
            <button
              type="button"
              class="btn btn-primary"
              @click="submitUpdateForm"
            >
              확인
            </button>
          </div>
        </form>
      </div>
    </div>
    <!-- view end -->
  </div>
</template>

<script>
import http from "@/api/http.js";

export default {
  name: "BoardView",
  components: {},
  data() {
    return {
      article: {},
      isDisabled: true,
      articleNo: "",
      updatedFiles: [],
    };
  },
  beforeRouteEnter(to, from, next) {
    const articleNo = to.params.articleNo;
    next((vm) => {
      vm.initArticleInfo(articleNo);
    });
  },
  beforeRouteUpdate(to, from, next) {
    const articleNo = to.params.articleNo;
    this.initArticleInfo(articleNo);
    next();
  },
  mounted() {
    this.scrollToTop();
  },

  methods: {
    scrollToTop() {
      // 페이지 맨 위로 스크롤 이동
      window.scrollTo({
        top: 300,
        behavior: "smooth",
      });
    },
    reset() {
      this.article = {};
      this.updatedFiles = [];
    },
    goToList() {
      this.$router.push({ name: "list" });
    },
    modify() {
      const confirmMessage = "수정하시겠습니까?";
      if (confirm(confirmMessage)) {
        if (this.$store.state.token == null) {
          alert("로그인 하세요.");
          this.$router.push({ name: "login" });
        } else {
          http
            .get("/member/details", {
              headers: {
                "Access-Token": this.$store.state.token.data.accessToken,
              },
            })
            .then((response) => {
              if (response.data.userId == this.article.userId) {
                this.isDisabled = false;
              } else {
                alert("회원 정보가 일치하지 않습니다.");
                this.initArticleInfo(this.articleNo);
              }
            })
            .catch((error) => {
              alert(error);
            });
        }
      }
    },
    cancel() {
      this.isDisabled = true;
      this.initArticleInfo(this.articleNo);
      console.log(this.articleNo);
    },
    del(articleNo) {
      const confirmMessage = "정말로 게시글을 삭제하시겠습니까?";
      if (confirm(confirmMessage)) {
        if (this.$store.state.token == null) {
          alert("로그인 하세요.");
          this.$router.push({ name: "login" });
        } else {
          http
            .get(`/hotPlaceBoard/delete/${articleNo}`, {
              headers: {
                "Access-Token": this.$store.state.token.data.accessToken,
              },
            })
            .then((response) => {
              alert(response.data);
              this.$router.push({ name: "list" });
            })
            .catch((error) => {
              alert(error.response.data.message);
              // alert(error);
            });
        }
      }
    },
    initArticleInfo(articleNo) {
      this.articleNo = articleNo;
      http
        .get(`/hotPlaceBoard/view/${articleNo}`)
        .then((response) => {
          this.article = response.data;
          this.updatedFiles = response.data.fileInfos;
          for (let i = 0; i < this.updatedFiles.length; i++) {
            this.updatedFiles[i]["isDeleted"] = false;
            // console.log(this.updatedFiles[i]);
          }
        })
        .catch((error) => {
          alert(error);
        });
    },
    submitUpdateForm() {
      const formData = new FormData();
      const boardDto = {
        subject: this.article.subject,
        content: this.article.content,
      };
      const boardDtoBlob = new Blob([JSON.stringify(boardDto)], {
        type: "application/json",
      });
      formData.append("boardDto", boardDtoBlob);

      // for (let i = 0; i < this.updatedFiles.length; i++) {
      //   if (!this.updatedFiles[i].isDeleted) formData.append("files", this.updatedFiles[i]);
      // }
      http
        .post(`hotPlaceBoard/modify/${this.articleNo}`, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            "Access-Token": this.$store.state.token.data.accessToken,
          },
        })
        .then((response) => {
          if (response.status == 200) alert("글 수정에 성공했습니다.");
        })
        .catch((error) => {
          alert(error.response.data.message);
        });
      this.isDisabled = true;
    },
    downloadFile(fileInfo) {
      console.log(fileInfo);
      const downloadUrl = `/hotPlaceFile/download/${fileInfo.saveFolder}/${fileInfo.originalFile}/${fileInfo.saveFile}`;
      const fileName = fileInfo.originalFile;
      http
        .get(downloadUrl, { responseType: "blob" })
        .then((response) => {
          const blob = new Blob([response.data]);
          const url = window.URL.createObjectURL(blob);
          const link = document.createElement("a");
          link.href = url;
          link.download = fileName;
          link.click();
          window.URL.revokeObjectURL(url);
        })
        .catch((error) => {
          console.error("파일 다운로드 중 오류가 발생했습니다.", error);
        });
    },
    handleFileUpdate(e) {
      this.updatedFiles = Array.from(e.target.files);
      this.updatedFiles.push(...e.target.files);
      this.updatedFiles[this.updatedFiles.length - 1]["isDeleted"] = false;
    },

    deleteFile(index) {
      const confirmMessage = "정말 삭제하시겠습니까?";
      if (confirm(confirmMessage)) this.updatedFiles[index].isDeleted = true;
    },
  },
};
</script>

<style scoped>
.form-label {
  font-weight: bold;
  color: #000000;
  font-size: 1.2rem;
  margin-bottom: 0.5rem;
}

.form-control {
  border: none;
  border-radius: 0;
  box-shadow: none;
  border-bottom: 1px solid #ced4da;
  font-size: 1rem;
  padding: 0.375rem 0.75rem;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.form-control:focus {
  border-color: rgb(255, 252, 204);
  outline: 0;
  box-shadow: 0 0 0 0.2rem rgb(255, 252, 204);
}

.form-control-plaintext {
  margin-left: 10px;
  white-space: pre-wrap;
  background-color: rgba(231, 231, 220, 0.5);
  border-radius: 10px;
  width: 30%;
  border: #000;
  font-weight: bold;
  padding: 0;
}

.form-group {
  margin-bottom: 2rem;
}

.btn {
  color: #000;
}

.btn-warning {
  background-color: #ffd700;
  border: none;
}

.btn-warning:hover {
  background-color: #ffc107;
}

.download-img {
  /* background-image: url("@/assets/img/logos/download.svg"); */
  list-style-type: none;
  list-style: none;
}
</style>
