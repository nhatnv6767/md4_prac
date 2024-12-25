package com.ra.repository;

import com.ra.model.entities.Employee;
import com.ra.model.enums.EmployeeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends BaseRepository<Employee> {
    boolean existsByEmail(String email);

    Optional<Employee> findByEmail(String email);

    // Tim kiem nhan vien voi nhieu dieu kien:
    // - Tu khoa (keyword): tim trong firstName, lastName, email
    // - Phong ban (departmentId)
    // - Vi tri - chuc vu (positionId)
    // - Trang thai nhan vien (employeeStatus)
    // Chi tiet query:
    // 1. SELECT e FROM Employee e WHERE e.status = true: Chi lay nhan vien dang
    // hoat dong
    // 2. Dieu kien tim theo keyword:
    // - Neu keyword la NULL thi bo qua dieu kien nay
    // - Neu co keyword thi tim kiem khong phan biet hoa thuong trong:
    // + Ten (firstName)
    // + Ho (lastName)
    // + Email
    // 3. Dk loc theo phong ban:
    // - Neu departmentId la NULL thi bo qua
    // - Neu co departmentId thi loc theo phong ban tuong ung
    // 4. Dieu kien loc theo vi tri:
    // - Neu positionId la NULL thi bo qua
    // - Neu co positionId thi loc theo vi tri tuong ung
    // 5. Dieu kien loc theo trang thai nhan vien:
    // - Neu employeeStatus la NULL thi bo qua
    // - Neu co employeeStatus thi loc theo trang thai tuong ung
    @Query("SELECT e FROM Employee e WHERE e.status = true " +
            "AND (:keyword IS NULL OR " +
            "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:departmentId IS NULL OR e.department.id = :departmentId) " +
            "AND (:positionId IS NULL OR e.position.id = :positionId) " +
            "AND (:employeeStatus IS NULL OR e.employeeStatus = :employeeStatus)")
    Page<Employee> searchEmployees(
            @Param("keyword") String keyword,
            @Param("departmentId") Long departmentId,
            @Param("positionId") Long positionId,
            @Param("employeeStatus") EmployeeStatus employeeStatus,
            Pageable pageable);

    // Tim truong phong cua mot phong ban cu the
    // Chi tiet cau query:
    // 1. SELECT e FROM Employee e WHERE e.status = true: Chi lay nhan vien dang
    // hoat dong
    // 2. e.department.id = :departmentId: Loc theo phong ban duoc chi dinh
    // 3. EXISTS: Kiem tra xem nhan vien co vai tro MANAGER khong
    // Subquery: SELECT r FROM e.roles r WHERE r.name = 'ROLE_MANAGER'
    @Query("SELECT e FROM Employee e WHERE e.status = true " +
            "AND e.department.id = :departmentId " +
            "AND EXISTS (SELECT r FROM e.roles r WHERE r.name = 'ROLE_MANAGER')")
    Optional<Employee> findDepartmentManager(@Param("departmentId") Long departmentId);

    // Kiem tra xem mot nhan vien co thuoc phong ban duoc chi dinh hay khong
    // Chi tiet cau query:
    // 1. SELECT COUNT(e) > 0: Dem so luong ban ghi thoa man dieu kien va tra ve
    // true neu > 0
    // 2. FROM Employee e WHERE e.status = true: Chi kiem tra nhan vien dang hoat
    // dong
    // 3. e.department.id = :departmentId: Kiem tra nhan vien co thuoc phong ban
    // duoc chi dinh
    // 4. e.id = :employeeId: Kiem tra dung nhan vien can tim..
    @Query("SELECT COUNT(e) > 0 FROM Employee e WHERE e.status = true " +
            "AND e.department.id = :departmentId " +
            "AND e.id = :employeeId")
    boolean isEmployeeInDepartment(@Param("employeeId") Long employeeId, @Param("departmentId") Long departmentId);
}
