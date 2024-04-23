<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ include file="/WEB-INF/views/common/confirm.jsp"%>
<div class="row justify-content-center">
	<div class="col-lg-8 col-md-10 col-sm-12">
		<h2 class="my-3 py-3 shadow-sm bg-light text-center">
			<mark class="sky">영화 목록</mark>
		</h2>
	</div>
	<div class="col-lg-8 col-md-10 col-sm-12">
		<div class="row align-self-center mb-2">
			<div class="col-md-2 text-start">
				<button type="button" id="btn-mv-register"
					class="btn btn-outline-primary btn-sm">영화 등록</button>
			</div>
		</div>
		<table class="table table-hover">
			<thead>
				<tr class="text-center">
					<th scope="col">ID</th>
					<th scope="col">제목</th>
					<th scope="col">디렉터</th>
					<th scope="col">장르</th>
					<th scope="col">상영 시간</th>
					<th scope="col">포스터</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="movie" items="${movies}">
					<tr class="text-center">
						<th scope="row">${movie.id}</th>
						<td class="text-start"><a href="#"
							class="article-title link-dark" data-no="${movie.id}"
							style="text-decoration: none"> ${movie.title} </a></td>
						<td>${movie.director}</td>
						<td>${movie.genre}</td>
						<td>${movie.runningTime}분</td>
						<td><img alt="이미지1" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="row">${navigation.navigator}</div>
</div>
<form id="form-param" method="get" action=""></form>
<form id="form-no-param" method="get" action="${root}/article/view">
</form>
<script>
	let titles = document.querySelectorAll(".article-title");
	titles.forEach(function(title) {
		title.addEventListener("click", function() {
			const form = document.querySelector("#form-no-param");
			form.setAttribute("action", "${root}/movie/"
					+ this.getAttribute("data-no"));
			form.submit();
		});
	});

	document.querySelector("#btn-mv-register").addEventListener("click",
			function() {
				let form = document.querySelector("#form-param");
				form.setAttribute("action", "${root}/movie/write");
				form.submit();
			});
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>
