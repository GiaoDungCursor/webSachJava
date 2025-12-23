<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8" />
                    <title>Quản trị</title>
                    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>" />
                </head>

                <body>
                    <jsp:include page="/WEB-INF/jsp/header.jsp" />

                    <div class="content">
                        <section class="admin-panel">
                            <h2 id="formTitle">Quản lý sách</h2>
                            <form id="bookForm" action="<c:url value='/admin/books/add'/>" method="post"
                                enctype="multipart/form-data">
                                <input type="hidden" name="id" id="bookId" />
                                <div><label>Tiêu đề</label><input type="text" name="title" id="title" required /></div>
                                <div><label>Tác giả</label><input type="text" name="author" id="author" required />
                                </div>
                                <div><label>Danh mục</label><input type="text" name="category" id="category" required />
                                </div>
                                <div><label>Giá</label><input type="number" name="price" id="price" step="1" required />
                                </div>
                                <div><label>Tồn kho</label><input type="number" name="stock" id="stock" min="0"
                                        required /></div>
                                <div>
                                    <label>Ảnh bìa</label>
                                    <input type="file" name="image" accept="image/*" />
                                </div>
                                <div style="margin-top:10px;">
                                    <button type="submit" class="primary" id="submitBtn">Thêm sách</button>
                                    <button type="button" onclick="resetForm()"
                                        style="background:#7f8c8d; color:white; border:none; padding:8px 15px; cursor:pointer; margin-left:10px;">Làm
                                        mới</button>
                                </div>
                            </form>

                            <h2>Danh sách sách</h2>
                            <div class="book-grid">
                                <c:forEach var="book" items="${books}">
                                    <div class="book-card" style="position:relative;">
                                        <div style="position:absolute; top:10px; right:10px; display:flex; gap:5px;">
                                            <button
                                                onclick="editBook('${book.id}', '${book.title}', '${book.author}', '${book.categoryId}', '${book.price}', '${book.stock}')"
                                                style="background:#f1c40f; border:none; padding:5px 10px; cursor:pointer; border-radius:3px;">Sửa</button>
                                            <form action="<c:url value='/admin/books/delete'/>" method="post"
                                                onsubmit="return confirm('Bạn có chắc muốn xóa sách này?');"
                                                style="display:inline;">
                                                <input type="hidden" name="bookId" value="${book.id}" />
                                                <button type="submit"
                                                    style="background:#e74c3c; border:none; padding:5px 10px; cursor:pointer; border-radius:3px; color:white;">Xóa</button>
                                            </form>
                                        </div>
                                        <c:choose>
                                            <c:when test="${fn:startsWith(book.coverUrl, 'http')}">
                                                <img src="${book.coverUrl}" alt="${book.title}" />
                                            </c:when>
                                            <c:otherwise>
                                                <img src="<c:url value='/${book.coverUrl}'/>" alt="${book.title}" />
                                            </c:otherwise>
                                        </c:choose>
                                        <p><strong>
                                                <c:out value="${book.title}" />
                                            </strong></p>
                                        <p>
                                            <c:out value="${book.author}" />
                                        </p>
                                        <p><strong>Giá:</strong>
                                            <fmt:formatNumber value="${book.price}" minFractionDigits="0"
                                                maxFractionDigits="0" /> ₫
                                        </p>
                                        <p><strong>Tồn:</strong>
                                            <c:out value="${book.stock}" />
                                        </p>
                                    </div>
                                </c:forEach>
                            </div>

                            <script>
                                function editBook(id, title, author, category, price, stock) {
                                    document.getElementById('formTitle').innerText = 'Cập nhật sách';
                                    document.getElementById('bookForm').action = "<c:url value='/admin/books/edit'/>";
                                    document.getElementById('bookId').value = id;
                                    document.getElementById('title').value = title;
                                    document.getElementById('author').value = author;
                                    document.getElementById('category').value = category;
                                    document.getElementById('price').value = price;
                                    document.getElementById('stock').value = stock;
                                    document.getElementById('submitBtn').innerText = 'Lưu thay đổi';
                                    window.scrollTo(0, 0);
                                }

                                function resetForm() {
                                    document.getElementById('formTitle').innerText = 'Thêm sách mới';
                                    document.getElementById('bookForm').action = "<c:url value='/admin/books/add'/>";
                                    document.getElementById('bookForm').reset();
                                    document.getElementById('bookId').value = '';
                                    document.getElementById('submitBtn').innerText = 'Thêm sách';
                                }
                            </script>

                </html>