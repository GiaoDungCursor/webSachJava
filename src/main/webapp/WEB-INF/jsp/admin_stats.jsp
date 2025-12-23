<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8" />
                <title>Thống kê doanh thu</title>
                <link rel="stylesheet" href="<c:url value='/css/styles.css'/>" />
                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
            </head>

            <body>
                <jsp:include page="/WEB-INF/jsp/header.jsp" />

                <div class="content">
                    <a href="<c:url value='/admin'/>">&larr; Quay lại Dashboard</a>
                    <h1>Thống kê doanh thu</h1>

                    <div style="display: flex; gap: 20px; font-size: 1.2em; margin-bottom: 30px;">
                        <div style="background: #e8f5e9; padding: 20px; border-radius: 8px; border: 1px solid #c8e6c9;">
                            <strong>Tổng doanh thu (Hoàn thành):</strong>
                            <div style="font-size: 2em; color: #2ecc71;">
                                <fmt:formatNumber value="${totalRevenue}" minFractionDigits="0" maxFractionDigits="0" />
                                ₫
                            </div>
                        </div>
                        <a href="<c:url value='/admin/orders'/>"
                            style="background: #fff3e0; padding: 20px; border-radius: 8px; border: 1px solid #ffe0b2; text-decoration: none; color: inherit; transition: transform 0.2s;"
                            onmouseover="this.style.transform='translateY(-3px)'"
                            onmouseout="this.style.transform='translateY(0)'">
                            <strong>Đơn chờ xử lý:</strong>
                            <div style="font-size: 2em; color: #f39c12;">
                                <c:out value="${pendingOrdersCount}" />
                            </div>
                            <small style="color: #f39c12; font-weight: 600;">Click để quản lý &rarr;</small>
                        </a>
                    </div>

                    <div
                        style="background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); margin-bottom: 30px;">
                        <canvas id="revenueChart" width="800" height="400"></canvas>
                    </div>
                </div>

                <script>
                    // Data passed from controller:
                    // labels: ['2024-01-01', '2024-01-02'...]
                    // data: [100000, 250000...]

                    // We expect controller to add "chartLabels" and "chartData" attributes
                    // Use simple JS array construction
                    const labels = [
                        <c:forEach var="item" items="${revenueData}">
                            '<c:out value="${item[0]}" />',
                        </c:forEach>
                    ];

                    const data = [
                        <c:forEach var="item" items="${revenueData}">
                            <c:out value="${item[1]}" />,
                        </c:forEach>
                    ];

                    const ctx = document.getElementById('revenueChart').getContext('2d');
                    const myChart = new Chart(ctx, {
                        type: 'line',
                        data: {
                            labels: labels,
                            datasets: [{
                                label: 'Doanh thu theo ngày (VNĐ)',
                                data: data,
                                backgroundColor: 'rgba(52, 152, 219, 0.2)',
                                borderColor: 'rgba(52, 152, 219, 1)',
                                borderWidth: 2,
                                tension: 0.1,
                                fill: true
                            }]
                        },
                        options: {
                            scales: {
                                y: {
                                    beginAtZero: true,
                                    ticks: {
                                        callback: function (value, index, values) {
                                            return value.toLocaleString('vi-VN') + ' ₫';
                                        }
                                    }
                                }
                            }
                        }
                    });
                </script>

                <jsp:include page="/WEB-INF/jsp/footer.jsp" />
            </body>

            </html>