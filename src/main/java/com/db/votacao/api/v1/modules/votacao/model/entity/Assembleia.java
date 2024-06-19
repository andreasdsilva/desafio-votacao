package com.db.votacao.api.v1.modules.votacao.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Table(name = "assembleia")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assembleia {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    private String name;

    @Builder.Default
    private String description = "";

    @Builder.Default
    private LocalDate creationDate = LocalDate.now();

    @OneToMany( cascade = CascadeType.ALL )
    @JoinTable(name = "assembleia_pauta",
            joinColumns = {
                @JoinColumn(name = "assembleia_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "pauta_id", referencedColumnName = "id")
            }
    )
    @Builder.Default
    List<Pauta> pautas = new ArrayList<>();

    @Column( name = "start_date", nullable = false )
    private LocalDate startDate;

    @Column( name = "end_date", nullable = false )
    private LocalDate endDate;
}
