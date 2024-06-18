package com.db.votacao.api.v1.modules.votacao.usecase.impl;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssembleiaDto;
import com.db.votacao.api.v1.modules.votacao.model.entity.Assembleia;
import com.db.votacao.api.v1.modules.votacao.repository.AssembleiaRepository;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.NotFoundException;
import com.db.votacao.api.v1.modules.votacao.shared.util.DtoEntityConverterUtil;
import com.db.votacao.api.v1.modules.votacao.usecase.AssembleiaInteractor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class AssembleiaInteractorImpl implements AssembleiaInteractor {

    private final AssembleiaRepository repository;

    /**
     * Method responsible to create Assembleia object
     * and return persisted object as AssembleiaDto
     *
     * @param assembleiaDto to be created and persisted
     * @return saved AssembleiaDto
     * @throws Exception
     */
    @Override
    public AssembleiaDto create(AssembleiaDto assembleiaDto) throws Exception {
        log.info("Assembleia: método create acionado");

        try {
            Assembleia assembleia = DtoEntityConverterUtil.convertToEntity(assembleiaDto, Assembleia.class);
            Assembleia savedAssembleia = this.repository.save(assembleia);

            return DtoEntityConverterUtil.convertToDto(savedAssembleia, AssembleiaDto.class);
        }
        catch (RuntimeException e) {
            log.error("Assembleia: Erro ao criar Assembleia. Mensagem: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Method responsible to find Assembleia object by id
     * and return AssembleiaDto object
     *
     * @param id assembleia id
     * @return AssembleiaDto object
     * @throws Exception
     */
    @Override
    public AssembleiaDto findById(long id) throws Exception {
        log.info("Assembleia: Método findById  acionado para id: {}", id);

        try {
            Assembleia assembleia = this.repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Assembleia não encontrada para id: " + id));

            return DtoEntityConverterUtil.convertToDto(assembleia, AssembleiaDto.class);
        }
        catch (RuntimeException e) {
            log.error("Assembleia:  Erro ao criar Assembleia. Mensagem: {}", e.getMessage(), e);
            throw e;
        }
    }
}
