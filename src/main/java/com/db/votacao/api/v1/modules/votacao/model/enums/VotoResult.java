package com.db.votacao.api.v1.modules.votacao.model.enums;

public enum VotoResult {
    SIM("Sim"),
    NAO("Não"),
    NULO("Nulo"),
    BRANCO("Branco");

    private final String value;

    VotoResult(String voto ) {
        this.value = voto;
    }

    public String getValue() {
        return value;
    }
}