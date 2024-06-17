package com.db.votacao.api.v1.modules.votacao.application;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssociadoDto;
import com.db.votacao.api.v1.modules.votacao.shared.util.ApiUtil;
import com.db.votacao.api.v1.modules.votacao.usecase.AssociadoInteractor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
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

    @Operation(
            operationId = "create associado",
            summary = "Cria associado e persiste no banco",
            tags = { "associado" },
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "associado created",
                            content = @Content(
                                    schema = @Schema( implementation = Valid.class ),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "invalid request body",
                            content = @Content(
                                    schema = @Schema( implementation = Valid.class ),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PostMapping()
    public ResponseEntity<AssociadoDto> create(@RequestBody @Valid AssociadoDto associadoDto) throws Exception {
        return ApiUtil.created(associadoInteractor.create(associadoDto));
    }
}
