<%-- 
    Document   : users
    Created on : Aug 1, 2023, 11:07:42 p.m.
    Author     : Nome
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="text-center">Danh sách người dùng</h1>
<div>
    <c:url value="/users" var="action"/>
    <form class="d-flex ms-auto flex-row-reverse" action="${action}">   
        <div class="d-flex">
            <select class="form-select me-1" name="role">
                <option value="" ${param.role.equals(null) ? 'selected' : ''}>Tất cả</option>
                <option value="ROLE_ADMIN" ${param.role.equals('ROLE_ADMIN') ? 'selected' : ''}>Admin</option>
                <option value="ROLE_USER" ${param.role.equals('ROLE_USER') ? 'selected' : ''}>Người dùng</option>
                <option value="ROLE_RESTAURANT" ${param.role.equals('ROLE_RESTAURANT') ? 'selected' : ''}>Cửa hàng</option>
                <option value="1" ${param.role == '1' ? 'selected' : ''}>Cửa hàng (chưa duyệt)</option>
            </select>
            <input class="form-control me-1" type="text" value="${param.kw}" name="kw" placeholder="Nhập tên, sđt...">
            <button class="btn btn-primary" type="submit">Tìm</button>
        </div>
    </form>
</div>
<section class="mt-3">
    <table class="table table-striped table-hover">
        <thead>
            <tr>
                <th class="align-middle text-center">Id</th>       
                <th>Họ và tên</th>
                <th>Số điện thoại</th>                              
                <th>Ngày sinh</th>                
                <th>Giới tính</th>                                
                <th>Vai trò</th>                                             
                <th></th>

            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user">        
                <c:choose>
                    <c:when test="${user.restaurant.status == 0}">
                        <tr class="table-danger">             
                            <td class="text-center align-middle">${user.id}</td>                   
                            <td class="align-middle">${user.name}</td>
                            <td class="align-middle">${user.phone}</td>                   
                            <td class="align-middle"><fmt:formatDate pattern="dd-MM-yyyy" value="${user.birth}"/></td>
                            <td class="align-middle">
                                <c:choose>
                                    <c:when test="${user.gender == 1}">
                                        Nam
                                    </c:when>
                                    <c:otherwise>Nữ</c:otherwise>
                                </c:choose>
                            </td>
                            <td class="align-middle">
                                <c:choose>
                                    <c:when test="${user.role.equals('ROLE_ADMIN')}">
                                        Admin
                                    </c:when>
                                    <c:when test="${user.role.equals('ROLE_RESTAURANT')}">
                                        Cửa hàng
                                    </c:when>
                                    <c:otherwise>Người dùng cá nhân</c:otherwise>
                                </c:choose>
                            </td>
                            <td class="align-middle">
                                <c:url value="/api/users/${user.id}" var="apiDel" />
                                <a href="<c:url value='/users/${user.id}'/>" class="btn btn-info">Chi tiết</a>
                                <button class="btn btn-danger" onclick="delUser('${apiDel}', ${user.id})" >Xóa</button>
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr class="table-light">             
                            <td class="text-center align-middle">${user.id}</td>                   
                            <td class="align-middle">${user.name}</td>
                            <td class="align-middle">${user.phone}</td>                   
                            <td class="align-middle"><fmt:formatDate pattern="dd-MM-yyyy" value="${user.birth}"/></td>
                            <td class="align-middle">
                                <c:choose>
                                    <c:when test="${user.gender == 1}">
                                        Nam
                                    </c:when>
                                    <c:otherwise>Nữ</c:otherwise>
                                </c:choose>
                            </td>
                            <td class="align-middle">
                                <c:choose>
                                    <c:when test="${user.role.equals('ROLE_ADMIN')}">
                                        Admin
                                    </c:when>
                                    <c:when test="${user.role.equals('ROLE_RESTAURANT')}">
                                        Cửa hàng
                                    </c:when>
                                    <c:otherwise>Người dùng cá nhân</c:otherwise>
                                </c:choose>
                            </td>
                            <td class="align-middle">
                                <c:url value="/api/users/${user.id}" var="apiDel" />
                                <a href="<c:url value='/users/${user.id}'/>" class="btn btn-info">Chi tiết</a>
                                <button class="btn btn-danger" onclick="delUser('${apiDel}', ${user.id})" >Xóa</button>
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>

            </c:forEach>
        </tbody>
    </table> 
    <div class="d-flex justify-content-center">
        <c:if test="${counter > 1}">
            <ul class="pagination mt-1 mb-0">
                <li class="page-item">
                    <c:url value="/users" var="prevUrl">
                        <c:param name="page" value="${currPage - 1}" />

                    </c:url>
                    <c:choose>
                        <c:when test="${currPage > 1}">
                            <a class="page-link" href="${prevUrl}">&laquo;</a>
                        </c:when>
                        <c:otherwise>
                            <a class="page-link">&laquo;</a>
                        </c:otherwise>
                    </c:choose>
                </li>

                <c:forEach begin="1" end="${counter}" var="i">
                    <c:url value="/users" var="pageUrl">
                        <c:param name="page" value="${i}" />

                    </c:url>
                    <c:if test="${i >= currPage - 1 && i <= currPage + 1}">
                        <li class="page-item <c:if test='${currPage == i}'>active</c:if>">
                            <a class="page-link" href="${pageUrl}">${i}</a>
                        </li>
                    </c:if>
                </c:forEach>

                <li class="page-item">
                    <c:url value="/users" var="nextUrl">
                        <c:param name="page" value="${currPage + 1}" />
                    </c:url>
                    <c:choose>
                        <c:when test="${currPage < counter}">
                            <a class="page-link" href="${nextUrl}">&raquo;</a>
                        </c:when>
                        <c:otherwise>
                            <a class="page-link">&raquo;</a>
                        </c:otherwise>
                    </c:choose>
                </li>
            </ul>
        </c:if>        
    </div>
</section>
<script src="<c:url value="/js/deleteUser.js" />"></script>