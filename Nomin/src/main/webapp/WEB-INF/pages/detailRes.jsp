<%-- 
    Document   : detailRes
    Created on : Aug 22, 2023, 7:24:00 p.m.
    Author     : Nome
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="d-flex shadow-lg mt-3 mb-3 p-3 bg-body rounded gap-3 flex-column">
    <h3 class="bg-primary bg-gradient text-white p-3 flex-grow-1">Thông tin chủ nhà hàng</h3>
    <div class="row">
        <div class="col-2"><img src="${user.avatar}" style="width: 190px; height: 190px; object-fit: cover;" id="avatar-preview" class="rounded-circle shadow" alt="Ảnh đại diện"/></div>        
        <div class="col-4 ps-5 d-flex flex-column justify-content-between">
            <div>Họ và tên:</div>            
            <div>Số điện thoại:</div>
            <div>Căn cước công dân:</div>
            <div>Email:</div>
            <div>Ngày sinh:</div>            
            <div>Giới tính:</div>
            <div>Địa chỉ</div>            
        </div>
        <div class="col-6 d-flex flex-column justify-content-between">
            <div>${user.name}</div>
            <c:choose>
                <c:when test="${user.phone != null}">
                    <div>${user.phone}</div>
                </c:when>
                <c:otherwise>
                    <div class="text-danger">Chưa cập nhật số điện thoại</div>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${user.cccd != null}">
                    <div>${user.cccd}</div>
                </c:when>
                <c:otherwise>
                    <div class="text-danger">Chưa cập nhật căn cước</div>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${user.email != null}">
                    <div>${user.email}</div>
                </c:when>
                <c:otherwise>
                    <div class="text-danger">Chưa cập nhật email</div>
                </c:otherwise>
            </c:choose>
            <div>${user.birth}</div>
            <div>${user.gender == 1 ? 'Nam' : 'Nữ'}</div>
            <div>${user.address}</div>
        </div>
    </div>

    <div id="map" style="width: 100%; height: 300px;"></div>
    <script>
        var userAddress = "${user.address}";
        var encodedAddress = encodeURIComponent(userAddress);
        goongjs.accessToken = 'mdpm60LDTlwPYElvrdAe2IXZZuBO7xjYWMEAKX0R';
        var geocodingUrl = "https://rsapi.goong.io/geocode?address=" + encodedAddress + "&api_key=g1EhcQacQNbVzTCR1aE8nsIVLhPDMvNqDJj5xhnm";
        fetch(geocodingUrl)
                .then(res => res.json())
                .then(data => {
                    if (data.status === "OK" && data.results.length > 0) {
                        var coordinates = data.results[0].geometry.location;
                        console.log(coordinates)
                        var map = new goongjs.Map({
                            container: 'map',
                            style: 'https://tiles.goong.io/assets/goong_map_web.json',
                            center: coordinates, // Sử dụng tọa độ từ Geocoding API
                            zoom: 15
                        });
                        var marker = new goongjs.Marker()
                                .setLngLat(coordinates)
                                .addTo(map);
                    } else {
                        console.error('Không thể tìm thấy tọa độ cho địa chỉ này.');
                    }
                })

    </script>

    <div class="d-flex justify-content-end">
        <a href="<c:url value='/users/${user.id}'/>" class="btn btn-primary">Cập nhật thông tin cá nhân</a>
    </div>
</div>

<div class="d-flex shadow-lg mt-3 mb-3 p-3 bg-body rounded gap-3">
    <div class="flex-grow-1">
        <h3 class="bg-primary bg-gradient text-white p-3">Thông tin cửa hàng</h3>
        <c:if test="${restaurant.status == 0}">
            <div class="alert alert-danger">Cửa hàng chưa được cho phép kinh doanh </div>
        </c:if>
        <c:url value="/addOrUpdateRes" var="action"/>
        <form:form method="post" action="${action}" modelAttribute="restaurant" id="updateResForm" enctype="multipart/form-data">    
            <form:errors path="*" element="div" cssClass="alert alert-danger"/>
            <form:hidden path="id"/>                   
            <form:hidden path="userId.id"/>
            <div class="form-floating mb-3 mt-3">
                <form:input type="text" class="form-control" 
                            path="name" id="name" placeholder="Tên nhà hàng..." />
                <label for="name">Tên nhà hàng</label>
                <form:errors path="name" element="div" cssClass="text-danger" />
            </div>
            <div class="form-floating mb-3 mt-3">
                <form:input type="text" class="form-control" 
                            path="deliveryFee" id="deliveryFee" placeholder="Phí vận chuyển..." />
                <label for="deliveryFee">Phí vận chuyển (VND)</label>
                <form:errors path="deliveryFee" element="div" cssClass="text-danger" />
            </div>
            <div>
                <span class="text-muted float-right">
                    Đánh giá:
                    <c:forEach var="star" begin="1" end="5">
                        <c:choose>
                            <c:when test="${star <= averageRate}">
                                <i class="fa-solid fa-star" style="color: #ee4d2d;"></i>
                            </c:when>
                            <c:when test="${star > averageRate and star < averageRate + 0.5}">
                                <i class="fa-solid fa-star-half-stroke" style="color: #ee4d2d;"></i>
                                <!--<i class="fa-solid fa-star-half" style="color: #ee4d2d;"></i>-->
                            </c:when>
                            <c:otherwise>
                                <i class="fa-solid fa-star"></i>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </span>
            </div>
            <div class="form-check mb-3">
                <form:checkbox class="form-check-input" value="1" path="status" label="Đang hoạt động"/>
            </div>
            <div class="d-flex justify-content-end">
                <a class="btn btn-primary text-white" href="<c:url value="/stats/${restaurant.id}"/>">Thống kê</a>
                <button class="btn btn-primary text-white ms-2" id="btnUpdateRes" type="submit">Cập nhật</button>
            </div>
        </form:form>

    </div>

</div>  

<div class="d-flex shadow-lg mt-3 mb-3 p-3 bg-body rounded gap-3">
    <div class="flex-grow-1">
        <h3 class="bg-primary bg-gradient text-white p-3">Thực đơn</h3>
        <a href="<c:url value="/detailFood"/>" class="btn btn-info">Thêm sản phẩm</a>
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th class="text-center align-middle">Id</th>       
                    <th>Hình ảnh</th>
                    <th>Tên món ăn</th>
                    <th>Loại món ăn</th>       
                    <th>Giá món ăn</th>
                    <th>Trạng thái</th>
                    <th></th>
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
                                <td class="align-middle">Còn hàng</td>
                                <td>
                                    <a href="<c:url value='/detailFood/${f.id}'/>" class="btn btn-info">Chi tiết</a>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <tr class="table-danger">
                                <td class="text-center align-middle">${f.id}</td>
                                <td ><img src="${f.image}" alt="Hình ảnh" width="50"/></td>
                                <td class="align-middle">${f.name}</td>
                                <td class="align-middle">${f.foodType}</td>
                                <td class="align-middle"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${f.price}" /> VND</td>
                                <td class="align-middle">Hết hàng</td>
                                <td>
                                    <a href="<c:url value='/detailFood/${f.id}'/>" class="btn btn-info">Chi tiết</a>
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div> 
<script src="<c:url value="/js/main.js" />"></script>

