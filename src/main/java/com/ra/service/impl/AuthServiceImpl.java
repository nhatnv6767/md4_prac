package com.ra.service.impl;

import com.ra.dto.request.LoginRequest;
import com.ra.dto.request.RegisterRequest;
import com.ra.dto.response.AuthResponse;
import com.ra.dto.response.EmployeeResponse;
import com.ra.exception.EmailAlreadyExistsException;
import com.ra.exception.InvalidJwtTokenException;
import com.ra.exception.ResourceNotFoundException;
import com.ra.model.entities.Employee;
import com.ra.model.enums.RoleType;
import com.ra.repository.DepartmentRepository;
import com.ra.repository.EmployeeRepository;
import com.ra.repository.PositionRepository;
import com.ra.repository.RoleRepository;
import com.ra.security.jwt.JwtService;
import com.ra.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final PositionRepository positionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    // Phuong thuc login:
    // 1. Nhan vao thong tin dang nhap tu LoginRequest gom email va password
    // 2. Tim kiem nhan vien theo email trong database
    // - Neu khong tim thay se nem ra loi UsernameNotFoundException
    // 3. So sanh mat khau nguoi dung nhap vao voi mat khau da ma hoa trong database
    // - Neu khong khop se nem ra loi BadCredentialsException
    // 4. Neu thong tin hop le:
    // - Tao access token moi voi thoi han ngan
    // - Tao refresh token moi voi thoi han dai
    // 5. Tra ve AuthResponse bao gom:
    // - Trang thai thanh cong
    // - Thong bao dang nhap thanh cong
    // - Access token va refresh token moi tao
    // - Thong tin nhan vien da duoc chuyen doi sang EmployeeResponse
    @Override
    public AuthResponse login(LoginRequest request) {
        Employee employee = employeeRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("Invalid email or password"));
        if (!passwordEncoder.matches(request.getPassword(), employee.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }
        // String accessToken = jwtService
        String accessToken = jwtService.generateToken((UserDetails) employee);
        String refreshToken = jwtService.generateRefreshToken((UserDetails) employee);

        return AuthResponse.builder()
                .success(true)
                .message("Login successful")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .employee(modelMapper.map(employee, EmployeeResponse.class))
                .build();
    }

    // Phuong thuc register:
    // 1. Nhan vao thong tin dang ky tu RegisterRequest
    // 2. Kiem tra email da ton tai trong he thong chua
    // - Neu da ton tai thi nem ra loi EmailAlreadyExistsException
    // 3. Tao doi tuong Employee moi voi:
    // - Thong tin co ban: ho, ten, email
    // - Mat khau duoc ma hoa bang BCrypt
    // - Tim va gan phong ban theo departmentId
    // - Tim va gan vi tri theo positionId
    // - Gan quyen mac dinh la ROLE_EMPLOYEE
    // 4. Luu nhan vien moi vao database
    // 5. Tao access token va refresh token cho nhan vien
    // 6. Tra ve AuthResponse bao gom:
    // - Trang thai thanh cong
    // - Thong bao dang ky thanh cong
    // - Access token va refresh token
    // - Thong tin nhan vien da duoc chuyen doi
    @Override
    public AuthResponse register(RegisterRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .department(departmentRepository.findById(request.getDepartmentId())
                        .orElseThrow(() -> new ResourceNotFoundException("Department not found")))
                .position(positionRepository.findById(request.getPositionId())
                        .orElseThrow(() -> new ResourceNotFoundException("Position not found")))
                .roles(Set.of(roleRepository.findByName(RoleType.ROLE_EMPLOYEE)
                        .orElseThrow(() -> new ResourceNotFoundException("Default role not found"))))
                .build();

        employeeRepository.save(employee);

        // gen token
        String accessToken = jwtService.generateToken((UserDetails) employee);
        String refreshToken = jwtService.generateRefreshToken((UserDetails) employee);

        return AuthResponse.builder()
                .success(true)
                .message("Register successful")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .employee(modelMapper.map(employee, EmployeeResponse.class))
                .build();
    }

    // Phuong thuc refreshToken:
    // 1. Nhan vao refresh token can lam moi
    // 2. Trich xuat email tu refresh token
    // 3. Tim kiem nhan vien theo email
    // - Neu khong tim thay thi nem ra loi UsernameNotFoundException
    // 4. Kiem tra refresh token co hop le va chua het han
    // - Neu khong hop le thi nem ra loi InvalidJwtTokenException
    // 5. Neu refresh token hop le:
    // - Tao access token moi
    // - Giu nguyen refresh token cu
    // 6. Tra ve AuthResponse bao gom:
    // - Trang thai thanh cong
    // - Thong bao lam moi token thanh cong
    // - Access token moi va refresh token cu
    // - Thong tin nhan vien da duoc cap nhat
    @Override
    public AuthResponse refreshToken(String refreshToken) {
        String email = jwtService.extractUsername(refreshToken);
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!jwtService.isTokenValid(refreshToken, (UserDetails) employee)) {
            throw new InvalidJwtTokenException("Invalid refresh token");
        }

        String accessToken = jwtService.generateToken((UserDetails) employee);
        return AuthResponse.builder()
                .success(true)
                .message("Refresh token successful")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .employee(modelMapper.map(employee, EmployeeResponse.class))
                .build();
    }
}
