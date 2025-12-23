package com.example.demo.repository;

import com.example.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param; // Import added
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);

    @org.springframework.data.jpa.repository.Query(value = "SELECT SUM(s.gia * c.SoLuongMua) FROM hoadon h JOIN ChiTietHoaDon c ON h.MaHoaDon = c.MaHoaDon JOIN sach s ON c.MaSach = s.masach WHERE h.damua = :status", nativeQuery = true)
    java.math.BigDecimal sumTotalByStatus(@Param("status") String status);

    // For chart: List of [Date, Total]
    @org.springframework.data.jpa.repository.Query(value = "SELECT CAST(h.NgayMua AS DATE), SUM(s.gia * c.SoLuongMua) FROM hoadon h JOIN ChiTietHoaDon c ON h.MaHoaDon = c.MaHoaDon JOIN sach s ON c.MaSach = s.masach WHERE h.damua = 'APPROVED' GROUP BY CAST(h.NgayMua AS DATE) ORDER BY CAST(h.NgayMua AS DATE)", nativeQuery = true)
    List<Object[]> getRevenueOverTime();

    @org.springframework.data.jpa.repository.Query("SELECT MAX(o.id) FROM Order o")
    Long findMaxId();

    // New queries for order management
    List<Order> findByStatus(String status);

    long countByStatus(String status);
}
