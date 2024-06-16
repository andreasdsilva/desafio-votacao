package com.db.votacao.api.v1.modules.votacao.model.dto;

import com.db.votacao.api.v1.modules.votacao.model.enums.PautaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PautaResultDto {

    private long pautaId;
    private long assembleiaId;
    private long votos;
    private PautaStatus status;
}
