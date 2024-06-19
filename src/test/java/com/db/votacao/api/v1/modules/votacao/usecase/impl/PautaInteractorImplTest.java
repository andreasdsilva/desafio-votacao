package com.db.votacao.api.v1.modules.votacao.usecase.impl;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssembleiaDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.PautaDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.PautaResultDto;
import com.db.votacao.api.v1.modules.votacao.model.entity.Pauta;
import com.db.votacao.api.v1.modules.votacao.model.enums.PautaStatus;
import com.db.votacao.api.v1.modules.votacao.repository.PautaRepository;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.BadRequestException;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.NotFoundException;
import com.db.votacao.api.v1.modules.votacao.shared.util.DtoEntityConverterUtil;
import com.db.votacao.api.v1.modules.votacao.usecase.AssembleiaInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PautaInteractorImplTest {

    @Mock
    private PautaRepository repository;
    @Mock
    private  AssembleiaInteractor assembleiaInteractor;

    @InjectMocks
    private PautaInteractorImpl interactor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("teste create on success")
    void testCreatSuccess() throws Exception {
        PautaDto pautaDto = new PautaDto();
        pautaDto.setAssembleiaId(1L);
        pautaDto.setStartTime(LocalDateTime.now());
        pautaDto.setEndTime(LocalDateTime.now().plusMinutes(1L));
        pautaDto.setStatus(PautaStatus.WAITING);

        AssembleiaDto assembleiaDto = new AssembleiaDto();
        assembleiaDto.setStartDate(LocalDate.now());
        assembleiaDto.setEndDate(LocalDate.now().plusDays(1L));

        Pauta pauta = DtoEntityConverterUtil.convertToEntity(pautaDto, Pauta.class);

        when(assembleiaInteractor.findById(anyLong())).thenReturn(assembleiaDto);
        when(repository.save(pauta)).thenReturn(pauta);

        PautaDto result = interactor.create(pautaDto);

        assertEquals(pautaDto, result);
        verify(repository).save(pauta);
    }

    @Test
    @DisplayName("Testing create throwing assembleia NotFoundException")
    void testCreateAssociadoNotFound() throws Exception {
        PautaDto pautaDto = new PautaDto();
        pautaDto.setAssembleiaId(1L);
        pautaDto.setStartTime(LocalDateTime.now().minusHours(1));
        pautaDto.setEndTime(LocalDateTime.now().plusDays(2));
        pautaDto.setStatus(PautaStatus.WAITING);

        AssembleiaDto assembleiaDto = new AssembleiaDto();
        assembleiaDto.setStartDate(LocalDate.now());
        assembleiaDto.setEndDate(LocalDate.now().plusDays(1L));

        when(assembleiaInteractor.findById(anyLong())).thenReturn(assembleiaDto);

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> interactor.create(pautaDto));

        assertEquals("Data de votação da pauta deve ser entre a data da assembleia!", exception.getMessage());
    }

    @Test
    @DisplayName("testing findById on Success")
    void testFindByIdOnSuccess() throws Exception {
        long pautaId = 1L;

        Pauta pauta = new Pauta();
        pauta.setId(1);

        when(repository.findById(pautaId)).thenReturn(Optional.of(pauta));

        PautaDto result = interactor.findById(pautaId);

        PautaDto pautaDtoExpected = new PautaDto();
        pautaDtoExpected.setAssembleiaId(0L);
        pautaDtoExpected.setStatus(PautaStatus.WAITING);
        pautaDtoExpected.setEndTime(result.getEndTime());
        pautaDtoExpected.setId(1);

        assertEquals(pautaDtoExpected, result);
    }

    @Test
    @DisplayName("Testing findById on NotFoundException")
    void testFindByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> interactor.findById(1L));

        assertEquals("Pauta não encontrada para id: 1", exception.getMessage());
    }

    @Test
    @DisplayName("testing getPautaResult on Pauta NotFound")
    void testGetPautaResultPautaNotFound() {
        long pautaId = 1L;

        Pauta pauta = new Pauta();
        pauta.setId(1);

        when(repository.findById(pautaId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> interactor.getPautaResult(pautaId));

        assertEquals("Pauta não encontrada para id: 1", exception.getMessage());
    }

    @Test
    @DisplayName("testing getPautaResult on Success")
    void testGetPautaResult() throws Exception {
        long pautaId = 1L;

        Pauta pauta = new Pauta();
        pauta.setId(1);

        when(repository.findById(pautaId)).thenReturn(Optional.of(pauta));

        PautaResultDto result = interactor.getPautaResult(pautaId);

        PautaResultDto pautaResultDtoExpected = new PautaResultDto();
        pautaResultDtoExpected.setPautaId(1);
        pautaResultDtoExpected.setStatus(PautaStatus.WAITING);

        assertEquals(pautaResultDtoExpected, result);
    }
}