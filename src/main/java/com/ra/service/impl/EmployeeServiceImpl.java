package com.ra.service.impl;

import com.ra.dto.request.EmployeeRequest;
import com.ra.dto.response.EmployeeResponse;
import com.ra.dto.response.PageResponse;
import com.ra.model.enums.EmployeeStatus;
import com.ra.repository.DepartmentRepository;
import com.ra.repository.EmployeeRepository;
import com.ra.repository.PositionRepository;
import com.ra.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;


    @Override
    public PageResponse<EmployeeResponse> searchEmployees(String keyword, Long departmentId, Long positionId, EmployeeStatus status, int page, int size, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public String uploadAvatar(Long id, MultipartFile file) {
        return "";
    }

    @Override
    public void updatePassword(Long id, String oldPassword, String newPassword) {

    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public PageResponse<EmployeeResponse> getAll(int page, int size, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public EmployeeResponse getById(Long id) {
        return null;
    }

    @Override
    public EmployeeResponse create(EmployeeRequest request) {
        return null;
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
