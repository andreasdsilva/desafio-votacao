package com.db.votacao.api.v1.modules.votacao.model.dto;

import com.db.votacao.api.v1.modules.votacao.model.enums.VotoResult;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class VotoDto {

    private long id;

    @NotBlank(message = "Documento de Associado deve ser informado!")
    private String associadoDocumento;

    @NotNull(message = "ID de Pauta deve ser informado!")
    private long pautaId;

    @NotNull(message = "Voto deve ser informado!")
    private VotoResult votoResult;
}
