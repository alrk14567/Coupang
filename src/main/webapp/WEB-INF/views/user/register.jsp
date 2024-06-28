<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>회원 가입</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<div class="container-fluid h-100">
    <form action="/user/register" method="post">
        <div class="row justify-content-center">
            <div class="col-4">
                <label for="username">아이디</label>
                <input type="text" name="username" id="username" class="form-control" oninput="disableButton()">
                <div class="btn btn-outline-primary" onclick="validateUsername()">중복확인</div>
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col-4">
                <label for="password">비밀번호</label>
                <input type="password" name="password" id="password" class="form-control">
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col-4">
                <label for="nickname">닉네임</label>
                <input type="text" name="nickname" id="nickname" class="form-control" oninput="disableButton()">
                <div class="btn btn-outline-primary" onclick="validateNickname()">중복확인</div>
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col-4">
                <label for="address">주소</label>
                <input type="text" name="address" id="address" class="form-control">
            </div>
        </div>
        <input type="hidden" name="userGrade" id="userGrade" value="${userGrade}">
        <c:choose>
            <c:when test="${userGrade eq 1}">
                <input type="hidden" name="gradeName" id="gradeName" value="관리자">
            </c:when>
            <c:when test="${userGrade eq 2}">
                <input type="hidden" name="gradeName" id="gradeName" value="판매자">
            </c:when>
            <c:when test="${userGrade eq 3}">
                <input type="hidden" name="gradeName" id="gradeName" value="구매자">
            </c:when>
            <c:otherwise>
                <input type="hidden" name="gradeName" id="gradeName" value="일반 사용자">
            </c:otherwise>
        </c:choose>
        <div class="row justify-content-center">
            <div class="col-4 text-center">
                <input id='btnSubmit' type="submit" class="btn btn-outline-primary" oninput="validateAll()"
                       value="회원 가입" disabled>
            </div>
        </div>
    </form>
    <script>
        let validNickname = false;
        let validUsername = false;

        function validateUsername() {
            let username = $('#username').val();
            $.ajax({
                url: '/user/validateUsername',
                type: 'get',
                data: {
                    'username': username
                },
                success: (result) => {
                    if (result.result === 'success') {
                        Swal.fire({
                            'title': '가입 가능한 아이디 입니다.'
                        })
                        validUsername = true;
                        if (validUsername && validNickname) {
                            $('#btnSubmit').removeAttr('disabled');
                        } else {
                            $('#btnSubmit').attr('disabled', 'true');
                        }
                    } else {
                        Swal.fire({
                            'title': '중복된 아이디 입니다.',
                            'icon': 'warning'
                        })
                        validUsername = false;
                    }
                }
            });
        }

        function validateNickname() {
            let nickname = $('#nickname').val();
            console.log(nickname);
            $.ajax({
                url: '/user/validateNickname',
                type: 'get',
                data: {
                    'nickname': nickname
                },
                success: (result) => {
                    if (result.result === 'success') {
                        Swal.fire({
                            'title': '가입 가능한 닉네임 입니다.'
                        })
                        validNickname = true;
                        if (validUsername && validNickname) {
                            $('#btnSubmit').removeAttr('disabled');
                        } else {
                            $('#btnSubmit').attr('disabled', 'true');
                        }
                    } else {
                        Swal.fire({
                            'title': '중복된 닉네임 입니다.',
                            'icon': 'warning'
                        })
                        validNickname = false;
                    }
                }
            });
        }

        /*function validateAll() {
            let nickname = $('nickname').val();
            let username = $('username').val();
            $.ajax({
                url: '/user/validateAll',
                type: 'get',
                data: {
                    'username': username,
                    'nickname': nickname
                },
                success: (result) => {
                    if (result.result === 'success') {
                        $('btnSubmit').removeAttr('disabled')
                    }
                }
            });
        }*/

        /*let isUsername = validateUsername(username)!=null;
        let isNickname = validateNickname(nickname)!=null;

        function validateCheck() {
            if (isUsername && isNickname) {
                $('#btnSubmit').removeAttr("disabled")
            }
        }*/

        function disableButton() {
            $('#btnSubmit').attr('disabled', 'true');
        }


    </script>
</div>
</body>
</html>
