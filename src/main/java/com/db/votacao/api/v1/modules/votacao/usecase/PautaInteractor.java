package com.db.votacao.api.v1.modules.votacao.usecase;

import com.db.votacao.api.v1.modules.votacao.model.dto.PautaDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.PautaResultDto;

public interface PautaInteractor {
    PautaDto create(PautaDto pautaDto) throws Exception;

    PautaResultDto getPautaResult(Long id) throws Exception;

    PautaDto findById(long id) throws Exception;
}
