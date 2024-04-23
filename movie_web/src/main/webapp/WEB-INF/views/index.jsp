<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:set var="root" value="${pageContext.request.contextPath}" />
<c:if test="${cookie.ssafy_id.value ne null}">
	<c:set var="idck" value=" checked" />
	<c:set var="saveid" value="${cookie.ssafy_id.value}" />
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>영화</title>
</head>
<body>
	<h1>영화 관리</h1>
	<hr>
	<c:if test="${user ne null}">
		<div class="row justify-content-center">
			<div class="col-lg-8 col-md-10 col-sm-12 m-3 text-end">
				<strong>${user.name}</strong> (${user.id})님 안녕하세요. <a
					href="${root}/member/logout">로그아웃</a><br />
			</div>
		</div>
		<a href="${root}/movie/list">영화 목록</a>
	</c:if>
	<c:if test="${user eq null}">
		<a href="${root}/member/login">로그인 하기</a>
	</c:if>
</body>
</html>