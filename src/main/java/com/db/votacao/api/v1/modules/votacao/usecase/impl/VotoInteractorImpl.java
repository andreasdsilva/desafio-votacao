package com.db.votacao.api.v1.modules.votacao.usecase.impl;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssociadoDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.PautaDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.VotoDto;
import com.db.votacao.api.v1.modules.votacao.model.entity.Voto;
import com.db.votacao.api.v1.modules.votacao.model.enums.AssociadoStatus;
import com.db.votacao.api.v1.modules.votacao.model.enums.PautaStatus;
import com.db.votacao.api.v1.modules.votacao.model.mapper.VotoMapper;
import com.db.votacao.api.v1.modules.votacao.repository.VotoRepository;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.BadRequestException;
import com.db.votacao.api.v1.modules.votacao.shared.util.DateTimeUtil;
import com.db.votacao.api.v1.modules.votacao.usecase.AssociadoInteractor;
import com.db.votacao.api.v1.modules.votacao.usecase.PautaInteractor;
import com.db.votacao.api.v1.modules.votacao.usecase.VotoInteractor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Service
public class VotoInteractorImpl implements VotoInteractor {

    private final VotoRepository repository;
    private final AssociadoInteractor associadoInteractor;
    private final PautaInteractor pautaInteractor;

    /**
     * Method responsible to create Voto by VotoDto
     * calling validating method and returning
     * persisted voto as VotoDto
     *
     * @param votoDto to create Voto Object
     * @return persisted VotoDto
     * @throws Exception
     */
    @Override
    public VotoDto create(VotoDto votoDto) throws Exception {
        log.info("Voto: Método create acionado");

        try {
            AssociadoDto associadoDto = associadoInteractor.findByDocumento(votoDto.getAssociadoDocumento());
            PautaDto pautaDto = pautaInteractor.findById(votoDto.getPautaId());

            validateVoto(associadoDto, pautaDto);

            Voto voto = VotoMapper.dtoToEntity(votoDto, pautaDto, associadoDto);
            Voto savedVoto = this.repository.save(voto);

            return VotoMapper.entityToDto(savedVoto);
        }
        catch (RuntimeException e) {
            log.error("Voto: Erro ao criar Voto. Mensagem: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Method responsible to call
     * isolated validating methods for each
     * object
     *
     * @param associadoDto to be validated
     * @param pautaDto to be validated
     */
    private void validateVoto(AssociadoDto associadoDto, PautaDto pautaDto) {
        validatePauta(pautaDto);
        validateAssociado(associadoDto, pautaDto);
    }

    /**
     * Method responsible to validate pautaDto
     * and throws BadRequestException if it's not valid
     *
     * @param pautaDto to be validated
     */
    private void validatePauta(PautaDto pautaDto) {
        if(pautaDto.getStatus().equals(PautaStatus.WAITING)) {
            throw new BadRequestException("Pauta em aguardo, aguardar até " + DateTimeUtil.getString(pautaDto.getStartTime()));
        }

        if(!pautaDto.getStatus().equals(PautaStatus.OPEN)) {
            throw new BadRequestException("Pauta já encerrada!");
        }
    }

    /**
     * Method responsible to validate if
     * associadoDto already voted, and it is valid
     * and throws BadRequestException if it's not valid
     *
     * @param pautaDto to be validated if associado already voted
     * @param associadoDto to be validated
     */
    private void validateAssociado(AssociadoDto associadoDto, PautaDto pautaDto) {
        if(associadoDto.getStatus() == AssociadoStatus.UNABLE_TO_VOTE) {
            throw new BadRequestException("Associado impossibilitado de votar!");
        }

        if(pautaDto.getVotos().stream().anyMatch(x -> Objects.equals(x.getAssociado().getId(), associadoDto.getId()))) {
            throw new BadRequestException("Voto de associado já contabilizado!");
        }
    }
}
