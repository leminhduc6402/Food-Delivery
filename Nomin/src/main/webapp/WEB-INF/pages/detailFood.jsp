<%-- 
    Document   : foodDetail
    Created on : Aug 23, 2023, 3:09:22 p.m.
    Author     : Nome
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1 class="text-center text-info mt-1">Quản lý món ăn</h1>

<c:url value="/addOrUpdateFood" var="action" />
<form:form method="post" action="${action}" modelAttribute="food" enctype="multipart/form-data">
    <form:hidden path="id"/>    
    <form:hidden path="restaurantId.id"/>    
    <form:hidden path="image"/>
    <div class="form-floating mb-3 mt-3">
        <form:input type="text" class="form-control" 
                    path="name" id="name" placeholder="Tên món ăn..." />
        <label for="name">Tên món ăn</label>
        <form:errors path="name" element="div" cssClass="text-danger" />
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input type="text" class="form-control" 
                    path="price" id="price" placeholder="Tên sản phẩm..." />
        <label for="price">Giá (VND)</label>
        <form:errors path="name" element="div" cssClass="text-danger" />
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input type="text" class="form-control" 
                    path="foodType" id="foodType" placeholder="Loại món ăn..." />
        <label for="foodType">Loại món ăn</label>
        <form:errors path="foodType" element="div" cssClass="text-danger" />
    </div>
    <label for="available">Trạng thái món ăn:</label>
    <select class="form-select me-1" id="available" name="available">
        <option value="" ${food.available == null ? 'selected' : ''}>-- Chọn trạng thái của món ăn -- </option>
        <option value="1" ${food.available == 1 ? 'selected' : ''}>Còn hàng</option>
        <option value="0" ${food.available == 0 ? 'selected' : ''}>Hết hàng</option>
    </select>
    <div class="form-floating mb-3 mt-3">
        <form:input type="file" class="form-control" 
                    path="file" id="avatar" accept=".jpg,.jpeg,.png" />
        <label for="file">Ảnh sản phẩm</label>
        <c:if test="${food.image != null}">
            <img src="${food.image}" width="120" id="avatar-preview"  class="mt-2" />
        </c:if>
    </div>
    <div class="form-floating mb-3 mt-3">
        <button class="btn btn-info" type="submit">
            <c:choose>
                <c:when test="${food.id == null}">Thêm sản phẩm</c:when>
                <c:otherwise>Cập nhật sản phẩm</c:otherwise>
            </c:choose>
        </button>
    </div>
</form:form>

<div class="d-flex shadow-lg mt-3 mb-3 p-3 bg-body rounded gap-3 flex-column">
    <h3 class="bg-primary bg-gradient text-white p-3 flex-grow-1">Bình luận và đánh giá</h3>
    <div>
        <c:forEach items="${comments}" var="cmt">           
            <div class="d-flex flex-row comment-row mb-3 border-bottom p-2">
                <div class="p-2">
                    <img src="${cmt.usersId.avatar}" alt="user" width="50" height="50" class="rounded-circle">
                </div>
                <div class="comment-text w-100">
                    <h6 class="font-medium">${cmt.usersId.name}</h6> 
                    <span class="m-b-15 d-block">${cmt.content}</span>
                    <div class="comment-footer"> 
                        <span class="text-muted float-right">
                            Đánh giá:
                            <c:forEach var="star" begin="1" end="5">
                                <i class="fa-solid fa-star" 
                                   style="color: ${star <= cmt.rate ? '#ee4d2d' : ''};"></i>
                            </c:forEach>
                        </span>
                    </div>
                </div>
            </div>           
        </c:forEach>
    </div>
</div>
<script src="<c:url value="/js/main.js" />"></script>
