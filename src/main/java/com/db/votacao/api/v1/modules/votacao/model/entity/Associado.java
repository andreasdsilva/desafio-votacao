package com.db.votacao.api.v1.modules.votacao.model.entity;

import com.db.votacao.api.v1.modules.votacao.model.enums.AssociadoStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "associado")
public class Associado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String documento;

    @Enumerated(EnumType.STRING)
    private AssociadoStatus status;
}
