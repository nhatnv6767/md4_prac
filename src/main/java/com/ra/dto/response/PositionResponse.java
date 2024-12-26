package com.ra.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionResponse {
    private Long id;
    private String name;
    private String description;
    private int employeeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
