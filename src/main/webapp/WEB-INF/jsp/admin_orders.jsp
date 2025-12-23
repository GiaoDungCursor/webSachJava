<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8" />
                    <title>Quản lý đơn hàng</title>
                    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>" />
                </head>

                <body>
                    <jsp:include page="/WEB-INF/jsp/header.jsp" />

                    <div class="content">
                        <section class="admin-panel">
                            <div
                                style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                                <h2>Quản lý đơn hàng</h2>
                                <a href="<c:url value='/admin/stats'/>"
                                    style="text-decoration: none; color: #1d79ff;">&larr; Quay lại Thống kê</a>
                            </div>

                            <table class="cart-table">
                                <thead>
                                    <tr>
                                        <th>Mã đơn</th>
                                        <th>Người tạo</th>
                                        <th>Ngày đặt</th>
                                        <th>Tổng tiền</th>
                                        <th>Trạng thái</th>
                                        <th>Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="order" items="${orders}">
                                        <tr>
                                            <td>
                                                <c:out value="${order.id}" />
                                            </td>
                                            <td>
                                                <c:out value="${order.username}" />
                                            </td>
                                            <td>
                                                <fmt:parseDate value="${order.orderDate}" pattern="yyyy-MM-dd'T'HH:mm"
                                                    var="parsedDate" type="both" />
                                                <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy HH:mm" />
                                            </td>
                                            <td>
                                                <fmt:formatNumber value="${order.total}" minFractionDigits="0"
                                                    maxFractionDigits="0" /> ₫
                                            </td>
                                            <td>
                                                <c:set var="statusClass" value="${fn:toLowerCase(order.status)}" />
                                                <span class="status-pill ${statusClass}">
                                                    <c:out value="${order.status}" />
                                                </span>
                                            </td>
                                            <td>
                                                <div style="display:flex; gap:5px;">
                                                    <c:choose>
                                                        <c:when test="${order.status == 'PENDING'}">
                                                            <form action="<c:url value='/admin/orders/approve'/>"
                                                                method="post" style="display:inline;">
                                                                <input type="hidden" name="orderId"
                                                                    value="${order.id}" />
                                                                <button type="submit"
                                                                    style="background:#2ecc71; color:white; border:none; padding:8px 15px; cursor:pointer; border-radius:4px; font-weight:600;">Duyệt</button>
                                                            </form>
                                                            <form action="<c:url value='/admin/orders/refund'/>"
                                                                method="post" style="display:inline;"
                                                                onsubmit="return confirm('Xác nhận hoàn tiền cho đơn hàng này?');">
                                                                <input type="hidden" name="orderId"
                                                                    value="${order.id}" />
                                                                <button type="submit"
                                                                    style="background:#e74c3c; color:white; border:none; padding:8px 15px; cursor:pointer; border-radius:4px; font-weight:600;">Refund</button>
                                                            </form>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span style="color:#7f8c8d; font-size: 0.9em;">Đã xử
                                                                lý</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty orders}">
                                        <tr>
                                            <td colspan="6" style="text-align: center; color: #7f8c8d; padding: 20px;">
                                                Chưa có đơn hàng nào trong hệ thống.
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </section>
                    </div>

                    <jsp:include page="/WEB-INF/jsp/footer.jsp" />
                </body>

                </html>