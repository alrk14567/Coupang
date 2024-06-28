<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>회원 정보</title>
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
        <div class="col-6">
            <table class="table table-striped">
                <tr>
                    <th>회원 번호</th>
                    <td>${userDTO.id}</td>
                </tr>
                <tr>
                    <th>회원 아이디</th>
                    <td>${userDTO.username}</td>
                </tr>
                <tr>
                    <th>회원 닉네임</th>
                    <td>${userDTO.nickname}</td>
                </tr>
                <tr>
                    <th>주소</th>
                    <td>${userDTO.address}</td>
                </tr>
                <tr>
                    <th>회원 등급</th>
                    <td>${userDTO.gradeName}</td>
                </tr>
                <c:if test="${logIn.id eq userDTO.id || logIn.userGrade eq 1}">
                    <tr class="text-center">
                        <td class="text-center" colspan="3">
                            <a class="btn btn-outline-success" href="/user/update/${userDTO.id}">수정하기</a>
                            <button class="btn btn-outline-danger" onclick="deleteMember(${userDTO.id})">회원 탈퇴</button>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td colspan="3" class="text-center">
                        <a class="btn btn-outline-secondary" href="/user/memberAll">회원 목록</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<script>
    function deleteMember(id) {
        Swql.fire({
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
</script>
</body>
</html>
