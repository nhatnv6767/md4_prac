package com.ra.service;

import com.ra.dto.response.PageResponse;

// IBaseService la mot interface chung cho cac service, su dung Generic voi 2 tham so:
// T: Dai dien cho Request DTO - la doi tuong chua du lieu dau vao tu client
// R: Dai dien cho Response DTO - la doi tuong tra ve cho client
// Vi du: 
// - T co the la CreateEmployeeRequest, UpdateEmployeeRequest
// - R co the la EmployeeResponse
public interface IBaseService<T, R> {
    PageResponse<R> getAll(int page, int size, String sortBy, String sortDir);

    R getById(Long id);

    // Tao moi 1 ban ghi
    // - request: du lieu dau vao tu client (kieu T)
    // - tra ve thong tin ban ghi vua tao (kieu R)
    R create(T request);

    // Cap nhat 1 ban ghi
    // - id: id ban ghi can cap nhat
    // - request: du lieu cap nhat tu client (kieu T)
    // - tra ve thong tin ban ghi sau khi cap nhat (kieu R)
    R update(Long id, T request);

    void delete(Long id);
}
