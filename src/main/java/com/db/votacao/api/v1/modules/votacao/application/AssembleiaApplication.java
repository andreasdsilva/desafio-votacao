package com.db.votacao.api.v1.modules.votacao.application;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssembleiaDto;
import com.db.votacao.api.v1.modules.votacao.shared.util.ApiUtil;
import com.db.votacao.api.v1.modules.votacao.usecase.AssembleiaInteractor;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping(ApiUtil.VERSION + "assembleia")
@RestController
public class AssembleiaApplication {

    private final AssembleiaInteractor assembleiaInteractor;

    @PostMapping()
    public ResponseEntity<AssembleiaDto> create(@RequestBody @Valid AssembleiaDto assembleiaDto) throws Exception {
        return ApiUtil.created(assembleiaInteractor.create(assembleiaDto));
    }
}
