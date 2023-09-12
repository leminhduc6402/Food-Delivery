<%--
  Created by IntelliJ IDEA.
  User: Nome
  Date: 10/09/2023
  Time: 6:34 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h1 class="text-center mt-3">THỐNG KÊ DOANH THU</h1>
<div>
    <c:url value="/stats/${restaurant.id}" var="action"/>
    <form class="d-flex gap-3 mt-2" action="${action}">
        <div class="d-flex justify-content-center align-items-center gap-2">
            <label for="year">Nhập năm:</label>
            <input id="year" type="number" name="year"/>
        </div>

        <select id="quarter" class="form-select select w-25" name="quarter">
            <option value="">--- Chọn quý ---</option>
            <option value="1">Quý 1</option>
            <option value="2">Quý 2</option>
            <option value="3">Quý 3</option>
            <option value="4">Quý 4</option>
        </select>

        <select id="month" class="form-select select w-25" name="month">
            <option value="">--- Chọn tháng ---</option>
            <option value="1">Tháng 1</option>
            <option value="2">Tháng 2</option>
            <option value="3">Tháng 3</option>
            <option value="4">Tháng 4</option>
            <option value="5">Tháng 5</option>
            <option value="6">Tháng 6</option>
            <option value="7">Tháng 7</option>
            <option value="8">Tháng 8</option>
            <option value="9">Tháng 9</option>
            <option value="10">Tháng 10</option>
            <option value="11">Tháng 11</option>
            <option value="12">Tháng 12</option>
        </select>

        <button class="btn btn-primary" type="submit">Thống kê</button>
    </form>

    <table class="table mt-3">
        <thead>
            <tr>
                <th scope="col">Mã sản phẩm</th>
                <th scope="col">Tên sản phẩm</th>
                <th scope="col">Tổng doanh thu sản phẩm</th>
                <th scope="col">Số lượng bán ra</th>
            </tr>
        </thead>
        <tbody class="table-body">
            <c:forEach items="${stats}" var="item">
                <tr>
                    <td>${item[0]}</td>                    
                    <td>${item[1]}</td>
                    <td>${item[2]}</td>
                    <td>${item[3]}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<script src="<c:url value="/js/stats.js" />"></script>
