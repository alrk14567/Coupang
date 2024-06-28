<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>새 상품 등록하기</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous">
    </script>
    <script src="https://cdn.ckeditor.com/ckeditor5/41.4.2/classic/ckeditor.js"></script>
    <!--ck 에디터는 편집 형식을 보다 사용자가 편하게 해주는애-->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://ckeditor.com/apps/ckfinder/3.5.0/ckfinder.js"></script>

</head>
<body>
<div class="container-fluid">
    <form method="post" action="/product/write" enctype="multipart/form-data">
        <div class="table">
            <div class="row justify-content-center mb-3">
                <div class="col-6">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="input_title" name="title" placeholder="title">
                        <label for="input_title">title</label>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center mb-3">
                <div class="col-6">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="item_name" name="itemName" placeholder="itemName">
                        <label for="item_name">상품 이름</label>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center mb-3">
                <div class="col-6">
                    <div class="form-floating">
                        <input type="number" class="form-control" id="item_amount" name="itemAmount" placeholder="itemAmount">
                        <label for="item_amount">상품 수량</label>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center mb-3">
                <div class="col-6">
                    <div class="form-floating">
                        <select name="categoryId" id="selectCategory" class="form-control" required>
                            <c:forEach items="${categoryList}" var="category">
                                <option value="${category.id}">${category.content}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center mb-3">
                <div class="col-6">
                    <div class="form-floating">
                        <input type="number" class="form-control" id="price" name="price" placeholder="price">
                        <label for="price">상품 가격</label>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center mb-3">
                <div class="col-6">
                    <textarea name="content" id="input_content"></textarea>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-6">
                    <label for="input_file">첨부 파일</label>
                    <input type="file" class="form-control" id="input_file" name="file" multiple>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-6">
                    <input type="submit" class="btn btn-outline-primary w-100" value="등록하기">
                </div>
            </div>
        </div>
    </form>
</div>
<script>
    ClassicEditor               // 이걸 통해 사용자가 보다 편하게 수정 가능
        .create(document.querySelector('#input_content'), {
            ckfinder: {
                uploadUrl: '/product/uploads'
            }
        })      // 인풋 데이터로 받을 수 있게 선언
        .catch(error => {
            console.log(error)
        })
</script>
</body>
</html>