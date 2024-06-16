package com.db.votacao.api.v1.modules.votacao.usecase.impl;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssociadoDto;
import com.db.votacao.api.v1.modules.votacao.model.entity.Associado;
import com.db.votacao.api.v1.modules.votacao.model.enums.AssociadoStatus;
import com.db.votacao.api.v1.modules.votacao.repository.AssociadoRepository;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.NotFoundException;
import com.db.votacao.api.v1.modules.votacao.shared.util.DtoEntityConverterUtil;
import com.db.votacao.api.v1.modules.votacao.usecase.AssociadoInteractor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AssociadoInteractorImpl implements AssociadoInteractor {

    private final AssociadoRepository repository;

    @Override
    public AssociadoDto create(AssociadoDto associadoDto) throws Exception {
        associadoDto.setStatus(AssociadoStatus.ABLE_TO_VOTE);
        Associado associado = DtoEntityConverterUtil.convertToEntity(associadoDto, Associado.class);
        Associado savedAssociado = this.repository.save(associado);

       return DtoEntityConverterUtil.convertToDto(savedAssociado, AssociadoDto.class);
    }

    @Override
    public AssociadoDto findByDocumento(String doc) throws Exception {
        Associado associado = this.repository.findByDocumento(doc).orElseThrow(() -> new NotFoundException("Associado não encontrada para documento: " + doc));

        return DtoEntityConverterUtil.convertToDto(associado, AssociadoDto.class);
    }
}
