<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8" />
            <title>Admin Dashboard</title>
            <link rel="stylesheet" href="<c:url value='/css/styles.css'/>" />
            <style>
                .dashboard-grid {
                    display: grid;
                    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                    gap: 20px;
                    margin-top: 30px;
                }

                .dashboard-card {
                    background: #fff;
                    padding: 30px;
                    border-radius: 8px;
                    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                    text-align: center;
                    transition: transform 0.2s;
                    cursor: pointer;
                    text-decoration: none;
                    color: #333;
                }

                .dashboard-card:hover {
                    transform: translateY(-5px);
                    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.15);
                }

                .dashboard-card h3 {
                    margin: 0;
                    font-size: 1.5em;
                }

                .dashboard-card p {
                    color: #7f8c8d;
                    margin-top: 10px;
                }

                .icon {
                    font-size: 3em;
                    margin-bottom: 15px;
                    display: block;
                }
            </style>
        </head>

        <body>
            <jsp:include page="/WEB-INF/jsp/header.jsp" />

            <div class="content">
                <h1>Trang quản trị</h1>
                <div class="dashboard-grid">
                    <a href="<c:url value='/admin/books'/>" class="dashboard-card">
                        <span class="icon">📚</span>
                        <h3>Quản lý sách</h3>
                        <p>Thêm, sửa, xóa sách trong kho.</p>
                    </a>
                    <a href="<c:url value='/admin/orders'/>" class="dashboard-card" style="border: 2px solid #2ecc71;">
                        <span class="icon">🛒</span>
                        <h3>Quản lý đơn hàng</h3>
                        <p>Duyệt thanh toán hoặc hoàn tiền cho khách.</p>
                    </a>
                    <a href="<c:url value='/admin/categories'/>" class="dashboard-card">
                        <span class="icon">🏷️</span>
                        <h3>Quản lý loại sách</h3>
                        <p>Thêm, sửa, xóa danh mục.</p>
                    </a>
                    <a href="<c:url value='/admin/stats'/>" class="dashboard-card">
                        <span class="icon">📊</span>
                        <h3>Thống kê doanh thu</h3>
                        <p>Xem báo cáo doanh thu theo thời gian.</p>
                    </a>
                </div>
            </div>

            <jsp:include page="/WEB-INF/jsp/footer.jsp" />
        </body>

        </html>