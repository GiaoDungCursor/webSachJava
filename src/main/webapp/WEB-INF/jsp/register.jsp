<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8" />
            <title>Đăng ký</title>
            <link rel="stylesheet" href="<c:url value='/css/styles.css'/>" />
        </head>

        <body>
            <jsp:include page="/WEB-INF/jsp/header.jsp" />

            <div class="content">
                <section class="card">
                    <h2>Đăng ký tài khoản</h2>
                    <form action="<c:url value='/register'/>" method="post">
                        <div>
                            <label>Tên người dùng</label>
                            <input type="text" name="username" required />
                        </div>
                        <div>
                            <label>Họ và tên</label>
                            <input type="text" name="fullName" required />
                        </div>
                        <div>
                            <label>Email</label>
                            <input type="email" name="email" required />
                        </div>
                        <div>
                            <label>Địa chỉ</label>
                            <input type="text" name="address" required />
                        </div>
                        <div>
                            <label>Số điện thoại</label>
                            <input type="text" name="phoneNumber" required />
                        </div>
                        <div>
                            <label>Mật khẩu</label>
                            <input type="password" name="password" required />
                        </div>
                        <div class="captcha">
                            <label>Captcha</label>
                            <span class="code">
                                <c:out value="${captcha}" />
                            </span>
                            <input type="text" name="captcha" placeholder="Nhập mã" required />
                        </div>
                        <div>
                            <button type="submit" class="primary">Tạo tài khoản</button>
                        </div>
                    </form>
                    <c:if test="${not empty error}">
                        <p style="color:#d32f2f;">
                            <c:out value="${error}" />
                        </p>
                    </c:if>
                </section>
            </div>

            <jsp:include page="/WEB-INF/jsp/footer.jsp" />
        </body>

        </html>