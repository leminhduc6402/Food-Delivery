<%-- 
    Document   : foods
    Created on : Aug 1, 2023, 3:29:25 p.m.
    Author     : Nome
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--<c:url value="/admin/foods"/>--%>

<section class="mt-3">
    <h1 class="text-center">Danh sách món ăn</h1>
    <!--Hiển thị số trang-->
    <div class="d-flex justify-content-between">
        <c:url value="/foods" var="action"/>
        <form class="d-flex ms-auto" action="${action}">
            <input class="form-control me-1" type="text" value="${param.kw}" name="kw" placeholder="Nhập tên món ăn...">
            <!--<input class="form-control me-1" type="text" value="${param.foodType}" name="foodType" placeholder="Nhập loại món ăn...">-->         
            <input class="form-control me-1" type="text" value="${param.restaurantName}" name="restaurantName" placeholder="Nhập tên nhà hàng...">
            <button class="btn btn-primary" type="submit">Tìm</button>
        </form>
    </div>

    <!--Bảng món ăn-->
    <table class="table table-striped table-hover">
        <thead>
            <tr>
                <th class="text-center align-middle">Id</th>       
                <th>Hình ảnh</th>
                <th>Tên món ăn</th>
                <th>Loại món ăn</th>       
                <th>Giá món ăn</th>
                <th>Tên cửa hàng</th>
                <th>Trạng thái</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${foods}" var="f">                           

                <c:choose>
                    <c:when test="${f.available == 1}">
                        <tr class="table-light">
                            <td class="text-center align-middle">${f.id}</td>
                            <td ><img src="${f.image}" alt="Hình ảnh" width="50"/></td>
                            <td class="align-middle">${f.name}</td>
                            <td class="align-middle">${f.foodType}</td>
                            <td class="align-middle"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${f.price}" /> VND</td>
                            <td class="align-middle">${f.restaurantId.name}</td>
                            <td class="align-middle">True</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr class="table-danger">
                            <td class="text-center align-middle">${f.id}</td>
                            <td ><img src="${f.image}" alt="Hình ảnh" width="50"/></td>
                            <td class="align-middle">${f.name}</td>
                            <td class="align-middle">${f.foodType}</td>
                            <td class="align-middle"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${f.price}" /> VND</td>
                            <td class="align-middle">${f.restaurantId.name}</td>
                            <td class="align-middle">False</td>
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
                    <c:url value="/foods" var="prevUrl">
                        <c:param name="page" value="${currPage - 1}" />
                        <c:param name="kw" value="${param.kw}" />
                        <c:param name="foodType" value="${param.foodType}" />
                        <c:param name="minPrice" value="${param.minPrice}" />
                        <c:param name="maxPrice" value="${param.maxPrice}" />
                        <c:param name="restaurantName" value="${param.restaurantName}" />
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
                    <c:url value="/foods" var="pageUrl">
                        <c:param name="page" value="${i}" />
                        <c:param name="kw" value="${param.kw}" />
                        <c:param name="foodType" value="${param.foodType}" />
                        <c:param name="minPrice" value="${param.minPrice}" />
                        <c:param name="maxPrice" value="${param.maxPrice}" />
                        <c:param name="restaurantName" value="${param.restaurantName}" />
                    </c:url>
                    <c:if test="${i >= currPage - 1 && i <= currPage + 1}">
                        <li class="page-item <c:if test='${currPage == i}'>active</c:if>">
                            <a class="page-link" href="${pageUrl}">${i}</a>
                        </li>
                    </c:if>
                </c:forEach>

                <li class="page-item">
                    <c:url value="/foods" var="nextUrl">
                        <c:param name="page" value="${currPage + 1}" />
                        <c:param name="kw" value="${param.kw}" />
                        <c:param name="foodType" value="${param.foodType}" />
                        <c:param name="minPrice" value="${param.minPrice}" />
                        <c:param name="maxPrice" value="${param.maxPrice}" />
                        <c:param name="restaurantName" value="${param.restaurantName}" />
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
