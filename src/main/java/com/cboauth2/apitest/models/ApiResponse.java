package com.cboauth2.apitest.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class ApiResponse {
    @Getter @Setter
    private int status;
    @Getter @Setter
    private boolean ok;
    @Getter @Setter
    private String tokenJWT;
    @Getter @Setter
    private Object data;
}
