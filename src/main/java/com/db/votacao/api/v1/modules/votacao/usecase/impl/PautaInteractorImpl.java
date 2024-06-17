package com.db.votacao.api.v1.modules.votacao.usecase.impl;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssembleiaDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.PautaDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.PautaResultDto;
import com.db.votacao.api.v1.modules.votacao.model.entity.Pauta;
import com.db.votacao.api.v1.modules.votacao.repository.PautaRepository;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.BadRequestException;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.NotFoundException;
import com.db.votacao.api.v1.modules.votacao.shared.util.DtoEntityConverterUtil;
import com.db.votacao.api.v1.modules.votacao.usecase.AssembleiaInteractor;
import com.db.votacao.api.v1.modules.votacao.usecase.PautaInteractor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
        AssembleiaDto assembleiaDto = assembleiaInteractor.findById(pautaDto.getAssembleiaId());

        validateDate(pautaDto, assembleiaDto);

        Pauta pauta = DtoEntityConverterUtil.convertToEntity(pautaDto, Pauta.class);

        return DtoEntityConverterUtil.convertToDto(this.repository.save(pauta), PautaDto.class);
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
    public PautaResultDto getPautaResult(Long id) throws Exception {
        PautaDto pauta = this.findById(id);

        return PautaResultDto.builder()
                .pautaId(pauta.getId())
                .assembleiaId(pauta.getAssembleiaId())
                .votos(pauta.getVotos().size())
                .status(pauta.getStatus())
        .build();
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
        Pauta pauta = this.repository.findById(id).orElseThrow(() -> new NotFoundException("Pauta não encontrada para id: " + id));
        return DtoEntityConverterUtil.convertToDto(pauta, PautaDto.class);
    }

    /**
     * Method responsible to validate pauta and
     * assembleia dates
     *
     * @param pautaDto PautaDto Object
     * @param assembleiaDto assembleiaDto object
     */
    private void validateDate(PautaDto pautaDto, AssembleiaDto assembleiaDto) {
        if(pautaDto.getStartTime().toLocalDate().isBefore(assembleiaDto.getStartDate())
                || pautaDto.getEndTime().toLocalDate().isAfter(assembleiaDto.getEndDate())) {
            throw new BadRequestException("Data de votação da pauta deve ser entre a data da assembleia!");
        }
    }
}
