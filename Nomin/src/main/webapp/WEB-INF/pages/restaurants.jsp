<%-- 
    Document   : restaurants
    Created on : Aug 22, 2023, 6:35:41 p.m.
    Author     : Nome
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="text-center">Danh sách nhà hàng</h1>
<div>
    <c:url value="/restaurants" var="action"/>
    <form class="d-flex ms-auto flex-row-reverse" action="${action}">   
        <div class="d-flex">
            <div class="align-items-center d-flex gap-2 w-75">
                <input type="checkbox" id="resStatus" value="0" name="status" ${param.status == 0 ? 'checked' : ''}/>
                <label for="resStatus">Chưa duyệt</label>
            </div>
            <input class="form-control me-1" type="text" value="${param.kw}" name="kw" placeholder="Nhập tên, phí ship..."/>
            <button class="btn btn-primary" type="submit">Tìm</button>
        </div>
    </form>
</div>  
<table class="table table-striped table-hover">
    <thead>
        <tr>
            <th class="text-center align-middle">Id</th>       
            <th>Tên nhà hàng</th>
            <th>Phí vận chuyển</th>
            <th>Trạng thái</th>   
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${restaurants}" var="res">                           

            <c:choose>
                <c:when test="${res.status == 1}">
                    <tr class="table-light">
                        <td class="text-center align-middle">${res.id}</td>
                        <td class="align-middle">${res.name}</td>
                        <td class="align-middle"><fmt:formatNumber value="${res.deliveryFee}" maxFractionDigits="2" /></td>
                        <td class="align-middle">Đã duyệt</td>                        
                        <td class="align-middle">
                            <c:url value="/api/restaurants/${res.id}" var="apiDel" />
                            <a href="<c:url value='/restaurants/${res.id}'/>" class="btn btn-info">Chi tiết</a>
                            <button class="btn btn-danger" onclick="delUser('${apiDel}', ${res.id})" >Xóa</button>
                        </td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr class="table-danger">
                        <td class="text-center align-middle">${res.id}</td>
                        <td class="align-middle">${res.name}</td>
                        <td class="align-middle"><fmt:formatNumber value="${res.deliveryFee}" maxFractionDigits="2" /></td>
                        <td class="align-middle">Chờ xét duyệt</td>
                        <td class="align-middle">
                            <c:url value="/api/restaurants/${res.id}" var="apiDel" />
                            <a href="<c:url value='/restaurants/${res.id}'/>" class="btn btn-info">Chi tiết</a>
                            <button class="btn btn-danger" onclick="delUser('${apiDel}', ${user.id})" >Xóa</button>
                        </td>
                    </tr>
                </c:otherwise>
            </c:choose>


        </c:forEach>
    </tbody>
</table>