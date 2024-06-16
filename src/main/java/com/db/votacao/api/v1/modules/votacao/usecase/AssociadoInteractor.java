package com.db.votacao.api.v1.modules.votacao.usecase;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssociadoDto;

public interface AssociadoInteractor {
    AssociadoDto create(AssociadoDto pautaDto) throws Exception;
    AssociadoDto findByDocumento(String documento) throws Exception;
}
