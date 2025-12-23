# Tài Liệu Ôn Tập Vấn Đáp Đồ Án Web Bán Sách

Tài liệu này giải thích chi tiết chức năng của từng file trong dự án, giúp bạn tự tin trả lời các câu hỏi khi đi vấn đáp.

## 1. Cấu Trúc Backend (Java Spring Boot)

### A. Controller (Điều hướng và Xử lý yêu cầu)
Thư mục: `src/main/java/com/example/demo/controller`

#### `AdminController.java`
*   **Vai trò:** Quản lý toàn bộ khu vực Admin.
*   **Chức năng chính:**
    *   `/admin`: Trang Dashboard chính với 3 lựa chọn (Sách, Loại, Thống kê).
    *   `/admin/books`: Hiển thị danh sách sách. Xử lý thêm, sửa, xóa sách.
    *   `/admin/orders`: Quản lý đơn hàng (Duyệt/Refund). Sau khi xử lý sẽ tự động chuyển hướng lại trang đơn hàng để tiếp tục làm việc.
    *   `/admin/categories`: Quản lý CRUD (Thêm/Sửa/Xóa) loại sách.
    *   `/admin/stats`: Tính toán doanh thu. Sử dụng `OrderRepository` để lấy tổng tiền và dữ liệu biểu đồ.
    *   **Điểm nhấn:** Hàm `saveImage` xử lý upload ảnh, lưu vào `src/main/resources/static/image_sach/`.

#### `AuthController.java`
*   **Vai trò:** Xử lý xác thực người dùng (Đăng nhập, Đăng ký).
*   **Chức năng chính:**
    *   `/login`: Kiểm tra username/password qua `AuthService`. Lưu user vào `HttpSession`.
    *   `/register`: Nhận dữ liệu từ form đăng ký (bao gồm username, họ tên, địa chỉ, sđt...).
    *   **Điểm nhấn Login:** Kiểm tra cả bảng `Admin` và `KhachHang`.
    *   **Điểm nhấn Register:** Xử lý Captcha và xử lý Default Value nếu form cũ chưa cập nhật trường mới.

#### `IndexController.java`
*   **Vai trò:** Trang chủ và các trang công khai.
*   **Chức năng chính:** Hiển thị danh sách sách (`/`), trang chi tiết sách, giỏ hàng.

---

### B. Service (Xử lý Logic nghiệp vụ)
Thư mục: `src/main/java/com/example/demo/service`

#### `AuthService.java`
*   **Vai trò:** Logic đăng nhập và đăng ký chuyên sâu.
*   **Điểm quan trọng:**
    *   `authenticate`: So sánh password text trơn (hoặc MD5 nếu có). Trả về đối tượng `User` chung cho cả Admin và Khách.
    *   `register`: **Tự động sinh ID** cho khách hàng mới (`findMaxId() + 1`) vì DB không có Auto Increment. Điền các giá trị mặc định cho `FullName`, `Address` nếu thiếu.

#### `BookService.java`
*   **Vai trò:** Thêm, sửa, xóa Sách.
*   **Logic:** Gọi `BookRepository` để thao tác DB. Kiểm tra tồn tại trước khi Update.

#### `OrderService.java`
*   **Vai trò:** Xử lý Đơn hàng.
*   **Chức năng:** Tạo đơn hàng mới từ Giỏ hàng (`Order` + `OrderItem`). Chuyển đổi `Order` entity sang `OrderDTO` để hiển thị tên khách hàng và tổng tiền trên giao diện.

---

### C. Repository (Giao tiếp Database)
Thư mục: `src/main/java/com/example/demo/repository`

#### `OrderRepository.java`
*   **Đặc biệt:** Dùng **Native SQL Query** (câu lệnh SQL thuần) để tính doanh thu.
    *   `sumTotalByStatus`: `SELECT SUM(gia * soluong)...` - Tính tổng tiền các đơn đã mua.
    *   `getRevenueOverTime`: Group by ngày để vẽ biểu đồ.
    *   *Lý do dùng SQL thuần:* Để join 3 bảng (`hoadon`, `chitiethoadon`, `sach`) tính tiền chính xác do bảng `Order` không lưu tổng tiền.

#### `CustomerRepository.java`
*   **Đặc biệt:** Có hàm `findMaxId` (`SELECT MAX(id)...`) để hỗ trợ việc tự tăng ID thủ công ở `AuthService`.

---

### D. Model (Thực thể - Entity)
Thư mục: `src/main/java/com/example/demo/model`

*   **`Book.java`**: Map bảng `sach`.
*   **`Category.java`**: Map bảng `loai`.
*   **`Customer.java`**: Map bảng `KhachHang`. Chú ý đã bỏ `@GeneratedValue` để tự set ID.
*   **`Order.java`** (`HOADON`) & **`OrderItem.java`** (`ChiTietHoaDon`): Cấu trúc đơn hàng.
*   **`OrderDTO.java`**: Class phụ (không phải bảng DB) dùng để gói dữ liệu gửi ra Admin (gồm cả tên user, tổng tiền đã tính toán).

---

## 2. Cấu Trúc Frontend (JSP)
Thư mục: `src/main/webapp/WEB-INF/jsp`

### Khu vực Admin
*   **`admin.jsp`**: **(Mới)** Dashboard chính với 3 nút lớn.
*   **`admin_books.jsp`**: Quản lý Sách. Có form upload ảnh (`multipart/form-data`) và Script chỉnh sửa sách.
*   **`admin_orders.jsp`**: **(Mới)** Quản lý đơn hàng chuyên sâu (Duyệt/Refund).
*   **`admin_categories.jsp`**: **(Mới)** Trang CRUD Loại sách.
*   **`admin_stats.jsp`**: **(Mới)** Trang Thống kê. Dùng thư viện `Chart.js` để vẽ biểu đồ doanh thu từ dữ liệu Controller gửi về.

### Khu vực User
*   **`index.jsp`**: Trang chủ bán hàng.
*   **`register.jsp`**: Form đăng ký. Đã thêm các trường `Họ tên`, `Địa chỉ`, `SĐT` và hiển thị thông báo lỗi cụ thể (dòng chữ đỏ) khi lỗi 500.

---

## 3. Cấu Hình
#### `application.properties`
*   Chứa thông tin kết nối MS SQL Server (`spring.datasource.url`).
*   Cấu hình `PhysicalNamingStrategyStandardImpl` để Hibernate không tự đổi tên bảng thành snake_case (giữ nguyên tên bảng SQL Server).
*   Cấu hình Encoding UTF-8 để không lỗi font tiếng Việt.

## Câu hỏi Vấn đáp Thường gặp

1.  **"Tại sao đăng ký lại cần tự tăng ID?"**
    *   *Trả lời:* Do bảng `KhachHang` trong DB không thiết lập thuộc tính Identity (tự tăng), nên code phải tự tìm ID lớn nhất và cộng 1.

2.  **"Làm sao tính được doanh thu khi bảng Hóa Đơn không có cột Tổng tiền?"**
    *   *Trả lời:* Em dùng câu lệnh SQL Join qua 3 bảng: Hóa đơn -> Chi tiết -> Sách, sau đó Sum(Giá * Số lượng). Logic này nằm trong `OrderRepository`.

3.  **"Cơ chế upload ảnh hoạt động thế nào?"**
    *   *Trả lời:* Client gửi form `multipart`. Server nhận file, lưu vào thư mục `src/.../static/image_sach/` và lưu đường dẫn tương đối vào Database.
