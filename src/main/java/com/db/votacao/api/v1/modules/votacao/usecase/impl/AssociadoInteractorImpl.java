package com.db.votacao.api.v1.modules.votacao.usecase.impl;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssociadoDto;
import com.db.votacao.api.v1.modules.votacao.model.entity.Associado;
import com.db.votacao.api.v1.modules.votacao.model.enums.AssociadoStatus;
import com.db.votacao.api.v1.modules.votacao.repository.AssociadoRepository;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.BadRequestException;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.NotFoundException;
import com.db.votacao.api.v1.modules.votacao.shared.util.DtoEntityConverterUtil;
import com.db.votacao.api.v1.modules.votacao.usecase.AssociadoInteractor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class AssociadoInteractorImpl implements AssociadoInteractor {

    private final AssociadoRepository repository;

    /**
     * Method responsible to create Associado by AssociadoDto
     *
     * @param associadoDto to create Associado object
     * @return persisted AssociadoDto
     * @throws Exception
     */
    @Override
    public AssociadoDto create(AssociadoDto associadoDto) throws Exception {
        log.info("Associado: Método create associado acionado");

        try {
            validateExistingAssociado(associadoDto.getDocumento());

            associadoDto.setStatus(AssociadoStatus.ABLE_TO_VOTE);
            Associado associado = DtoEntityConverterUtil.convertToEntity(associadoDto, Associado.class);
            Associado savedAssociado = this.repository.save(associado);

            return DtoEntityConverterUtil.convertToDto(savedAssociado, AssociadoDto.class);
        }
        catch (RuntimeException e) {
            log.error("Associado: Erro ao criar Associado. Mensagem: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Validate if exists Associado
     * with same documento
     *
     * @param documento 
     */
    private void validateExistingAssociado(String documento) {
        if(this.repository.findByDocumento(documento).isEmpty()) {
            return;
        }

        throw new BadRequestException("Associado ja existente para documento: " + documento);
    }

    /**
     * Method responsible to find Associado by documento
     * and return AssociadoDto objecto
     *
     * @param doc associado documento
     * @return AssociadoDto objecto
     * @throws Exception
     */
    @Override
    public AssociadoDto findByDocumento(String doc) throws Exception {
        log.info("Associado: Método findByDocumento acionado");
        try {
            Associado associado = this.repository.findByDocumento(doc)
                    .orElseThrow(() -> new NotFoundException("Associado não encontrada para documento: " + doc));

            return DtoEntityConverterUtil.convertToDto(associado, AssociadoDto.class);
        }
        catch (RuntimeException e) {
            log.error("Associado: Erro ao encontrar Associado com documento: {}. Mensagem: {}", doc, e.getMessage());
            throw e;
        }
    }
}
