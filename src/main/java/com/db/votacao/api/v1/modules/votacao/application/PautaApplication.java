package com.db.votacao.api.v1.modules.votacao.application;

import com.db.votacao.api.v1.modules.votacao.model.dto.PautaDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.PautaResultDto;
import com.db.votacao.api.v1.modules.votacao.shared.util.ApiUtil;
import com.db.votacao.api.v1.modules.votacao.usecase.PautaInteractor;
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
@RequestMapping(ApiUtil.VERSION + "pauta")
@RestController
public class PautaApplication {

    private final PautaInteractor pautaInteractor;

    @Operation(
            operationId = "get pauta result",
            summary = "retorna resultado de pauta",
            tags = { "pauta" },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "pauta retornada",
                            content = @Content(
                                    schema = @Schema( implementation = PautaResultDto.class ),
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
                            responseCode = "400",
                            description = "invalid request body",
                            content = @Content(
                                    schema = @Schema( implementation = Valid.class ),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @GetMapping("/result/{id}")
    public ResponseEntity<PautaResultDto> getPautaResult(@PathVariable("id") Long id) throws Exception {
        return ApiUtil.ok(pautaInteractor.getPautaResult(id));
    }

    @Operation(
            operationId = "create pauta",
            summary = "Cria pauta e persiste no banco",
            tags = { "pauta" },
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "pauta created",
                            content = @Content(
                                    schema = @Schema( implementation = PautaDto.class ),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "assembleia not found",
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
    public ResponseEntity<PautaDto> createPauta( @RequestBody @Valid PautaDto pautaDTO ) throws Exception {
        return ApiUtil.created(pautaInteractor.create(pautaDTO));
    }
}
