package com.db.votacao.api.v1.modules.votacao.model.dto;

import com.db.votacao.api.v1.modules.votacao.model.entity.Associado;
import com.db.votacao.api.v1.modules.votacao.model.entity.Pauta;
import com.db.votacao.api.v1.modules.votacao.model.enums.VotoResult;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VotoDto {

    private long id;

    @NotBlank(message = "Documento de Associado deve ser informado!")
    private String associadoDocumento;

    @NotBlank(message = "ID de Pauta deve ser informado!")
    private long pautaId;

    private VotoResult voto;
}
