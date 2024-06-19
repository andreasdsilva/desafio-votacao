package com.db.votacao.api.v1.modules.votacao.model.dto;

import com.db.votacao.api.v1.modules.votacao.model.enums.AssociadoStatus;
import com.db.votacao.api.v1.modules.votacao.validator.ValidCpfCnpj;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class AssociadoDto {

    private Long id;

    @NotBlank(message = "Associado sem nome informado")
    private String nome;

    @NotBlank(message = "Associado sem Documento informado")
    @ValidCpfCnpj
    private String documento;

    private AssociadoStatus status;
}
