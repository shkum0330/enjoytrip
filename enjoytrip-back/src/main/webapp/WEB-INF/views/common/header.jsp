<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<c:set var="root" value="${pageContext.request.contextPath}"></c:set>
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js"
	crossorigin="anonymous"></script>
<link rel="stylesheet" href="${root}\css\styles.css" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
</head>
<body id="page-top">

	<!-- session으로 전달된 메시지를 한 번 출력 후 삭제 -->
	<c:if test="${msg ne null }">
		<script>
			alert("${msg}");
		</script>
		<c:set var="msg" value="${null}"></c:set>
	</c:if>

	<!-- Navigation-->
	<nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
		<div class="container">
			<a class="navbar-brand" href="${root}/"> <img
				src="${root}/img/logos/ssafy_logo.png" class="p-0 w-100"
				alt="..." />
			</a>
			<script>
				console.log("${root}/");
			</script>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarResponsive"
				aria-controls="navbarResponsive" aria-expanded="false"
				aria-label="Toggle navigation">
				Menu <i class="fas fa-bars ms-1"></i>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
					<li class="nav-item"><a class="nav-link"
						href="${root}/attraction/search-region">지역별여행지</a></li>
					<li class="nav-item"><a class="nav-link"
											href="${root}/myPlanBoard/list?pgno=1&key=&word=">나의여행계획</a></li>
					<li class="nav-item"><a class="nav-link"
											href="${root}/hotPlaceBoard/list?pgno=1&key=&word=">핫플자랑하기</a></li>
					<li class="nav-item"><a class="nav-link"
											href="${root}/tripInfoBoard/list?pgno=1&key=&word=">여행정보공유</a></li>

					<c:if test="${login eq null }">
						<script>
							console.log("1");
						</script>
						<li class="nav-item dropdown" id="navNonLogIn"><a
							class="btn dropdown-toggle nav-link text-start"
							data-bs-toggle="dropdown" aria-expanded="false"> 로그인/회원가입 </a>
							<div class="dropdown-menu dropdown-menu-end " role="menu">
								<a id="navLogInBtn" class="dropdown-item" href="#"
									data-bs-toggle="modal" data-bs-target="#logInModal"> 로그인 </a> <a
									id="navRegBtn" class="dropdown-item " href="#"
									data-bs-toggle="modal" data-bs-target="#registModal"> 회원가입
								</a> <a id="navFindPassBtn" class="dropdown-item" href="#"
									data-bs-toggle="modal" data-bs-target="#findPassModal">
									비밀번호 변경 </a>
							</div></li>
					</c:if>
					<c:if test="${login ne null }">
						<c:if test="${login.userClass eq 0}">
							<script>
								console.log("2");
							</script>
							<li class="nav-item dropdown" id="navMemLogIn"><a
								class="btn dropdown-toggle nav-link text-start"
								data-bs-toggle="dropdown" aria-expanded="false"> 마이페이지/로그아웃
							</a>
								<div class="dropdown-menu dropdown-menu-end " role="menu">
									<a id="myPageBtn" class="dropdown-item" href="board.jsp">
										마이페이지 </a> <a class="dropdown-item navLogOutBtn pointer-event"
										href="${root}/logout"> 로그아웃 </a>
								</div></li>
						</c:if>
						<c:if test="${login.userClass eq 1}">
							<script>
								console.log("3");
							</script>
							<li class="nav-item dropdown" id="navAdminLogIn"><a
								class="btn dropdown-toggle nav-link text-start"
								data-bs-toggle="dropdown" aria-expanded="false"> 회원관리/로그아웃 </a>
								<div class="dropdown-menu dropdown-menu-end " role="menu">
									<a id="manageMemBtn" class="dropdown-item"
										href="${root}/manageMem"> 회원관리 </a> <a
										class="dropdown-item navLogOutBtn pointer-event"
										href="${root}/logout"> 로그아웃 </a>
								</div></li>
						</c:if>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>

	<div class="modal" id="logInModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">로그인</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<form id="logInForm" action="${root}/login" method="post">
					<input type="hidden" id="actionLogIn" name="action" value="login">
					<div class="modal-body">
						<div class="mb-3">
							<label for="username" class="form-label">아이디</label>
							<input type="text" class="form-control" name="userId" id="useridLogIn" placeholder="아이디를 입력하세요">
						</div>
						<div class="mb-3">
							<label for="password" class="form-label">비밀번호</label>
							<input type="password" class="form-control" name="userPassword" id="userpwdLogIn" placeholder="비밀번호를 입력하세요">
						</div>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal"
								onclick="document.getElementById('logInForm').reset();">취소</button>
						<button type="submit" id="logInBtn" class="btn btn-outline-primary" data-bs-dismiss="modal">로그인</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<div class="modal" id="findPassModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">비밀번호 찾기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<form id="findPassForm" action="member" method="post">
					<input type="hidden" id="action" name="action" value="findPass">
					<div class="modal-body">
						<div class="mb-3">
							<label for="text" class="form-label">ID 입력</label>
							<input type="text" class="form-control" name="userId" id="userId" placeholder="아이디를 입력하세요">
						</div>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal"
								onclick="document.getElementById('findPassForm').reset();">취소</button>
						<button type="submit" id="findPassBtn" class="btn btn-outline-primary" data-bs-dismiss="modal">비밀번호 찾기</button>
					</div>
				</form>
			</div>
		</div>
	</div>


	<div class="modal" id="registModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">회원가입</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<form id="RegistForm" action="join" method="post">
					<input type="hidden" id="actionRegist" name="action" value="join">

					<div class="modal-body">
						<div class="mb-3">
							<label for="username" class="form-label">이름</label>
							<input type="text" class="form-control" id="usernameRegist" name ="userName" placeholder="이름">
						</div>
						<div class="mb-3">
							<label for="username" class="form-label">아이디</label>
							<input type="text" class="form-control" id="useridRegist" name="userId" placeholder="아이디">
						</div>
						<div class="mb-3">
							<label for="password" class="form-label">비밀번호</label>
							<input type="password" class="form-control" id="userpwdRegist" name="userPassword" placeholder="비밀번호">
						</div>
						<div class="mb-3">
							<label for="password" class="form-label">비밀번호 확인</label>
							<input type="password" class="form-control" id="passwordChk2Regist" name="userPasswordChk2" placeholder="비밀번호 확인">
						</div>
						<div class="mb-3">
							<label for="password" class="form-label">이메일</label>
							<div class="d-flex justify-content-between">
								<input style="width:40%" type="text" class="form-control" id="emailidRegist" name="emailId" placeholder="이메일">
								<p class="text-center">@</p>
								<select style="width:50%" class="form-select" id = "emaildomainRegist" name="emailDomain">
									<option value="naver.com">
										naver.com
									</option>
									<option value="google.com">
										google.com
									</option>
								</select>
							</div>

						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal"
								onclick="document.getElementById('registForm').reset();">취소</button>
						<button type="submit" class="btn btn-outline-primary" data-bs-dismiss="modal"
								onclick="document.getElementById('registForm').reset();">회원가입</button>
					</div>
				</form>
			</div>
		</div>
	</div>