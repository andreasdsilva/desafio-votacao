package com.db.votacao.api.v1.modules.votacao.repository;

import com.db.votacao.api.v1.modules.votacao.model.entity.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    Optional<Associado> findByDocumento(String documento);
}
