<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ include file="/WEB-INF/views/common/confirm.jsp"%>
<div class="row justify-content-center">
	<div class="col-lg-8 col-md-10 col-sm-12">
		<h2 class="my-3 py-3 shadow-sm bg-light text-center">
			<mark class="sky">영화 수정</mark>
		</h2>
	</div>
	<div class="col-lg-8 col-md-10 col-sm-12">
		<form id="form-modify" method="POST" action="">
			<input type="hidden" name="id" value="${movie.id}">
			<div class="mb-3">
				<label for="title" class="form-label">제목 : </label> <input
					type="text" class="form-control" id="title" name="title"
					value="${movie.title}" />
			</div>
			<div class="mb-3">
				<label for="director" class="form-label">감독 : </label> <input
					class="form-control" id="director" name="director"
					value="${movie.director}" />
			</div>
			<div class="mb-3">
				<label for="genre" class="form-label">장르 : </label> <input
					class="form-control" id="genre" name="genre" value="${movie.genre}" />
			</div>
			<div class="mb-3">
				<label for="runningTime" class="form-label">상영시간 : </label> <input
					class="form-control" id="runningTime" name="runningTime"
					value="${movie.runningTime}" />
			</div>
			<div class="col-auto text-center">
				<button type="button" id="btn-modify"
					class="btn btn-outline-primary mb-3">글수정</button>
				<button type="button" id="btn-list"
					class="btn btn-outline-danger mb-3">목록으로이동...</button>
			</div>
		</form>
	</div>
</div>
</div>
<form id="form-param" method="get" action=""></form>
<script>
	document.querySelector("#btn-modify").addEventListener("click", function() {
		if (!document.querySelector("#title").value) {
			alert("제목 입력!!");
			return;
		} else if (!document.querySelector("#director").value) {
			alert("감독 입력!!");
			return;
		} else {
			let form = document.querySelector("#form-modify");
			form.setAttribute("action", "${root}/movie/${movie.id}/modify");
			form.submit();
		}
	});

	document.querySelector("#btn-list").addEventListener("click", function() {
		if (confirm("취소를 하시면 작성중인 글은 삭제됩니다.\n취소하시겠습니까?")) {
			let form = document.querySelector("#form-param");
			form.setAttribute("action", "${root}/movie/list");
			form.submit();
		}
	});
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>
