<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<c:if test="${movie eq null}">
	<script>
		alert("글이 삭제되었거나 부적절한 URL 접근입니다.");
		location.href = "${root}/movie/list";
	</script>
</c:if>
<%@ include file="/WEB-INF/views/common/confirm.jsp"%>
<div class="row justify-content-center">
	<div class="col-lg-8 col-md-10 col-sm-12">
		<h2 class="my-3 py-3 shadow-sm bg-light text-center">
			<mark class="sky">영화 보기</mark>
		</h2>
	</div>
	<div class="col-lg-8 col-md-10 col-sm-12">
		<div class="row my-2">
			<h2 class="text-secondary px-5">${movie.id}. ${movie.title}</h2>
		</div>
		<div class="row">
			<div class="col-md-8">
				<div class="clearfix align-content-center">
					<img class="avatar me-2 float-md-start bg-light p-2"
						src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg" />
					<p>
						<span class="fw-bold">${movie.director}</span> <br />
					</p>
				</div>
			</div>
			<div class="col-md-4 align-self-center text-end">상영시간 :
				${movie.runningTime}</div>
			<div class="divider mb-3"></div>
			<div class="text-secondary">
              ${movie.genre}
            </div>
			<div class="divider mt-3 mb-3"></div>
			<div class="d-flex justify-content-end">
				<button type="button" id="btn-list"
					class="btn btn-outline-primary mb-3">영화목록</button>
				<button type="button" id="btn-mv-modify"
					class="btn btn-outline-success mb-3 ms-1">영화 수정</button>
				<button type="button" id="btn-delete"
					class="btn btn-outline-danger mb-3 ms-1">영화 삭제</button>
				<form id="form-no-param" method="get" action="${root}/board">
				</form>
				<script>
					document.querySelector("#btn-mv-modify").addEventListener(
							"click",
							function() {
								let form = document
										.querySelector("#form-no-param");
								form.setAttribute("action",
										"${root}/movie/${movie.id}/modify");
								form.submit();
							});

					document.querySelector("#btn-delete").addEventListener(
							"click",
							function() {
								if (confirm("정말 삭제하시겠습니까?")) {
									let form = document
											.querySelector("#form-no-param");
									form.setAttribute("action",
											"${root}/movie/${movie.id}/delete");
									form.submit();
								}
							});
				</script>
			</div>
		</div>
	</div>
</div>
<form id="form-param" method="get" action=""></form>
<form id="downform" action="${root}/article/download"></form>
<script>
	document.querySelector("#btn-list").addEventListener("click", function() {
		let form = document.querySelector("#form-param");
		form.setAttribute("action", "${root}/movie/list");
		form.submit();
	});

	let files = document.querySelectorAll(".filedown");
	files.forEach(function(file) {
		file.addEventListener("click", function() {
			document.querySelector("[name='sfolder']").value = file
					.getAttribute("sfolder");
			document.querySelector("[name='ofile']").value = file
					.getAttribute("ofile");
			document.querySelector("[name='sfile']").value = file
					.getAttribute("sfile");
			document.querySelector("#downform").submit();
		});
	});
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>