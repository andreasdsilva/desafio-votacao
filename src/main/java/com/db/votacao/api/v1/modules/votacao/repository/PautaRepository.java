package com.db.votacao.api.v1.modules.votacao.repository;

import com.db.votacao.api.v1.modules.votacao.model.entity.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

    List<Pauta> findByAssembleiaId(long assembleiaId);
}
