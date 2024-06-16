package com.db.votacao.api.v1.modules.votacao.usecase.impl;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssembleiaDto;
import com.db.votacao.api.v1.modules.votacao.model.entity.Assembleia;
import com.db.votacao.api.v1.modules.votacao.repository.AssembleiaRepository;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.NotFoundException;
import com.db.votacao.api.v1.modules.votacao.shared.util.DtoEntityConverterUtil;
import com.db.votacao.api.v1.modules.votacao.usecase.AssembleiaInteractor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AssembleiaInteractorImpl implements AssembleiaInteractor {

    private final AssembleiaRepository repository;

    @Override
    public AssembleiaDto create(AssembleiaDto assembleiaDto) throws Exception {
       Assembleia assembleia = DtoEntityConverterUtil.convertToEntity(assembleiaDto, Assembleia.class);
       Assembleia savedAssembleia = this.repository.save(assembleia);

       return DtoEntityConverterUtil.convertToDto(savedAssembleia, AssembleiaDto.class);
    }

    @Override
    public AssembleiaDto findById(long id) throws Exception {
        Assembleia assembleia = this.repository.findById(id).orElseThrow(() -> new NotFoundException("Assembleia não encontrada para id: " + id));

        return DtoEntityConverterUtil.convertToDto(assembleia, AssembleiaDto.class);
    }
}
