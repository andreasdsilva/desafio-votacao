package com.db.votacao.api.v1.modules.votacao.model.dto;

import com.db.votacao.api.v1.modules.votacao.model.entity.Pauta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssembleiaDto {

    private long id;

    @NotBlank(message = "Assembleia sem nome!")
    private String name;

    private String description = "";

    private LocalDate creationDate = LocalDate.now();

    List<Pauta> pautas = new ArrayList<>();

    @NotNull(message = "Assembleia sem data de início")
    private LocalDate startDate;

    @NotNull(message = "Assembleia sem data de fim")
    private LocalDate endDate;
}
