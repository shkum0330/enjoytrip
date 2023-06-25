<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ include file="common/header.jsp" %>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
      <!-- Masthead-->
      <header class="masthead">
        <div class="container">
          <div class="masthead-subheading mastheadInner">여행을 떠나봐요</div>
          <div class="masthead-heading mastheadInner">다양한 여행지를 소개합니다.</div>
        </div>
      </header>

      <main class="mt-5">
        <!-- 관광지 소개 -->
        <div class="position-relative">
          <!-- 설명 -->
          <div>
            <div class="text-center fw-bold fs-2">여행을 떠나봐요</div>
            <div class="text-center fw-semibold fs-5">다양한 여행지를 소개합니다.</div>
          </div>
          <!-- 버튼을 위한 -->
          <div class="px-0 px-sm-1 px-md-3 px-lg-5 position-relative">
            <button class="btn btnScroll btnScrollLeftClass ms-0 ms-sm-1 ms-md-3 ms-lg-5 start-0 "
              id="btnScrollLeftAttr">left</button>
            <button class="btn btnScroll btnScrollRightClass me-0 me-sm-1 me-md-3 me-lg-5 end-0"
              id="btnScrollRightAttr">right</button>

            <!-- 카드 리스트 123-->
              <div class="mt-4">
                <ul class="d-flex list-unstyled scrollList" id="scrollListAttr">
                  <c:forEach var="attraction" items="${attractionList1}" varStatus="status">
                    <li class="card mainCard mainCardAttr" style="background-image: url('${attraction.image}');">
                      <a class=" w-100 h-100" href="#" data-bs-toggle="modal" data-bs-target="#attractionModal1${status.index}">
                        <div class="p-0 card-body">
                          <div class="mainCardAttrInner mainCardAttrTitle">
                              ${attraction.title}
                          </div>
                          <div class="mainCardAttrInner mainCardAttrContent">
                              ${attraction.title}
                          </div>
                          <button
                                  class="mainCardAttrInner btn btn-primary btn-xl mastheadInner border-1 border-dark mainCardAttrBtn">자세히
                            보기..</button>
                        </div>
                      </a>
                    </li>
                  </c:forEach>
                </ul>
              </div>
          </div>
        </div>
        <!-- 여행 계획 -->
        <div class="position-relative">
          <!-- 설명 -->
          <div>
            <div class="text-center fw-bold fs-2">나만의 여행지</div>
            <div class="text-center fw-semibold fs-5">다른 유저들의 여행지도 확인해보세요.</div>
          </div>
          <!-- 버튼을 위한 -->
          <div class="px-0 px-sm-1 px-md-3 px-lg-5 position-relative">
            <button class="btn btnScroll ms-0 ms-sm-1 ms-md-3 ms-lg-5 start-0" id="btnScrollLeftPlan">left</button>
            <button class="btn btnScroll me-0 me-sm-1 me-md-3 me-lg-5 end-0" id="btnScrollRightPlan">right</button>

            <!-- 카드 리스트 -->
              <div class="mt-4">
                <ul class="card-list d-flex list-unstyled scrollList" id="scrollListPlan">
                  <c:forEach var="attraction" items="${attractionList2}" varStatus="status">
                    <li class="card mainCard mainCardPlan">
                      <a class="" href="#" data-bs-toggle="modal" data-bs-target="#attractionModal2${status.index}">
                        <div class="card-body p-0 imgPlan">
                          <img class="img-fluid" src="${attraction.image}" alt=""/>
                        </div>
                        <div class="card-footer border-0">
                          <h6 class="h6 d-block text-secondary">
                              ${attraction.title}
                          </h6>
                          <h4 class="h4 d-block">
                              ${attraction.title}
                          </h4>
                        </div>
                      </a>
                    </li>
                  </c:forEach>
                </ul>
              </div>
          </div>
        </div>
        <!-- 핫플 -->
      </main>

    <c:forEach var="attraction" items="${attractionList1}" varStatus="status">
      <div class="modal" id="attractionModal1${status.index}" tabindex="-1">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">자세히 보기</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <img class="img-fluid" src="${attraction.image}">
              <h1>${attraction.title}</h1>
              <h6>주소</h6>
              <h6>${attraction.addr1}</h6>
              <h6>상세설명</h6>
              <h6>${attraction.description}</h6>
            </div>
          </div>
        </div>
      </div>
    </c:forEach>

    <c:forEach var="attraction" items="${attractionList2}" varStatus="status">
      <div class="modal" id="attractionModal2${status.index}" tabindex="-1">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">자세히 보기</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <img class="img-fluid" src="${attraction.image}">
              <h1>${attraction.title}</h1>
              <h6>주소</h6>
              <h6>${attraction.addr1}</h6>
              <h6>상세설명</h6>
              <h6>${attraction.description}</h6>
            </div>
          </div>
        </div>
      </div>
    </c:forEach>

<script type="text/javascript" src="${root}\js\scripts.js">
</script>
<%@ include file="common/footer.jsp" %>