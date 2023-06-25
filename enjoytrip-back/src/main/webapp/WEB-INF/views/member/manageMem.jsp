<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ include file="../common/header.jsp" %>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
      <link rel="stylesheet" href="${root}\css\styles.css" />
      <div style="margin-top:6rem;"><p></p></div>
      <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 shadow-sm bg-light text-center">
            <mark class="sky">유저 목록</mark>
          </h2>
        </div>
        <div class="col-lg-8 col-md-10 col-sm-12">
          <div class="row align-self-center mb-2">
            <div class="col-md-2 text-start">
            </div>
            <div class="col-md-7 offset-3">
            </div>
          </div>
          <table class="table table-hover">
            <thead>
              <tr class="text-center">
                <th scope="col">아이디</th>
                <th scope="col">이름</th>
                <th scope="col">이메일</th>
                <th scope="col">가입일자</th>
                <th scope="col">등급</th>
                <th scope="col">관리</th>
              </tr>
            </thead>
            <tbody>    
				<c:forEach var="member" items="${members}">    
	              <tr class="text-center">
	                <th scope="row">${member.userId}</th>
	                <th scope="row">${member.userName}</th>
	                <td>${member.emailId} @ ${member.emailDomain}</td>
	                <td>${member.joinDate}</td>
	                <td>${member.userClass}</td>
                  <td class="d-flex justify-content-center">
                  <button class="modifyBtn" href="#" data-no="${member.userId}">수정</button>
                  <button class="deleteBtn" href="#" data-no="${member.userId}">삭제</button>
                  </td>

	              </tr>            
				</c:forEach>   
            </tbody>
          </table>
        </div>
      </div>
    </div>
   <script>
   	let modifyBtns = document.querySelectorAll(".modifyBtn");
    modifyBtns.forEach(function (modifyBtn){
    	modifyBtn.addEventListener("click",function(){
    		location.href="${root}/modifyMem/" + this.getAttribute("data-no");
    	});
    });
    
    let deleteBtns = document.querySelectorAll(".deleteBtn");
    deleteBtns.forEach(function (deleteBtn){
    	deleteBtn.addEventListener("click",function(){
          location.href="${root}/deleteMem/" + this.getAttribute("data-no");
    	});
    });

   </script>
<%@ include file="../common/footer.jsp" %>