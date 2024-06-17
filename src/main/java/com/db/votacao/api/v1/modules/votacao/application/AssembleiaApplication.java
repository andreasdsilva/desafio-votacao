package com.db.votacao.api.v1.modules.votacao.application;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssembleiaDto;
import com.db.votacao.api.v1.modules.votacao.shared.util.ApiUtil;
import com.db.votacao.api.v1.modules.votacao.usecase.AssembleiaInteractor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping(ApiUtil.VERSION + "assembleia")
@RestController
public class AssembleiaApplication {

    private final AssembleiaInteractor assembleiaInteractor;

    @Operation(
            operationId = "create assembleia",
            summary = "Cria assembleia e persiste no banco",
            tags = { "assembleia" },
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "assembleia created",
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
    public ResponseEntity<AssembleiaDto> create(@RequestBody @Valid AssembleiaDto assembleiaDto) throws Exception {
        return ApiUtil.created(assembleiaInteractor.create(assembleiaDto));
    }
}
