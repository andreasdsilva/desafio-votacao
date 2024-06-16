package com.db.votacao.api.v1.modules.votacao.model.dto;

import com.db.votacao.api.v1.modules.votacao.model.entity.Voto;
import com.db.votacao.api.v1.modules.votacao.model.enums.PautaStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PautaDto {

    private long id;

    @NotNull(message = "Assembleia sem ID informado")
    private Long assembleiaId;

    @NotBlank(message = "Pauta sem nome")
    private String name;

    @NotBlank(message = "Pauta sem descrição")
    private String description = "";

    private List<Voto> votos = new ArrayList<>();

    @NotNull(message = "Pauta sem data e hora de início")
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private PautaStatus status;
}
