<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Thanh toán</title>
    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="content">
    <section class="card">
        <h2>Thanh toán đơn hàng</h2>
        <p>Kiểm tra lại giỏ hàng và thông tin thanh toán.</p>
        <div class="order-summary">
            <table class="cart-table">
                <thead>
                <tr>
                    <th>Sách</th>
                    <th>Số lượng</th>
                    <th>Thành tiền</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${items}">
                    <tr>
                        <td><c:out value="${item.title}"/></td>
                        <td><c:out value="${item.quantity}"/></td>
                        <td>
                            <fmt:formatNumber value="${item.lineTotal}" minFractionDigits="2" maxFractionDigits="2"/>
                            ₫
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <p><strong>Tổng:</strong>
                <fmt:formatNumber value="${total}" minFractionDigits="2" maxFractionDigits="2"/>
                ₫
            </p>
        </div>
        <form action="<c:url value='/checkout/pay'/>" method="post">
            <div>
                <label>Số thẻ</label>
                <input type="text" name="cardNumber" required/>
            </div>
            <div>
                <label>Ngày hết hạn</label>
                <input type="text" name="expiry" placeholder="MM/YY" required/>
            </div>
            <div>
                <label>Mã CVV</label>
                <input type="text" name="cvv" required/>
            </div>
            <button type="submit" class="primary">Xác nhận thanh toán</button>
        </form>
    </section>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
