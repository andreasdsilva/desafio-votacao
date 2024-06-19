package com.db.votacao.api.v1.modules.votacao.model.mapper;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssociadoDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.PautaDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.VotoDto;
import com.db.votacao.api.v1.modules.votacao.model.entity.Associado;
import com.db.votacao.api.v1.modules.votacao.model.entity.Pauta;
import com.db.votacao.api.v1.modules.votacao.model.entity.Voto;
import com.db.votacao.api.v1.modules.votacao.shared.util.DtoEntityConverterUtil;

public class VotoMapper {

    public static Voto dtoToEntity(VotoDto votoDto, PautaDto pautaDto, AssociadoDto associadoDto) throws Exception {
        return Voto.builder()
                .id(votoDto.getId())
                .pauta(DtoEntityConverterUtil.convertToEntity(pautaDto, Pauta.class))
                .associado(DtoEntityConverterUtil.convertToEntity(associadoDto, Associado.class))
                .build();
    }

    public static VotoDto entityToDto(Voto voto) {
        return VotoDto.builder()
                .associadoDocumento(voto.getAssociado().getDocumento())
                .pautaId(voto.getPauta().getId())
                .votoResult(voto.getVotoResult())
                .build();
    }
}
