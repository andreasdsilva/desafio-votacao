package com.db.votacao.api.v1.modules.votacao.model.mapper;

import com.db.votacao.api.v1.modules.votacao.model.dto.PautaDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.PautaResultDto;

public class PautaResultMapper {

    public static PautaResultDto toDto(PautaDto pautaDto) {
        return PautaResultDto.builder()
                .pautaId(pautaDto.getId())
                .assembleiaId(pautaDto.getAssembleiaId())
                .votos(pautaDto.getVotos().size())
                .status(pautaDto.getStatus())
                .build();
    }
}
