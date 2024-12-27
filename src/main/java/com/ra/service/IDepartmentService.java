package com.ra.service;

import com.ra.dto.request.DepartmentRequest;
import com.ra.dto.response.DepartmentResponse;
import com.ra.dto.response.PageResponse;

public interface IDepartmentService extends IBaseService<DepartmentRequest, DepartmentResponse> {
    PageResponse<DepartmentResponse> searchDepartments(String name, int page, int size);

    boolean existsByName(String name);
}
