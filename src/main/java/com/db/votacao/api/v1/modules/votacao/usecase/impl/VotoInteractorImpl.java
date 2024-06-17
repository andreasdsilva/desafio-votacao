package com.db.votacao.api.v1.modules.votacao.usecase.impl;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssociadoDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.PautaDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.VotoDto;
import com.db.votacao.api.v1.modules.votacao.model.entity.Associado;
import com.db.votacao.api.v1.modules.votacao.model.entity.Pauta;
import com.db.votacao.api.v1.modules.votacao.model.entity.Voto;
import com.db.votacao.api.v1.modules.votacao.model.enums.AssociadoStatus;
import com.db.votacao.api.v1.modules.votacao.model.enums.PautaStatus;
import com.db.votacao.api.v1.modules.votacao.repository.VotoRepository;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.BadRequestException;
import com.db.votacao.api.v1.modules.votacao.shared.util.DateTimeUtil;
import com.db.votacao.api.v1.modules.votacao.shared.util.DtoEntityConverterUtil;
import com.db.votacao.api.v1.modules.votacao.usecase.AssociadoInteractor;
import com.db.votacao.api.v1.modules.votacao.usecase.PautaInteractor;
import com.db.votacao.api.v1.modules.votacao.usecase.VotoInteractor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@AllArgsConstructor
@Service
public class VotoInteractorImpl implements VotoInteractor {

    private final VotoRepository repository;
    private final AssociadoInteractor associadoInteractor;
    private final PautaInteractor pautaInteractor;

    @Override
    public VotoDto create(VotoDto votoDto) throws Exception {
        AssociadoDto associadoDto = associadoInteractor.findByDocumento(votoDto.getAssociadoDocumento());
        PautaDto pautaDto = pautaInteractor.findById(votoDto.getPautaId());

        validateVoto(associadoDto, pautaDto);

        Voto voto = Voto.builder()
                .pauta(DtoEntityConverterUtil.convertToEntity(pautaDto, Pauta.class))
                .associado(DtoEntityConverterUtil.convertToEntity(associadoDto, Associado.class))
                .build();
        Voto savedVoto = this.repository.save(voto);

        return VotoDto.builder()
                .associadoDocumento(savedVoto.getAssociado().getDocumento())
                .pautaId(savedVoto.getPauta().getId())
                .voto(savedVoto.getVoto())
                .build();
    }

    private void validateVoto(AssociadoDto associadoDto, PautaDto pautaDto) {
        validatePauta(pautaDto);
        validateAssociado(associadoDto, pautaDto);
    }

    private void validatePauta(PautaDto pautaDto) {
        if(pautaDto.getStatus().equals(PautaStatus.WAITING)) {
            throw new BadRequestException("Pauta em aguardo, aguardar até " + DateTimeUtil.getString(pautaDto.getStartTime()));
        }

        if(!pautaDto.getStatus().equals(PautaStatus.OPEN)) {
            throw new BadRequestException("Pauta já encerrada!");
        }
    }

    private void validateAssociado(AssociadoDto associadoDto, PautaDto pautaDto) {
        if(associadoDto.getStatus() == AssociadoStatus.UNABLE_TO_VOTE) {
            throw new BadRequestException("Associado impossibilitado de votar!");
        }

        if(pautaDto.getVotos().stream().anyMatch(x -> Objects.equals(x.getAssociado().getId(), associadoDto.getId()))) {
            throw new BadRequestException("Voto de associado já contabilizado!");
        }
    }
}
