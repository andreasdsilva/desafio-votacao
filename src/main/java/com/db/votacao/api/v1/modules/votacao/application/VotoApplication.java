package com.db.votacao.api.v1.modules.votacao.application;

import com.db.votacao.api.v1.modules.votacao.model.dto.VotoDto;
import com.db.votacao.api.v1.modules.votacao.shared.util.ApiUtil;
import com.db.votacao.api.v1.modules.votacao.usecase.VotoInteractor;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping(ApiUtil.VERSION + "voto")
@RestController
public class VotoApplication {

    private final VotoInteractor votoInteractor;

    @PostMapping()
    public ResponseEntity<VotoDto> create(@RequestBody @Valid VotoDto votoDto) throws Exception {
        return ApiUtil.created(votoInteractor.create(votoDto));
    }
}
