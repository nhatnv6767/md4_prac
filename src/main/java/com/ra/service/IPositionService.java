package com.ra.service;

import com.ra.dto.request.DepartmentRequest;
import com.ra.dto.request.PositionRequest;
import com.ra.dto.response.DepartmentResponse;
import com.ra.dto.response.PageResponse;
import com.ra.dto.response.PositionResponse;

public interface IPositionService extends IBaseService<PositionRequest, PositionResponse> {
    PageResponse<PositionResponse> searchPositions(String name, int page, int size);

    boolean existsByName(String name);
}
