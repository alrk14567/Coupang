<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %> <!--태그라이브라는 애를 불러올 껀데 걔 이름이 c야-->
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>게시판</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

</head>
<body>
<div class="container-fluid">
    <div class="main h-100">
        <div class="row justify-content-center">
            <div class="col-8 text-center">
                <c:if test="${logIn.getUserGrade()==1}">
                    <div class="col-2">
                        <a class="btn btn-outline-success" href="/product/showAll">상품 목록</a>
                    </div>
                </c:if>
                <div class="row justify-content-center">
                    <h2>회원 목록</h2>
                </div>
                <table class="table table-striped">
                    <tr>
                        <th>회원 번호</th>
                        <th colspan="3">회원 아이디</th>
                        <th>회원 닉네임</th>
                        <th>회원 등급</th>
                    </tr>
                    <c:forEach items="${list}" var="m">
                        <tr onclick="javascript:location.href='/user/memberOne/${m.id}'">
                            <td>${m.id}</td>
                            <td colspan="3">${m.username}</td>
                            <td>${m.nickname}</td>
                            <td>${m.gradeName}</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="6" class="text-center">
                            <ul class="pagination justify-content-center" >
                                <li class="page-item">
                                    <a class="page-link" href="/user/memberAll/1"> << </a>
                                </li>
                                <c:if test="${curPage>5}">
                                    <li class="page-item">
                                        <a href="/user/memberAll/${curPage -5}" class="page-link"> < </a>
                                    </li>
                                </c:if>
                                <c:if test="${curPage<=5}">
                                    <li class="page-item disabled">
                                        <a href="/user/memberAll/${curPage -5}" class="page-link"> < </a>
                                    </li>
                                </c:if>
                                <c:forEach var="page" begin="${startPage}" end="${endPage}">
                                    <c:choose>
                                        <c:when test="${page eq curPage}">
                                            <li class="page-item active">
                                                <span class="page-link">${page}</span>
                                                <%
                                                    //<a href="/board/showAll/${page}">[${page}]</a><!--<span>[${page}]</span> 감싸면 클릭이 되지 않는다.-->
                                                %>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item">
                                                <a class="page-link" href="/user/memberAll/${page}">${page}</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <c:if test="${curPage<maxPage-5}">
                                    <li class="page-item">
                                        <a class="page-link" href="/user/memberAll/${curPage+5}"> > </a>
                                    </li>
                                </c:if>
                                <c:if test="${curPage>=maxPage-5}">
                                    <li class="page-item disabled">
                                        <a class="page-link" href="/user/memberAll/${curPage+5}"> > </a>
                                    </li>
                                </c:if>
                                <li class="page-item">
                                    <a class="page-link" href="/user/memberAll/${maxPage}"> >> </a>
                                </li>
                            </ul>
                        </td>
                    </tr>
                </table>
                <div class="row text-center justify-content-center">
                    <div class="col-6">
                        <form class="justify-content-center text-center" action="/user/memberAll" method="post">
                            <div class="justify-content-center text-center">
                                <input type="text" name="inputNickname">
                                <input type="submit" value="닉네임 검색">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
