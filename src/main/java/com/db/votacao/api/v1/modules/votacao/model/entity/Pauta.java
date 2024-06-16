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

    private long assembleiaId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "pauta_voto",
            joinColumns = {
                @JoinColumn(name = "pauta_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "voto_id", referencedColumnName = "id")
            }
    )
    @Builder.Default
    private List<Voto> votos = new ArrayList<>();

    private LocalDateTime startTime;

    @Builder.Default
    private LocalDateTime endTime = LocalDateTime.now().plusMinutes(1);

    @Transient
    @Enumerated( EnumType.STRING )
    private PautaStatus status;

    public PautaStatus getStatus() {
        this.status = getStatusUpdated();
        return this.status;
    }

    private PautaStatus getStatusUpdated() {
        if(endTime.isAfter(LocalDateTime.now())
            || (startTime.isAfter(LocalDateTime.now()) && endTime.isBefore(LocalDateTime.now()))) {
            return PautaStatus.WAITING;
        }

        long approvedVotes = votos.stream().filter( voto -> voto.getVoto().equals( VotoResult.SIM )).count();
        long reprovedVotes = votos.stream().filter( voto -> voto.getVoto().equals( VotoResult.NAO )).count();

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
