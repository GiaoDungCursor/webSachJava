<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="content">
    <section class="card">
        <h2>Đăng nhập</h2>
        <form action="<c:url value='/login'/>" method="post">
            <div>
                <label>Tên người dùng</label>
                <input type="text" name="username" required/>
            </div>
            <div>
                <label>Mật khẩu</label>
                <input type="password" name="password" required/>
            </div>
            <div>
                <button type="submit" class="primary">Đăng nhập</button>
            </div>
        </form>
        <c:if test="${not empty error}">
            <p style="color:#d32f2f;"><c:out value="${error}"/></p>
        </c:if>
        <c:if test="${not empty success}">
            <p style="color:#2a7d32;"><c:out value="${success}"/></p>
        </c:if>
        <p>Chưa có tài khoản? <a href="<c:url value='/register'/>">Đăng ký ngay</a></p>
    </section>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
