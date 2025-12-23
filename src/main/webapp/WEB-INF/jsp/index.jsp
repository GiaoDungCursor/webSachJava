<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8" />
                    <title>BookStore</title>
                    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>" />
                </head>

                <body>
                    <jsp:include page="/WEB-INF/jsp/header.jsp" />

                    <div class="content">
                        <div class="filters">
                            <form action="<c:url value='/'/>" method="get"
                                style="display:flex; gap:10px; flex-wrap:wrap; align-items:center;">
                                <input type="text" name="keyword" placeholder="Tìm kiếm sách"
                                    value="${param.keyword != null ? param.keyword : keyword}" />
                                <select name="category">
                                    <option value="">Tất cả danh mục</option>
                                    <c:forEach var="cat" items="${categories}">
                                        <option value="${cat}" <c:if test="${category == cat}">selected</c:if>>
                                            <c:out value="${cat}" />
                                        </option>
                                    </c:forEach>
                                </select>
                                <button type="submit" class="primary">Áp dụng</button>
                            </form>
                            <span class="filters-meta">Hiển thị <span>${books.size()}</span> của
                                <span>${totalBooks}</span></span>
                        </div>

                        <div class="book-grid">
                            <c:forEach var="book" items="${books}">
                                <div class="book-card">
                                    <c:choose>
                                        <c:when test="${fn:startsWith(book.coverUrl, 'http')}">
                                            <img src="${book.coverUrl}" alt="${book.title}" />
                                        </c:when>
                                        <c:otherwise>
                                            <img src="<c:url value='/${book.coverUrl}'/>" alt="${book.title}" />
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="book-details">
                                        <h3>
                                            <c:out value="${book.title}" />
                                        </h3>
                                        <p><strong>Tác giả:</strong>
                                            <c:out value="${book.author}" />
                                        </p>
                                        <p><strong>Thể loại:</strong>
                                            <c:out value="${book.categoryId}" />
                                        </p>
                                        <p><strong>Giá:</strong>
                                            <fmt:formatNumber value="${book.price}" minFractionDigits="2"
                                                maxFractionDigits="2" />
                                            ₫
                                        </p>
                                        <p><strong>Tồn:</strong>
                                            <c:out value="${book.stock}" />
                                        </p>
                                        <form action="<c:url value='/cart/add'/>" method="post">
                                            <input type="hidden" name="bookId" value="${book.id}" />
                                            <input type="number" name="qty" min="1" value="1"
                                                style="width:60px; margin-right:10px;" />
                                            <button type="submit" class="primary">Thêm vào giỏ</button>
                                        </form>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <ul class="pagination">
                            <c:forEach var="pageNum" items="${pageNumbers}">
                                <li class="${pageNum == currentPage ? 'active' : ''}">
                                    <c:url var="pageUrl" value="/">
                                        <c:param name="page" value="${pageNum}" />
                                        <c:param name="size" value="4" />
                                        <c:param name="keyword" value="${keyword}" />
                                        <c:param name="category" value="${category}" />
                                    </c:url>
                                    <a href="${pageUrl}">
                                        <c:out value="${pageNum}" />
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                        <div>
                            <a href="<c:url value='/cart'/>">Xem giỏ hàng (
                                <c:out value="${cartSize}" /> )
                            </a>
                        </div>
                    </div>

                    <jsp:include page="/WEB-INF/jsp/footer.jsp" />
                </body>

                </html>