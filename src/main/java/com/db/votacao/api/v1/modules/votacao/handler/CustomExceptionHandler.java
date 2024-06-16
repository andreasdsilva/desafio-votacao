package com.db.votacao.api.v1.modules.votacao.handler;

import org.springframework.http.ResponseEntity;

public interface CustomExceptionHandler<T extends RuntimeException> {
    ResponseEntity<String> handle(T exception);
}