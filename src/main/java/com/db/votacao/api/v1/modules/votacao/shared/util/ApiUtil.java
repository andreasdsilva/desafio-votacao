package com.db.votacao.api.v1.modules.votacao.shared.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiUtil {

    public static final String VERSION = "api/v1/";

    public static <T> ResponseEntity<T> ok() {
        return new ResponseEntity<>( HttpStatus.OK );
    }

    public static <T> ResponseEntity<T> ok( T body ) {
        return new ResponseEntity<>( body, HttpStatus.OK );
    }

    public static <T> ResponseEntity<T> created( T body ) {
        return new ResponseEntity<>( body, HttpStatus.CREATED );
    }

    public static <T> ResponseEntity<T> response( T body, HttpStatus httpStatus ) {
        return new ResponseEntity<>( body, httpStatus );
    }
}
