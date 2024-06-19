package com.db.votacao.api.v1.modules.votacao.model.entity;

import com.db.votacao.api.v1.modules.votacao.model.enums.VotoResult;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table( name = "voto" )
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn( name = "associado_id" )
    private Associado associado;

    @JsonIgnore
    @ManyToOne
    @JoinColumn( name = "pauta_id" )
    private Pauta pauta;

    @Column( name = "voto_result", nullable = false )
    private VotoResult votoResult;
}
