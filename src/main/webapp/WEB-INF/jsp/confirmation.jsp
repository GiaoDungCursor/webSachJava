<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Xác nhận đơn hàng</title>
    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="content">
    <section class="card">
        <h2>Thanh toán thành công</h2>
        <p>Mã đơn hàng: <strong><c:out value="${order.id}"/></strong></p>
        <c:set var="statusClass" value="${order.status == 'COMPLETED' ? 'completed' : 'pending'}"/>
        <p>Trạng thái hiện tại:
            <span class="status-pill ${statusClass}">
                <c:out value="${order.status}"/>
            </span>
        </p>
        <p>Tổng thanh toán:
            <strong>
                <fmt:formatNumber value="${order.total}" minFractionDigits="2" maxFractionDigits="2"/>
                ₫
            </strong>
        </p>
        <div>
            <a href="<c:url value='/'/>" class="primary">Tiếp tục mua sắm</a>
            <c:if test="${not empty sessionScope.LOGGED_IN_USER and sessionScope.LOGGED_IN_USER.role == 'ADMIN'}">
                <a href="<c:url value='/admin'/>">Quản trị đơn hàng</a>
            </c:if>
        </div>
    </section>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
