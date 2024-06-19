package com.db.votacao.api.v1.modules.votacao.usecase.impl;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssembleiaDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.PautaDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.PautaResultDto;
import com.db.votacao.api.v1.modules.votacao.model.entity.Pauta;
import com.db.votacao.api.v1.modules.votacao.model.mapper.PautaResultMapper;
import com.db.votacao.api.v1.modules.votacao.repository.PautaRepository;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.BadRequestException;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.NotFoundException;
import com.db.votacao.api.v1.modules.votacao.shared.util.DtoEntityConverterUtil;
import com.db.votacao.api.v1.modules.votacao.usecase.AssembleiaInteractor;
import com.db.votacao.api.v1.modules.votacao.usecase.PautaInteractor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class PautaInteractorImpl implements PautaInteractor {

    private final PautaRepository repository;
    private final AssembleiaInteractor assembleiaInteractor;

    /**
     * Method responsible to create Pauta by PautaDto
     * calling validating method and returning
     * persisted pauta as PautaDto
     *
     * @param pautaDto to create Pauta Object
     * @return persisted PautaDto
     * @throws Exception
     */
    @Override
    public PautaDto create(PautaDto pautaDto) throws Exception {
        log.info("Pauta: Método create acionado");

        try {
            AssembleiaDto assembleiaDto = assembleiaInteractor.findById(pautaDto.getAssembleiaId());
            Pauta pauta = DtoEntityConverterUtil.convertToEntity(pautaDto, Pauta.class);

            validateDate(pauta, assembleiaDto);

            return DtoEntityConverterUtil.convertToDto(this.repository.save(pauta), PautaDto.class);
        }
        catch (RuntimeException e) {
            log.error("Pauta: Erro criarPauta. Mensagem: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Method responsible to find Pauta by id
     * and return populated PautaResultDto
     *
     * @param id Pauta Id
     * @return PautaResultDto Object
     * @throws Exception
     */
    @Override
    public PautaResultDto getPautaResult(Long pautaId) throws Exception {
        log.info("Pauta: Método getPautaResult acionado");
        try {
            PautaDto pautaDto = this.findById(pautaId);

            return PautaResultMapper.toDto(pautaDto);
        }
        catch (RuntimeException e) {
            log.error("Pauta: Erro gerar PautaResult com id pauta: {}. Mensagem: {}", pautaId, e.getMessage());
            throw e;
        }
    }

    /**
     * Method responsible to find Pauta
     * and return PautaDto by its id
     *
     * @param id pauta id
     * @return PautaDto Object
     * @throws Exception
     */
    @Override
    public PautaDto findById(long id) throws Exception {
        log.info("Pauta: Método findById acionado");
        try {
            Pauta pauta = this.repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Pauta não encontrada para id: " + id));

            return DtoEntityConverterUtil.convertToDto(pauta, PautaDto.class);
        }
        catch (RuntimeException e) {
            log.error("Associado: Erro ao encontrar Pauta com id: {}. Mensagem: {}", id, e.getMessage());
            throw e;
        }
    }

    /**
     * Method responsible to validate pauta and
     * assembleia dates
     *
     * @param pauta PautaDto Object
     * @param assembleiaDto assembleiaDto object
     */
    private void validateDate(Pauta pauta, AssembleiaDto assembleiaDto) {
        if(pauta.getStartTime().toLocalDate().isBefore(assembleiaDto.getStartDate())
                || pauta.getEndTime().toLocalDate().isAfter(assembleiaDto.getEndDate())) {
            throw new BadRequestException("Data de votação da pauta deve ser entre a data da assembleia!");
        }
    }
}
