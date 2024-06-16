package com.db.votacao.api.v1.modules.votacao.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AssociadoStatus {

    ABLE_TO_VOTE("PODE VOTAR"),
    UNABLE_TO_VOTE("NÃO PODE VOTAR");

    private final String valor;

    AssociadoStatus(String value) {
        this.valor = value;
    }

    @JsonValue
    public String getStatus() {
        return valor;
    }
}
