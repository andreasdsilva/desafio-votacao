package com.db.votacao.api.v1.modules.votacao.handler.exception;

import com.db.votacao.api.v1.modules.votacao.handler.CustomExceptionHandler;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.NotFoundException;
import com.db.votacao.api.v1.modules.votacao.shared.util.ApiUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundExceptionHandler implements CustomExceptionHandler<NotFoundException> {

    @Override
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handle(NotFoundException ex) {
        return ApiUtil.response(ex.getMessage(), ex.getStatus());
    }
}