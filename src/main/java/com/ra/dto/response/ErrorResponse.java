package com.ra.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse extends BaseResponse {
    private String errorCode;
    private List<String> errors;

    public ErrorResponse(boolean b, String message, String name, Object o) {
    }
}
