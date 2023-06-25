<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ include file="../common/header.jsp" %>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
      <link rel="stylesheet" href="${root}\css\styles.css" />
      <div style="margin-top:6rem;"><p></p></div>
      <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 shadow-sm bg-light text-center">
            <mark class="sky">유저 정보 수정</mark>
          </h2>
        </div>
        <div class="col-lg-8 col-md-10 col-sm-12">
          <form id="form-modify" method="POST" action="">
          	<input type="hidden" name="action" value="modify">
            <input type="hidden" name="userId" value="${member.userId}">
            <div class="mb-3">
              <label for="content" class="form-label">이름 : </label>
              <input type="text" class="form-control" id="content" name="userName" value="${member.userName}" />
            </div>
            <div class="mb-3">
              <label for="subject" class="form-label">이메일 : </label>
              <div class="d-flex">
              <input type="text" class="form-control" id="subject1" name="emailId" value="${member.emailId}"/>
              @
              <input type="text" class="form-control" id="subject2" name="emailDomain" value="${member.emailDomain}"/>
              </div>
            </div>
            <div class="col-auto text-center">
              <button type="submit" id="btn-modify" class="btn btn-outline-primary mb-3" data-no="${member.userId}">
                회원정보 수정
              </button>
              <button type="button" id="btn-list" class="btn btn-outline-danger mb-3">
                목록으로이동...
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <script>
      document.querySelector("#btn-modify").addEventListener("click", function () {
        if (!document.querySelector("#subject1").value) {
          alert("이름 입력!!");
          return;
        } else if (!document.querySelector("#content").value) {
          alert("이메일 입력!!");
          return;
        } else {
          let form = document.querySelector("#form-modify");
          form.setAttribute("action", "${root}/modifyMem/"+this.getAttribute("data-no"));
 		  form.submit();
        }
      });
      document.querySelector("#btn-list").addEventListener("click", function () {
        location.href = "${root}/manageMem";
      });
    </script>
<%@ include file="../common/footer.jsp" %>
