<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8" />
            <title>Quản lý loại sách</title>
            <link rel="stylesheet" href="<c:url value='/css/styles.css'/>" />
        </head>

        <body>
            <jsp:include page="/WEB-INF/jsp/header.jsp" />

            <div class="content">
                <a href="<c:url value='/admin'/>">&larr; Quay lại Dashboard</a>
                <h1>Quản lý loại sách</h1>

                <div style="display: flex; gap: 30px; margin-top: 20px;">
                    <div style="flex: 1;">
                        <h2 id="formTitle">Thêm loại mới</h2>
                        <form id="catForm" action="<c:url value='/admin/categories/add'/>" method="post">
                            <input type="hidden" name="id" id="catId" />
                            <div><label>Mã loại</label><input type="text" name="id_input" id="id_input" required />
                            </div>
                            <div><label>Tên loại</label><input type="text" name="name" id="name" required /></div>
                            <button type="submit" class="primary" id="submitBtn">Thêm danh mục</button>
                            <button type="button" onclick="resetForm()" style="margin-left:5px;">Hủy</button>
                        </form>
                    </div>

                    <div style="flex: 2;">
                        <h2>Danh sách loại</h2>
                        <table class="cart-table">
                            <thead>
                                <tr>
                                    <th>Mã loại</th>
                                    <th>Tên loại</th>
                                    <th>Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="cat" items="${categories}">
                                    <tr>
                                        <td>
                                            <c:out value="${cat.id}" />
                                        </td>
                                        <td>
                                            <c:out value="${cat.name}" />
                                        </td>
                                        <td>
                                            <button onclick="editCategory('${cat.id}', '${cat.name}')"
                                                style="background:#f1c40f; border:none; padding:5px 10px; cursor:pointer; border-radius:3px;">Sửa</button>
                                            <form action="<c:url value='/admin/categories/delete'/>" method="post"
                                                onsubmit="return confirm('Xóa danh mục này?');" style="display:inline;">
                                                <input type="hidden" name="id" value="${cat.id}" />
                                                <button type="submit"
                                                    style="background:#e74c3c; border:none; padding:5px 10px; cursor:pointer; border-radius:3px; color:white;">Xóa</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <script>
                function editCategory(id, name) {
                    document.getElementById('formTitle').innerText = 'Cập nhật loại: ' + id;
                    document.getElementById('catForm').action = "<c:url value='/admin/categories/edit'/>";
                    // ID field is PK, generally shouldn't change, but we pass it as hidden
                    document.getElementById('catId').value = id;

                    // Disable ID input or make it readonly for visual context
                    var idInput = document.getElementById('id_input');
                    idInput.value = id;
                    idInput.setAttribute('readonly', 'readonly');

                    document.getElementById('name').value = name;
                    document.getElementById('submitBtn').innerText = 'Lưu thay đổi';
                }

                function resetForm() {
                    document.getElementById('formTitle').innerText = 'Thêm loại mới';
                    document.getElementById('catForm').action = "<c:url value='/admin/categories/add'/>";
                    document.getElementById('catId').value = '';

                    var idInput = document.getElementById('id_input');
                    idInput.value = '';
                    idInput.removeAttribute('readonly');

                    document.getElementById('name').value = '';
                    document.getElementById('submitBtn').innerText = 'Thêm danh mục';
                }
            </script>

            <jsp:include page="/WEB-INF/jsp/footer.jsp" />
        </body>

        </html>