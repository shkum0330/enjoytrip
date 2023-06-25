<template>
  <div class="hotplaceboardlist">
    <!-- list start -->
    <div class="row justify-content-center">
      <div class="col-lg-8 col-md-10 col-sm-12" style="margin-bottom: 50px">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark>글 목록</mark>
        </h2>
      </div>
      <div class="col-lg-8 col-md-10 col-sm-12">
        <div class="row align-self-center mb-2">
          <div class="col-md-2 text-start">
            <div v-if="isLogin">
              <button
                type="button"
                id="btn-mv-register"
                class="btn btn-outline-primary btn-sm"
                @click="write()"
              >
                글쓰기
              </button>
            </div>
            <div v-if="!isLogin">
              <button
                type="button"
                id="btn-mv-register"
                class="btn btn-outline-primary btn-sm"
                @click="login()"
              >
                글쓰기
              </button>
            </div>
          </div>
          <div class="col-md-7 offset-3">
            <form class="d-flex" id="searchForm" @submit.prevent="submitSearchForm">
              <input type="hidden" name="action" value="list" />
              <input type="hidden" name="pgno" value="1" />
              <select
                name="key"
                id="key"
                class="form-select form-select-sm ms-5 me-1 w-50"
                aria-label="검색조건"
                v-model="selectedOption"
              >
                <option id="opt_subject" selected value="#">검색조건</option>
                <option id="opt_subject" value="subject">제목</option>
                <option id="opt_article_no" value="content">내용</option>
                <option id="opt_userid" value="writer">작성자</option>
              </select>
              <div class="input-group input-group-sm">
                <input
                  type="text"
                  name="word"
                  id="word"
                  class="form-control"
                  placeholder="검색어..."
                  v-model="searchKeyword"
                  required
                />
                <button type="submit" id="btn-search" class="btn btn-search">검색</button>
              </div>
            </form>
          </div>
        </div>
        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col">글번호</th>
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">조회수</th>
              <th scope="col">작성일</th>
            </tr>
          </thead>
          <tbody v-if="articles.length != 0">
            <tr class="text-center" v-for="article in articles" :key="article.articleNo">
              <th scope="row">{{ article.articleNo }}</th>
              <td class="text-start">
                <router-link
                  :to="{
                    name: 'view',
                    params: { articleNo: article.articleNo },
                  }"
                  class="article-title link-dark"
                  :data-no="article.articleNo"
                  style="text-decoration: underline; cursor: pointer"
                >
                  {{ article.subject }}
                </router-link>
              </td>
              <td>{{ article.userId }}</td>
              <td>{{ article.hit }}</td>
              <td>{{ article.registerTime }}</td>
            </tr>
          </tbody>
          <tr v-if="articles.length === 0" class="text-center">
            <td colspan="5">게시글이 없습니다.</td>
          </tr>
        </table>
        <div class="d-flex justify-content-center mt-4">
          <b-pagination
            v-model="curPage"
            :total-rows="totalRows"
            :per-page="perPage"
            :emit-immediately="true"
            @input="pageChanged"
            size="lg"
          ></b-pagination>
        </div>
      </div>
    </div>
    <!-- list end -->
  </div>
</template>

<script>
import http from "@/api/http.js";
import { mapGetters } from "vuex";

export default {
  name: "HotPlaceBoardList",
  components: {},
  data() {
    return {
      articles: [],
      allArticles: [], // 전체 데이터를 저장할 배열
      key: "",
      word: "",
      curPage: 1,
      perPage: 5,
      totalRows: 0,
      selectedOption: "#",
      searchKeyword: "",
    };
  },
  computed: {
    ...mapGetters(["isLogin"]),
  },
  mounted() {
    this.initBoardList();
  },
  methods: {
    initBoardList() {
      http
        .get(`/hotPlaceBoard/list`)
        .then((response) => {
          this.allArticles = response.data; // 전체 데이터를 저장
          this.allArticles.reverse(); // 전체 데이터를 역순으로 정렬
          this.totalRows = this.allArticles.length; // 전체 데이터의 개수를 totalRows에 설정
          this.updateArticles(); // 현재 페이지에 맞는 아이템들만 표시
        })
        .catch((error) => {
          console.log(error.response.data.message);
        });
    },
    submitSearchForm() {
      if (this.selectedOption == "#") alert("검색 조건을 설정하세요.");
      else {
        if (this.selectedOption == "subject") {
          http
            .get(`hotPlaceBoard/list/findBySubject?subject=${this.searchKeyword}`)
            .then((response) => {
              if (response.status == 200) this.allArticles = response.data; // 전체 데이터를 저장
              this.allArticles.reverse(); // 전체 데이터를 역순으로 정렬
              this.totalRows = this.allArticles.length; // 전체 데이터의 개수를 totalRows에 설정
              this.updateArticles(); // 현재 페이지에 맞는 아이템들만 표시
            })
            .catch((error) => {
              alert(error.response.data.message);
            });
        }
        if (this.selectedOption == "content") {
          http
            .get(`hotPlaceBoard/list/findByContent?content=${this.searchKeyword}`)
            .then((response) => {
              if (response.status == 200) this.allArticles = response.data; // 전체 데이터를 저장
              this.allArticles.reverse(); // 전체 데이터를 역순으로 정렬
              this.totalRows = this.allArticles.length; // 전체 데이터의 개수를 totalRows에 설정
              this.updateArticles(); // 현재 페이지에 맞는 아이템들만 표시
            })
            .catch((error) => {
              alert(error.response.data.message);
            });
        }
        if (this.selectedOption == "writer") {
          http
            .get(`hotPlaceBoard/list/findByWriter?userId=${this.searchKeyword}`)
            .then((response) => {
              if (response.status == 200) this.allArticles = response.data; // 전체 데이터를 저장
              this.allArticles.reverse(); // 전체 데이터를 역순으로 정렬
              this.totalRows = this.allArticles.length; // 전체 데이터의 개수를 totalRows에 설정
              this.updateArticles(); // 현재 페이지에 맞는 아이템들만 표시
            })
            .catch((error) => {
              alert(error.response.data.message);
            });
        }
      }
    },

    pageChanged() {
      this.updateArticles();
    },
    updateArticles() {
      let start = (this.curPage - 1) * this.perPage;
      let end = start + this.perPage;
      this.articles = this.allArticles.slice(start, end); // 현재 페이지에 맞는 아이템들만 표시
    },
    login() {
      alert("로그인 후 이용하세요. ");
      this.$router.push({ name: "login" });
    },
    write() {
      this.$router.push({ name: "write" });
    },
    detail(articleNo) {
      http
        .get(`hotPlaceBoard/${articleNo}/findById`)
        .then((response) => {
          alert(response);
          // console.log(response);
        })
        .catch((error) => {
          alert(error.response.data.message);
        });
      // view로 이동한 후에 스크롤을 맨 위로 이동
      this.$router.push({
        name: "view",
        params: { articleNo: articleNo },
      });
    },
  },
};
</script>
