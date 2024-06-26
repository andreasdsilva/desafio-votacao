package com.db.votacao.api.v1.modules.votacao.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String mensagem) {
        super(mensagem);
    }

    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
