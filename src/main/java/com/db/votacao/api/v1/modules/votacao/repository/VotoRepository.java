package com.db.votacao.api.v1.modules.votacao.repository;

import com.db.votacao.api.v1.modules.votacao.model.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
}
