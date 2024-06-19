package com.db.votacao.api.v1.modules.votacao.model.entity;

import com.db.votacao.api.v1.modules.votacao.model.enums.PautaStatus;
import com.db.votacao.api.v1.modules.votacao.model.enums.VotoResult;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table( name = "pauta" )
public class Pauta {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    private String name;

    @Builder.Default
    private String description = "";

    @Column(name = "assembleia_id", nullable = false)
    private Long assembleiaId;

    @OneToMany(mappedBy = "pauta", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Voto> votos = new ArrayList<>();

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Transient
    @Enumerated( EnumType.STRING )
    private PautaStatus status;

    public PautaStatus getStatus() {
        this.status = getStatusUpdated();
        return this.status;
    }

    private PautaStatus getStatusUpdated() {
        if(startTime.isAfter(LocalDateTime.now())) {
            return PautaStatus.WAITING;
        }

        if(startTime.isBefore(LocalDateTime.now()) && endTime.isAfter(LocalDateTime.now())) {
            return PautaStatus.OPEN;
        }

        long approvedVotes = votos.stream().filter( voto -> voto.getVotoResult().equals( VotoResult.SIM )).count();
        long reprovedVotes = votos.stream().filter( voto -> voto.getVotoResult().equals( VotoResult.NAO )).count();

        if( approvedVotes <= 0 && reprovedVotes <= 0 ) {
            return PautaStatus.NULLIFIED;
        }

        if( approvedVotes == reprovedVotes ) {
            return PautaStatus.DRAW;
        }

        if( approvedVotes > reprovedVotes ) {
            return PautaStatus.APPROVED;
        }

        return PautaStatus.REPROVED;
    }
}
