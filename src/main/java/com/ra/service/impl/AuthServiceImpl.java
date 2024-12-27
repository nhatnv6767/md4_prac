package com.ra.service.impl;

import com.ra.dto.request.LoginRequest;
import com.ra.dto.request.RegisterRequest;
import com.ra.dto.response.AuthResponse;
import com.ra.model.entities.Employee;
import com.ra.repository.EmployeeRepository;
import com.ra.repository.RoleRepository;
import com.ra.security.jwt.JwtService;
import com.ra.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;


    @Override
    public AuthResponse login(LoginRequest request) {
        Employee employee = employeeRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("Invalid email or password")
        );
        if (!passwordEncoder.matches(request.getPassword(), employee.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }
//        String accessToken = jwtService
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        return null;
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        return null;
    }
}
