package com.db.votacao.api.v1.modules.votacao.usecase;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssembleiaDto;

public interface AssembleiaInteractor {

    AssembleiaDto create(AssembleiaDto assembleiaDto) throws Exception;
    AssembleiaDto findById(long id) throws Exception;
}
