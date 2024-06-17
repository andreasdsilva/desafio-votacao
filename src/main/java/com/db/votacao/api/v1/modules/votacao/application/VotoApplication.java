package com.db.votacao.api.v1.modules.votacao.application;

import com.db.votacao.api.v1.modules.votacao.model.dto.PautaDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.VotoDto;
import com.db.votacao.api.v1.modules.votacao.shared.util.ApiUtil;
import com.db.votacao.api.v1.modules.votacao.usecase.VotoInteractor;
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
@RequestMapping(ApiUtil.VERSION + "voto")
@RestController
public class VotoApplication {

    private final VotoInteractor votoInteractor;

    @Operation(
            operationId = "create voto",
            summary = "Cria voto e persiste no banco",
            tags = { "voto" },
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "voto created",
                            content = @Content(
                                    schema = @Schema( implementation = PautaDto.class ),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "pauta not found",
                            content = @Content(
                                    schema = @Schema( implementation = Valid.class ),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "associado not found",
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
    public ResponseEntity<VotoDto> create(@RequestBody @Valid VotoDto votoDto) throws Exception {
        return ApiUtil.created(votoInteractor.create(votoDto));
    }
}
