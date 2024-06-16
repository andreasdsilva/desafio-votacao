package com.db.votacao.api.v1.modules.votacao.application;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssociadoDto;
import com.db.votacao.api.v1.modules.votacao.shared.util.ApiUtil;
import com.db.votacao.api.v1.modules.votacao.usecase.AssociadoInteractor;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping(ApiUtil.VERSION + "associado")
@RestController
public class AssociadoApplication {

    private final AssociadoInteractor associadoInteractor;

    @PostMapping()
    public ResponseEntity<AssociadoDto> create(@RequestBody @Valid AssociadoDto associadoDto) throws Exception {
        return ApiUtil.created(associadoInteractor.create(associadoDto));
    }
}
