<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Quản trị</title>
    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="content">
    <section class="admin-panel">
        <h2>Thêm sách mới</h2>
        <form action="<c:url value='/admin/books/add'/>" method="post">
            <div><label>Tiêu đề</label><input type="text" name="title" required/></div>
            <div><label>Tác giả</label><input type="text" name="author" required/></div>
            <div><label>Danh mục</label><input type="text" name="category" required/></div>
            <div><label>Giá</label><input type="number" name="price" step="0.01" required/></div>
            <div><label>Tồn kho</label><input type="number" name="stock" min="0" required/></div>
            <div><label>Mô tả</label><textarea name="description" rows="3"></textarea></div>
            <button type="submit" class="primary">Thêm</button>
        </form>

        <h2>Danh sách sách</h2>
        <div class="book-grid">
            <c:forEach var="book" items="${books}">
                <div class="book-card">
                    <img src="${book.coverUrl}" alt="${book.title}"/>
                    <p><strong><c:out value="${book.title}"/></strong></p>
                    <p><c:out value="${book.author}"/></p>
                    <p><strong>Giá:</strong>
                        <fmt:formatNumber value="${book.price}" minFractionDigits="2" maxFractionDigits="2"/>
                        ₫
                    </p>
                    <p><strong>Tồn:</strong> <c:out value="${book.stock}"/></p>
                </div>
            </c:forEach>
        </div>

        <h2>Đơn hàng</h2>
        <table class="cart-table">
            <thead>
            <tr>
                <th>Mã đơn</th>
                <th>Người tạo</th>
                <th>Tổng</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td><c:out value="${order.id}"/></td>
                    <td><c:out value="${order.username}"/></td>
                    <td>
                        <fmt:formatNumber value="${order.total}" minFractionDigits="2" maxFractionDigits="2"/>
                        ₫
                    </td>
                    <td>
                        <c:set var="statusClass" value="${order.status == 'COMPLETED' ? 'completed' : 'pending'}"/>
                        <span class="status-pill ${statusClass}">
                            <c:out value="${order.status}"/>
                        </span>
                    </td>
                    <td>
                        <form action="<c:url value='/admin/orders/status'/>" method="post">
                            <input type="hidden" name="orderId" value="${order.id}"/>
                            <select name="status">
                                <option value="PENDING" <c:if test="${order.status == 'PENDING'}">selected</c:if>>PENDING</option>
                                <option value="COMPLETED" <c:if test="${order.status == 'COMPLETED'}">selected</c:if>>COMPLETED</option>
                                <option value="CANCELLED" <c:if test="${order.status == 'CANCELLED'}">selected</c:if>>CANCELLED</option>
                            </select>
                            <button type="submit">Cập nhật</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty orders}">
                <tr>
                    <td colspan="5">Chưa có đơn hàng.</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </section>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
