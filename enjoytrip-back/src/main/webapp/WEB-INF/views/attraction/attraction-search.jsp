<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>enjoy trip</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css"/>
    <!-- Font Awesome icons (free version)-->
    <link rel="stylesheet" href="../../../../resources/static/css/styles.css"/>
    <script
            src="https://use.fontawesome.com/releases/v6.1.0/js/all.js"
            crossorigin="anonymous"
    ></script>
    <!-- Core theme CSS (includes Bootstrap)-->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
    />
</head>

<body>
<!-- 중앙 content start -->
<div class="container" style="max-width : 90% !important">
    <div style="height: 70px"></div>
    <div class="alert alert-primary mt-3 text-center fw-bold" role="alert">
        전국 관광지 정보
    </div>
    <div class="row">
        <div class="col-md-7 p-md-5 align-items-center">

            <div class="row align-content-center">
                <!-- 관광지 검색 start -->
                <form class="d-flex my-3" onsubmit="return false;" role="search">
                    <select id="search-sido" class="form-select mx-1">
                        <option value="0" selected>시/도</option>
                    </select>
                    <select id="search-gugun" class="form-select mx-1">
                        <option value="0" selected>시/군/구</option>
                    </select>
                    <select id="search-content-id" class="form-select mx-1">
                        <option value="0" selected>관광지 유형 선택</option>
                        <option value="12">관광지</option>
                        <option value="14">문화시설</option>
                        <option value="15">축제공연행사</option>
                        <option value="25">여행코스</option>
                        <option value="28">레포츠</option>
                        <option value="32">숙박</option>
                        <option value="38">쇼핑</option>
                        <option value="39">음식점</option>
                    </select>
                    <%--                <input id="search-keyword" class="form-control me-2" type="search" placeholder="검색어"--%>
                    <%--                       aria-label="검색어"/>--%>
                    <button id="btn-search" class="btn btn-outline-success w-25 mx-1" type="button">검색</button>
                </form>
                <!-- 관광지 검색 end -->
            </div>

            <div class="row align-content-center">
                <!-- kakao map start -->
                <div id="map" style="width: 100%; height: 600px"></div>
                <!-- kakao map end -->
            </div>

        </div>

        <div class="col-md-5">
            <div class="row m-3">
                <table class="table table-striped" style="display: none">
                    <thead>
                    <tr>
                        <th>대표이미지</th>
                        <th>관광지명</th>
                        <th>주소</th>
                        <th>위도</th>
                        <th>경도</th>
                    </tr>
                    </thead>
                    <tbody id="trip-list"></tbody>
                </table>
            </div>
        </div>
    </div>

    <%@ include file="../common/footer.jsp" %>
    <script type="text/javascript" src='${root}\js\map.js?v=<%=System.currentTimeMillis()%>'>
    </script>
</div>

<div id="desc-modal">

</div>

</body>
</html>