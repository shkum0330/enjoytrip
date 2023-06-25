import Vue from "vue";
import VueRouter from "vue-router";

//home
import HomeView from "@/views/HomeView.vue";

//member & admin
import MemberView from "@/views/member/MemberView.vue";
import MemberLogin from "@/components/member/MemberLogin.vue";
import MemberRegist from "@/components/member/MemberRegist.vue";
import MemberDetail from "@/components/member/MemberDetail.vue";
import AdminView from "@/views/member/AdminView.vue";
import MemberFindPassword from "@/components/member/MemberFindPassword.vue";

//attraction
import AttractionView from "@/views/attraction/AttractionView.vue";

//hotplaceboard
import HotPlaceBoardView from "@/views/hotPlace/HotPlaceBoardView.vue";
import HotPlaceBoardList from "@/components/hotPlace/HotPlaceBoardList.vue";
import HotPlaceBoardWrite from "@/components/hotPlace/HotPlaceBoardWrite.vue";
import BoardView from "@/components/hotPlace/BoardView.vue";

//Comment
import HotPlaceCommentList from "@/components/hotPlace/HotPlaceCommentList.vue"
import HotPlaceComment from "@/components/hotPlace/HotPlaceComment.vue"

Vue.use(VueRouter);

const routes = [
  // Home
  {
    path: "/",
    name: "home",
    component: HomeView,
  },

  // 관리자
  {
    path: "/admin",
    name: "admin",
    component: AdminView,
  },

  // User
  {
    path: "/member",
    name: "member",
    component: MemberView,
    children: [
      {
        path: "/member/login",
        name: "login",
        component: MemberLogin,
      },
      {
        path: "/member/regist",
        name: "regist",
        component: MemberRegist,
      },
      {
        path: "/member/detail",
        name: "detail",
        component: MemberDetail,
      },
      {
        path: "/member/findPass",
        name: "findPass",
        component: MemberFindPassword,
      },
    ],
  },

  // attraction
  {
    path: "/attraction/searchRegion",
    name: "searchRegion",
    component: AttractionView,
  },

  // hotPlaceBoard
  {
    path: "/hotPlaceBoard",
    name: "hotPlaceBoard",
    component: HotPlaceBoardView,
    children: [
      {
        path: "/list",
        name: "list",
        component: HotPlaceBoardList,
      },
      {
        path: "/write",
        name: "write",
        component: HotPlaceBoardWrite,
      },
      {
        path: "/view/:articleNo",
        name: "view",
        components: {
          default: BoardView,
          HotPlaceCommentList: HotPlaceCommentList,
          HotPlaceComment : HotPlaceComment
        }
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
