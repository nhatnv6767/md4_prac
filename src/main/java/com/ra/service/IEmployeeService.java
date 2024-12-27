package com.ra.service;

import com.ra.dto.request.EmployeeRequest;
import com.ra.dto.response.EmployeeResponse;
import com.ra.dto.response.PageResponse;
import com.ra.model.enums.EmployeeStatus;
import org.springframework.web.multipart.MultipartFile;

public interface IEmployeeService extends IBaseService<EmployeeRequest, EmployeeResponse> {
    PageResponse<EmployeeResponse> searchEmployees(String keyword, Long departmentId, Long positionId, EmployeeStatus status, int page, int size, String sortBy, String sortDir);

    String uploadAvatar(Long id, MultipartFile file);

    void updatePassword(Long id, String oldPassword, String newPassword);

    boolean existsByEmail(String email);
}
