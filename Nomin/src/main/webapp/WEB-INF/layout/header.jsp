<%-- 
    Document   : header
    Created on : Jul 27, 2023, 4:37:05 p.m.
    Author     : Nome
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<c:url value="/" var="action"/>
<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="javascript:void(0)">Nomin</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mynavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <!--navbar-collapse me-auto-->
        <div class="collapse navbar-collapse justify-content-between" id="mynavbar">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/"/>">Trang chủ</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/foods"/>">Món ăn</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/users"/>">Người dùng</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/restaurants"/>">Nhà hàng</a>
                </li>
            </ul>
            <ul class="navbar-nav">
                <c:choose>
                    <c:when test="${pageContext.request.userPrincipal.name != null and pageContext.request.userPrincipal.authenticated}">
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value="/" />">${pageContext.request.userPrincipal.name}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-danger" href="<c:url value="/logout" />">Đăng xuất</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value="/login" />">Đăng nhập</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>