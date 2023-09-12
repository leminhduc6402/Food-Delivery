<%-- 
    Document   : register
    Created on : Jul 29, 2023, 2:33:38 p.m.
    Author     : Nome
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--<c:url value="/register" var="action" />--%>
<div style=" background: url("https://res.cloudinary.com/dvyk1ajlj/image/upload/v1692253741/bwfnwxachwyw8kzlyl7s.jpg") no-repeat fixed center; ">
    <form:form method="post" action="${action}" modelAttribute="user" enctype="multipart/form-data" cssClass="mb-3">
        <div class="mb-3 mt-3">
            <label for="name" class="form-label">Họ tên(*):</label>
            <input type="text" class="form-control" id="name" placeholder="Nhập họ tên đầy đủ" name="name">
        </div>
        <div class="mb-3">
            <label for="phone" class="form-label">Số điện thoại(*):</label>
            <input type="text" class="form-control" id="phone" placeholder="Nhập số điện thoại" name="phone">
        </div>
        <div class="mb-3 mt-3">
            <label for="birth" class="form-label">Ngày sinh:</label>
            <input type="date" class="form-control" id="birth" placeholder="Chọn ngày sinh" name="birth">
        </div>
        <div class="mb-3"> Giới tính(*):
            <div class="d-flex gap-3">
                <div class="form-check">
                    <input type="radio" class="form-check-input" id="radio1" name="optradio" value="option1" checked>
                    <label class="form-check-label" for="radio1">Nam</label>
                </div>
                <div class="form-check">
                    <input type="radio" class="form-check-input" id="radio2" name="optradio" value="option2">
                    <label class="form-check-label" for="radio2">Nữ</label>
                </div>
            </div>
        </div>
        <div class="mb-3">
            <label for="address" class="form-label">Nhập địa chỉ nhà(*):</label>
            <input type="text" class="form-control" id="address" placeholder="Nhập địa chỉ nhà" name="address">
        </div>
        <div class="mb-3 mt-3">
            <label for="username" class="form-label">Tài khoản(*):</label>
            <input type="text" class="form-control" id="username" placeholder="Nhập tài khoản" name="username">
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Mật khẩu(*):</label>
            <input type="text" class="form-control" id="password" placeholder="Nhập mật khẩu" name="password">
        </div>
        <div class="mb-3">
            <label for="avatar" class="form-label">Ảnh đại diện:</label>
            <input type="file" accept=".jpg,.jpeg,.png" class="form-control" id="avatar" placeholder="Chọn ảnh đại diện" name="avatar">
        </div>
        <button type="submit" class="btn btn-success">Đăng ký</button>
    </form:form>
</div>

