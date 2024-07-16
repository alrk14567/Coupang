<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>${boardDTO.id}번 게시글</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row justify-content-center">
        <div class="row justify-content-end">
            <div class="col-2">
                <a class="btn btn-outline-nomal" href="/cart/showList">장바구니</a> &nbsp;&nbsp;
                <a class="btn btn-outline-danger" href="/user/logOut">로그 아웃</a>
            </div>
        </div>
        <div class="col-6">
            <table class="table table-striped">
                <tr>
                    <th>제목</th>
                    <td>${productDTO.title}</td>
                </tr>
                <tr>
                    <th>상품 번호</th>
                    <td>${productDTO.id}</td>
                </tr>
                <tr>
                    <th>상품 이름</th>
                    <td>${productDTO.itemName}</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${productDTO.nickname}</td>
                </tr>
                <tr>
                    <th>작성일</th>
                    <td><fmt:formatDate value="${productDTO.entryDate}" pattern="yyyy년 MM월 dd일 E요일 HH시 mm분 ss초"/></td>
                </tr>
                <tr>
                    <th>수정일</th>
                    <td><fmt:formatDate value="${productDTO.modifyDate}" pattern="yyyy년 MM월 dd일 E요일 HH시 mm분 ss초"/></td>
                </tr>
                <tr>
                    <th colspan="2" class="text-center">내용</th>
                </tr>
                <tr>
                    <td colspan="2" >${productDTO.content}</td>
                </tr>
                <c:if test="${productDTO.writerId eq logIn.id}">
                    <tr class="text-center">
                        <td class="text-center" colspan="3">
                            <a class="btn btn-outline-success" href="/product/update/${productDTO.id}">수정하기</a>
                            <button class="btn btn-outline-danger" onclick="deleteBoard(${productDTO.id})">삭제하기</button>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <c:if test="${logIn.userGrade eq 3}">
                <tr class="text-center">
                    <td class="text-center" colspan="3">
                        <input type="number" id="quantity" class="form-control d-inline w-auto" placeholder="수량" min="1" max="${productDTO.itemAmount}">
                        <button class="btn btn-outline-success" onclick="addToCart(${productDTO.id})">장바구니 담기</button>
                        <a class="btn btn-outline-success" href="/buy/write">구매하기</a>
                    </td>
                </tr>
                </c:if>
                </tr>
                <tr>
                    <td colspan="3" class="text-center">
                        <a class="btn btn-outline-secondary" href="/product/showAll">목록으로</a>
                    </td>
                </tr>
            </table>
            <table class="table table-primary table-striped">
                <tr class="text-center">
                    <td>댓글</td>
                    <form action="/reply/insert/${productDTO.id}" method="post">
                        <td colspan="5">
                            <input type="text" name="content" class="form-control justify-content-center"
                                   placeholder="댓글">
                        </td>
                        <td>
                            <input type="submit" class="btn btn-outline-success" value="작성"/>
                        </td>
                    </form>
                </tr>
                <c:forEach items="${replyList}" var="list">
                    <tr id="tr-${list.id}">
                        <c:choose>
                            <c:when test="${list.writerId eq logIn.id}">
                                <form id="form-${productDTO.id}" action="/reply/update/${list.id}" method="post">
                                    <td colspan="2">
                                        <input id='form-${list.id}' type="text" class="form-control" name="content"
                                               value="${list.content}" disabled>
                                    </td>
                                    <td>${list.nickname}</td>
                                    <td><fmt:formatDate value="${list.modifyDate}" pattern="yy년MM월d일 HH:mm"/></td>
                                    <td>
                                        <div id="div-${list.id}" class="btn btn-outline-primary"
                                             onclick="buttonClick(${list.id},${productDTO.id})">수정
                                        </div>
                                    </td>
                                    <td>
                                        <a href="/reply/delete/${list.id}" class="btn btn-outline-warning">삭제</a>
                                    </td>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <td colspan="2">${list.content}</td>
                                <td>${list.nickname}</td>
                                <td><fmt:formatDate value="${list.modifyDate}" pattern="yy년MM월d일 HH:mm"/></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>

<script>
    function deleteBoard(id) {
        Swal.fire({
            title: '정말로 삭제하시겠습니까?',
            showCancelButton: true,
            confirmButtonText: '삭제하기',
            cancelButtonText: '취소',
            icon: 'warning'
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    title: '삭제되었습니다.',
                    icon: 'check'
                }).then((result) => {
                    location.href = '/board/delete/' + id;
                })
            }
        })
    }

    let updateUse = false;

    function buttonClick(replyId, boardId) {
        updateUse = true;
        if (updateUse) {
            disableButton(replyId);
            let div = document.getElementById("div-" + replyId);
            let td = document.createElement("td");
            let form = document.getElementById("form-" + boardId);
            let input = document.createElement("input");
            let btnSubmit = document.createElement("input");
            form.setAttribute("action", "/reply/update/" + replyId);
            input.setAttribute("type", "text")
            input.setAttribute("name", "content")
            input.setAttribute("value", "샘플")

            btnSubmit.setAttribute("type", "submit");
            btnSubmit.setAttribute("value", "수정");
            btnSubmit.className = div.className;

            form.appendChild(input);
            form.appendChild(btnSubmit);
            $(td).attr('colspan', '7');

            td.appendChild(form);

            let tds = $('#tr-'+replyId).children();
            for(e of tds){
                $(e).hide();
            }

            $('#tr-' + replyId).append(td);
        }
    }

    function disableButton(id) {
        console.log(id);
        // 수정 div를 submit 버튼으로 변환
        // inputContent에 disabled attribute 삭제
        $('#form-' + id).removeAttr('disabled');
    }

    function addToCart(productId) {
        let quantity=document.getElementById("quantity").value;

        $.ajax({
            url: '/cart/insert',
            type: 'POST',
            data: {
                'quantity': quantity,
                'productId': productId
            },
            success: function(response) {
                Swal.fire({
                    title: '성공적으로 장바구니에 담겼습니다.',
                    icon: 'success'
                });
            },
            error: function (xhr, status, error) {
                Swal.fire({
                    title: '오류가 발생했습니다.',
                    text: xhr.reponseText,
                    icon: 'success'
                })
            }

        })
    }
</script>
</body>
</html>