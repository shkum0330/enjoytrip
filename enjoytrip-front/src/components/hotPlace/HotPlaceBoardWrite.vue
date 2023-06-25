<template>
  <div class="hotplaceboardwrite">
    <!-- write start -->
    <div class="row justify-content-center">
      <div class="col-lg-8 col-md-10 col-sm-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글쓰기</mark>
        </h2>
      </div>
      <div class="col-lg-8 col-md-10 col-sm-12">
        <form
          id="writeForm"
          enctype="multipart/form-data"
          @submit.prevent="submitWriteForm"
        >
          <input type="hidden" name="pgno" value="1" />
          <input type="hidden" name="key" value="" />
          <input type="hidden" name="word" value="" />
          <div class="mb-3">
            <label for="subject" class="form-label">제목 : </label>
            <input
              type="text"
              class="form-control"
              id="subject"
              v-model="boardDto.subject"
              placeholder="제목..."
              required
            />
          </div>
          <div class="mb-3">
            <label for="content" class="form-label">내용 : </label>
            <textarea
              class="form-control"
              id="content"
              v-model="boardDto.content"
              rows="7"
              required
            ></textarea>
          </div>
          <div class="mb-3">
            <label for="upfile" class="form-label">파일 : </label>
            <input
              type="file"
              class="form-control border"
              id="upfile"
              ref="fileInput"
              @change="handleFiles"
              multiple
            />
          </div>
          <div class="d-flex justify-content-end" style="margin-top: 10px">
            <button type="button" class="btn btn-danger" @click="goToList()">
              목록으로
            </button>
            <button type="button" class="btn btn-secondary" @click="reset()">
              내용 지우기
            </button>
            <button
              type="button"
              class="btn btn-primary"
              @click="submitWriteForm"
            >
              글 등록
            </button>
          </div>
        </form>
      </div>
    </div>
    <!-- write end -->
  </div>
</template>

<script>
import http from "@/api/http.js";

export default {
  name: "HotPlaceBoardWrite",
  components: {},
  data() {
    return {
      boardDto: {
        subject: "",
        content: "",
      },
      files: [],
    };
  },

  methods: {
    goToList() {
      this.$router.push({ name: "list" });
    },
    submitWriteForm() {
      //서버에 보낼 데이터 준비
      const formData = new FormData();
      const boardDtoBlob = new Blob([JSON.stringify(this.boardDto)], {
        type: "application/json",
      });
      formData.append("boardDto", boardDtoBlob);

      const fileInput = this.$refs.fileInput;
      for (let i = 0; i < fileInput.files.length; i++) {
        formData.append("files", fileInput.files[i]);
      }

      console.log(formData);
      http
        .post("/hotPlaceBoard/write", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            "Access-Token": this.$store.state.token.data.accessToken,
          },
        })
        .then(() => {
          alert("글 작성에 성공하였습니다.");
          this.$router.push({ name: "list" });
        })
        .catch((error) => {
          alert(error);
        });
    },
    reset() {
      this.boardInfo.boardDto.subject = "";
      this.boardInfo.boardDto.content = "";
    },

    handleFiles(e) {
      //선택된 파일들을 files 배열에 저장
      this.files = Array.from(e.target.files);
    },
  },
};
</script>
