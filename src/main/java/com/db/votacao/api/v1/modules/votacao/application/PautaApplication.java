package com.db.votacao.api.v1.modules.votacao.application;

import com.db.votacao.api.v1.modules.votacao.model.dto.PautaDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.PautaResultDto;
import com.db.votacao.api.v1.modules.votacao.shared.util.ApiUtil;
import com.db.votacao.api.v1.modules.votacao.usecase.PautaInteractor;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping(ApiUtil.VERSION + "pauta")
@RestController
public class PautaApplication {

    private final PautaInteractor pautaInteractor;

    @GetMapping("/result/{id}")
    public ResponseEntity<PautaResultDto> getPautaResult(@PathVariable("id") Long id) throws Exception {
        return ApiUtil.ok(pautaInteractor.getPautaResult(id));
    }

    @PostMapping()
    public ResponseEntity<PautaDto> createPauta( @RequestBody @Valid PautaDto pautaDTO ) throws Exception {
        return ApiUtil.created(pautaInteractor.create(pautaDTO));
    }
}
