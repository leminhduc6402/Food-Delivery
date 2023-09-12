<%-- 
    Document   : Detail
    Created on : Aug 9, 2023, 3:31:32 p.m.
    Author     : Nome
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div>
    <c:url value="/addOrUpdateUser" var="action"/>
    <form:form method="post" id="updateUserForm" action="${action}" modelAttribute="user" enctype="multipart/form-data">
        <form:hidden path="id"/>                            
        <form:hidden path="username"/>
        <form:hidden path="password"/>
        <form:hidden path="role"/>
        <div class="d-flex shadow-lg p-3 mb-3 bg-body rounded gap-3">
            <div class="d-flex flex-column">
                <img src="${user.avatar}" style="width: 190px; height: 190px; object-fit: cover;" id="avatar-preview" class="rounded-circle shadow" alt="Ảnh đại diện"/>
                <label for="avatar" type="button" class="btn btn-secondary mt-3">Chọn ảnh</label>
                <form:input id="avatar" path="file" class="d-none" type="file" accept=".jpg,.jpeg,.png" value="Chọn ảnh"/>
            </div>
            <div class="flex-grow-1">
                <h3 class="bg-primary bg-gradient text-white p-3">Thông tin cá nhân</h3>
                <form:errors path="*" element="div" cssClass="alert alert-danger"/>
                <div>
                    <div class="form-floating mb-3 mt-3">
                        <form:input type="text" class="form-control" 
                                    path="name" id="name" placeholder="Họ và tên..." />
                        <label for="name">Họ và tên</label>
                        <form:errors path="name" element="div" cssClass="text-danger" />
                    </div>
                    <div class="form-floating mb-3 mt-3">
                        <form:input type="text" class="form-control" 
                                    path="phone" id="phone" placeholder="Số điện thoại..." />
                        <label for="phone">Số điện thoại</label>
                        <form:errors path="phone" element="div" cssClass="text-danger" />
                    </div>

                    <div class="form-floating mb-3 mt-3">
                        <form:input type="text" class="form-control" 
                                    path="cccd" id="cccd" placeholder="Căn cước công dân..." />
                        <label for="cccd">Căn cước công dân</label>
                        <form:errors path="cccd" element="div" cssClass="text-danger" />                
                    </div>

                    <div class="form-floating mb-3 mt-3">
                        <form:input type="email" class="form-control" 
                                    path="email" id="email" placeholder="Email..." />
                        <label for="email">Email</label>
                    </div>

                    <div class="form-floating mb-3 mt-3">
                        <form:input type="date" path="birth" id="birth" class="form-control"/>
                        <label for="birth">Ngày sinh</label>
                    </div>

                    <div class="d-flex gap-3">
                        <form:radiobutton path="gender" value="1" label="Nam"/>
                        <form:radiobutton path="gender" value="0" label="Nữ"/>
                    </div>

                    <div class="form-floating mb-3 mt-3">
                        <form:input type="text" class="form-control" 
                                    path="address" id="address" placeholder="Địa chỉ..." />
                        <label for="address">Địa chỉ</label>
                        <form:errors path="address" element="div" cssClass="text-danger" />
                    </div>

                    <div class="d-flex justify-content-end">
                        <button class="btn btn-primary text-white" type="submit" id="btnUpdateUser">Cập nhật</button>
                    </div>
                </div>

            </div>

        </div>
    </form:form>
</div>
<script src="<c:url value="/js/main.js" />"></script>
