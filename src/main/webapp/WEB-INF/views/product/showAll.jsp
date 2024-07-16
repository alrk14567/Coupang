<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!--태그라이브라는 애를 불러올 껀데 걔 이름이 c야-->
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>게시판</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>

</head>
<body>
<div class="container-fluid">
    <div class="main h-100">
        <div class="row justify-content-center">
            <div class="col-10 text-center">
                <div class="row justify-content-start mb-3">
                    <c:if test="${logIn.getUserGrade()==1}">
                        <div class="col-2">
                            <a class="btn btn-outline-success" href="/user/memberAll">회원 목록</a>
                        </div>
                    </c:if>
                    <c:if test="${logIn.getUserGrade()==2||logIn.getUserGrade()==1}">
                        <div class="col-3">
                            <a class="btn btn-outline-success" href="/product/write">상품 등록하기</a>
                        </div>
                    </c:if>
                    <div class="col-2">
                        <a class="btn btn-outline-danger" href="/user/logOut">로그 아웃</a>
                    </div>
                </div>

                <div class="row justify-content-center">
                    <form class="justify-content-center text-center" action="/product/showAll" method="post">
                        <div class="container-fluid">
                            <div class="row justify-content-center">
                                <div class="col-auto">
                                    <select name="categoryId" id="selectCategory" class="form-control form-control-sm" style="height: auto" required>
                                        <option value="">-</option>
                                        <c:forEach items="${categoryList}" var="category">
                                            <option value="${category.id}">${category.content}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-auto">
                                    <input type="text" style="height: 2px" name="inputNickname" class="form-control form-control-lg">
                                </div>
                                <div class="col-auto">
                                    <input type="submit" style="height: auto" value="상품 검색" class="btn btn-primary">
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="row row-cols-1 row-cols-md-5 g-4">
                    <c:forEach items="${list}" var="b" varStatus="status">
                        <c:if test="${status.index < 20}">
                            <div class="col">
                                <div class="card h-100" onclick="javascript:location.href='/product/showOne/${b.id}'">
                                    <div class="card-body">
                                        <!--상품 이미지-->
                                        <c:if test="${b.fileName !=null}">
                                            <img src="${path}/${b.id}/${b.fileName}" width="200px" alt="상품">
                                        </c:if>
                                        <c:if test="${b.fileName == null}">
                                            <img src="/product/uploads/showAllImage/준비중.jpg" width="200px"
                                                 alt="이미지 준비중">
                                            <h6> 이미지 준비중입니다.</h6>
                                        </c:if>

                                    </div>
                                    <div class="card-footer">
                                        <p class="card-text">
                                                ${b.title} <br>
                                                ${b.price} 원
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
                <div class="row justify-content-center mt-3">
                    <ul class="pagination justify-content-center">
                        <li class="page-item">
                            <a class="page-link" href="/product/showAll/1"> << </a>
                        </li>
                        <c:if test="${curPage>5}">
                            <li class="page-item">
                                <a href="/product/showAll/${curPage -5}" class="page-link"> < </a>
                            </li>
                        </c:if>
                        <c:if test="${curPage<=5}">
                            <li class="page-item disabled">
                                <a href="/product/showAll/${curPage -5}" class="page-link"> < </a>
                            </li>
                        </c:if>
                        <c:forEach var="page" begin="${startPage}" end="${endPage}">
                            <c:choose>
                                <c:when test="${page eq curPage}">
                                    <li class="page-item active">
                                        <span class="page-link">${page}</span>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item">
                                        <a class="page-link" href="/product/showAll/${page}">${page}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${curPage<maxPage-5}">
                            <li class="page-item">
                                <a class="page-link" href="/product/showAll/${curPage+5}"> > </a>
                            </li>
                        </c:if>
                        <c:if test="${curPage>=maxPage-5}">
                            <li class="page-item disabled">
                                <a class="page-link" href="/product/showAll/${curPage+5}"> > </a>
                            </li>
                        </c:if>
                        <li class="page-item">
                            <a class="page-link" href="/product/showAll/${maxPage}"> >> </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>