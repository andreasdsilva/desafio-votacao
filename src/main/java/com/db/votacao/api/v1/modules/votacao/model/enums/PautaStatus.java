package com.db.votacao.api.v1.modules.votacao.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PautaStatus {

    WAITING("Aguardando"),
    OPEN("Aberta"),
    NULLIFIED("Anulada"),
    APPROVED("Aprovada"),
    REPROVED("Reprovada"),
    DRAW("Empatada");

    private final String valor;

    PautaStatus(String valor) {
        this.valor = valor;
    }

    @JsonValue
    public String getValor() {
        return valor;
    }
}
