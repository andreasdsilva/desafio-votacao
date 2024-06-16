package com.db.votacao.api.v1.modules.votacao.usecase;

import com.db.votacao.api.v1.modules.votacao.model.dto.VotoDto;

public interface VotoInteractor {

    VotoDto create(VotoDto votoDto) throws Exception;
}