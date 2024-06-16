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

    @Override
    public PautaDto create(PautaDto pautaDto) throws Exception {
        AssembleiaDto assembleiaDto = assembleiaInteractor.findById(pautaDto.getAssembleiaId());

        validateDate(pautaDto, assembleiaDto);

        Pauta pauta = DtoEntityConverterUtil.convertToEntity(pautaDto, Pauta.class);

        return DtoEntityConverterUtil.convertToDto(this.repository.save(pauta), PautaDto.class);
    }

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

    @Override
    public PautaDto findById(long id) throws Exception {
        Pauta pauta = this.repository.findById(id).orElseThrow(() -> new NotFoundException("Pauta não encontrada para id: " + id));
        return DtoEntityConverterUtil.convertToDto(pauta, PautaDto.class);
    }

    private void validateDate(PautaDto pautaDto, AssembleiaDto assembleiaDto) {
        if(pautaDto.getStartTime().toLocalDate().isBefore(assembleiaDto.getStartDate())
                || pautaDto.getEndTime().toLocalDate().isAfter(assembleiaDto.getEndDate())) {
            throw new BadRequestException("Data de votação da pauta deve ser entre a data da assembleia!");
        }
    }
}
