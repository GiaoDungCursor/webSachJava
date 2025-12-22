<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Giỏ hàng</title>
    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="content">
    <section class="card">
        <h2>Giỏ hàng</h2>
        <c:if test="${not empty items}">
            <table class="cart-table">
                <thead>
                <tr>
                    <th>Sách</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Thành tiền</th>
                    <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${items}">
                    <tr>
                        <td><c:out value="${item.title}"/></td>
                        <td>
                            <fmt:formatNumber value="${item.price}" minFractionDigits="2" maxFractionDigits="2"/>
                            ₫
                        </td>
                        <td>
                            <form action="<c:url value='/cart/update'/>" method="post">
                                <input type="hidden" name="bookId" value="${item.bookId}"/>
                                <input type="number" name="quantity" value="${item.quantity}" min="0" style="width:70px;"/>
                                <button type="submit">Cập nhật</button>
                            </form>
                        </td>
                        <td>
                            <fmt:formatNumber value="${item.lineTotal}" minFractionDigits="2" maxFractionDigits="2"/>
                            ₫
                        </td>
                        <td>
                            <form action="<c:url value='/cart/update'/>" method="post">
                                <input type="hidden" name="bookId" value="${item.bookId}"/>
                                <input type="hidden" name="quantity" value="0"/>
                                <button type="submit">Xóa</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty items}">
            <p>Không có sản phẩm trong giỏ.</p>
        </c:if>
        <p><strong>Tổng:</strong>
            <fmt:formatNumber value="${total}" minFractionDigits="2" maxFractionDigits="2"/>
            ₫
        </p>
        <div>
            <form action="<c:url value='/cart/clear'/>" method="post" style="display:inline;">
                <button type="submit">Xóa toàn bộ</button>
            </form>
            <a href="<c:url value='/checkout'/>" class="primary" style="margin-left:15px;">Thanh toán</a>
        </div>
    </section>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
