<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
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
            <h1 class="text-center">장바구니</h1>
            <div class="col-8 text-center">
                <table class="table table-striped">
                    <tr>
                        <th>제품 번호</th>
                        <th colspan="3">제품 이름</th>
                        <th>수량</th>
                        <th>가격</th>
                    </tr>
                    <c:forEach items="${list}" var="l">

                        <tr>
                            <form action="/cart/update/${l.id}" method="post">
                                <td>${l.productId}</td>
                                <td colspan="3"
                                    onclick="javascript:location.href='/product/showOne/${l.productId}'">${l.itemName}</td>
                                <td>
                                    <input type="number" id="quantity" name="amount" class="form-control d-inline w-auto" placeholder="수량"
                                           min="1" max="${l.itemAmount}" value="${l.amount}">
                                </td>
                                <td>${l.price}</td>
                                <td>
                                    <input type="submit" class="btn btn-outline-primary" value="수정">
                                </td>
                                <td>
                                    <a href="/cart/delete/${l.id}" class="btn btn-outline-warning"> X</a>
                                </td>
                            </form>
                        </tr>

                    </c:forEach>
                    <tr>
                        <td>
                            <a class="btn btn-outline-success" href="/buy/write">구매하기</a>
                        </td>
                        <td class="row justify-content-end">
                            총 비용: ${totalPrice} 원
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>