package com.mainline.backend_parsor.dto;

import lombok.Data;

@Data
public class SqlResponse {
    private String value;
    private TokenType type;

    public SqlResponse(String value, TokenType type) {
        this.value = value;
        this.type = type;
    }
}
