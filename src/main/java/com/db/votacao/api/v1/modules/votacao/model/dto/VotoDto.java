package com.db.votacao.api.v1.modules.votacao.model.dto;

import com.db.votacao.api.v1.modules.votacao.model.enums.VotoResult;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VotoDto {

    private long id;

    @NotBlank(message = "Documento de Associado deve ser informado!")
    private String associadoDocumento;

    @NotBlank(message = "ID de Pauta deve ser informado!")
    private long pautaId;

    private VotoResult voto;
}
