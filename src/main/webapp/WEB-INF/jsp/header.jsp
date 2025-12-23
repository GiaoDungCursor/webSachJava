<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <header class="site-header">
            <div class="brand">
                <a href="<c:url value='/'/>" class="logo">BookStore</a>
            </div>
            <nav>
                <a href="<c:url value='/'/>">Trang chủ</a>
                <a href="<c:url value='/cart'/>">Giỏ hàng</a>
                <c:if test="${not empty sessionScope.LOGGED_IN_USER and sessionScope.LOGGED_IN_USER.role == 'ADMIN'}">
                    <a href="<c:url value='/admin'/>">Admin</a>
                </c:if>
                <c:choose>
                    <c:when test="${empty sessionScope.LOGGED_IN_USER}">
                        <a href="<c:url value='/login'/>">Đăng nhập</a>
                        <a href="<c:url value='/register'/>">Đăng ký</a>
                    </c:when>
                    <c:otherwise>
                        <form action="<c:url value='/logout'/>" method="post" style="display:inline;">
                            <button type="submit" class="link-button">Đăng xuất</button>
                        </form>
                        <span class="user-label">Xin chào,
                            <c:out value="${sessionScope.LOGGED_IN_USER.username}" />
                        </span>
                    </c:otherwise>
                </c:choose>
            </nav>
        </header>