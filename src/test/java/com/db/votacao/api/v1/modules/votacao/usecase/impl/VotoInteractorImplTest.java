package com.db.votacao.api.v1.modules.votacao.usecase.impl;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssociadoDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.PautaDto;
import com.db.votacao.api.v1.modules.votacao.model.dto.VotoDto;
import com.db.votacao.api.v1.modules.votacao.model.entity.Associado;
import com.db.votacao.api.v1.modules.votacao.model.entity.Pauta;
import com.db.votacao.api.v1.modules.votacao.model.entity.Voto;
import com.db.votacao.api.v1.modules.votacao.model.enums.AssociadoStatus;
import com.db.votacao.api.v1.modules.votacao.model.enums.PautaStatus;
import com.db.votacao.api.v1.modules.votacao.model.mapper.PautaResultMapper;
import com.db.votacao.api.v1.modules.votacao.repository.VotoRepository;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.BadRequestException;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.NotFoundException;
import com.db.votacao.api.v1.modules.votacao.shared.util.DateTimeUtil;
import com.db.votacao.api.v1.modules.votacao.shared.util.DtoEntityConverterUtil;
import com.db.votacao.api.v1.modules.votacao.usecase.AssociadoInteractor;
import com.db.votacao.api.v1.modules.votacao.usecase.PautaInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VotoInteractorImplTest {

    @Mock
    private AssociadoInteractor associadoInteractor;
    @Mock
    private PautaInteractor pautaInteractor;
    @Mock
    private VotoRepository repository;

    @InjectMocks
    private VotoInteractorImpl interactor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testing create success")
    void testCreate() throws Exception {
        PautaDto pautaDto = new PautaDto();
        pautaDto.setStatus(PautaStatus.OPEN);
        pautaDto.setName("pauta");
        pautaDto.setDescription("");
        pautaDto.setAssembleiaId(1L);
        pautaDto.setEndTime(LocalDateTime.now());
        pautaDto.setStartTime(LocalDateTime.now());

        AssociadoDto associadoDto = new AssociadoDto();
        associadoDto.setStatus(AssociadoStatus.ABLE_TO_VOTE);
        associadoDto.setDocumento("123");

        VotoDto votoDto = new VotoDto();
        votoDto.setAssociadoDocumento("123");

        Voto voto = new Voto();
        Associado associado = DtoEntityConverterUtil.convertToEntity(associadoDto, Associado.class);
        Pauta pauta = DtoEntityConverterUtil.convertToEntity(pautaDto, Pauta.class);
        voto.setPauta(pauta);
        voto.setAssociado(associado);

        when(associadoInteractor.findByDocumento(anyString())).thenReturn(associadoDto);
        when(pautaInteractor.findById(anyLong())).thenReturn(pautaDto);
        when(repository.save(voto)).thenReturn(voto);

        VotoDto result = interactor.create(votoDto);

        assertEquals(votoDto, result);
        verify(repository).save(voto);
    }

    @Test
    @DisplayName("Testing create with PautaStatus = WAITING")
    void testCreateWithPautaWaiting() throws Exception {
        PautaDto pautaDto = new PautaDto();
        pautaDto.setStatus(PautaStatus.WAITING);
        pautaDto.setStartTime(LocalDateTime.now());

        when(pautaInteractor.findById(anyLong())).thenReturn(pautaDto);

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> interactor.create(new VotoDto()));

        assertEquals("Pauta em aguardo, aguardar até " + DateTimeUtil.getString(pautaDto.getStartTime()), exception.getMessage());
    }

    @Test
    @DisplayName("Testing create with closed pauta")
    void testCreateWithPautaClosed() throws Exception {
        PautaDto pautaDto = new PautaDto();
        pautaDto.setStatus(PautaStatus.NULLIFIED);
        pautaDto.setStartTime(LocalDateTime.now());

        when(pautaInteractor.findById(anyLong())).thenReturn(pautaDto);

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> interactor.create(new VotoDto()));

        assertEquals("Pauta já encerrada!", exception.getMessage());
    }

    @Test
    @DisplayName("Testing create with AssociadoStatus = UNABLE_TO_VOTE")
    void testCreateWithAssociadoUnableToVote() throws Exception {
        PautaDto pautaDto = new PautaDto();
        pautaDto.setStatus(PautaStatus.OPEN);

        AssociadoDto associadoDto = new AssociadoDto();
        associadoDto.setStatus(AssociadoStatus.UNABLE_TO_VOTE);

        VotoDto votoDto = new VotoDto();
        votoDto.setPautaId(1L);
        votoDto.setAssociadoDocumento("123");

        when(pautaInteractor.findById(anyLong())).thenReturn(pautaDto);
        when(associadoInteractor.findByDocumento(anyString())).thenReturn(associadoDto);

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> interactor.create(votoDto));

        assertEquals("Associado impossibilitado de votar!", exception.getMessage());
    }

    @Test
    @DisplayName("Testing create with associado who already voted")
    void testCreateWithAssociadoAlreadyVoted() throws Exception {
        AssociadoDto associadoDto = new AssociadoDto();
        associadoDto.setId(1L);
        associadoDto.setStatus(AssociadoStatus.ABLE_TO_VOTE);

        PautaDto pautaDto = new PautaDto();
        pautaDto.setStatus(PautaStatus.OPEN);

        Voto voto = new Voto();
        Associado associado = new Associado();
        associado.setId(associadoDto.getId());
        voto.setAssociado(associado);

        pautaDto.setVotos(Collections.singletonList(voto));

        VotoDto votoDto = new VotoDto();
        votoDto.setPautaId(1L);
        votoDto.setAssociadoDocumento("123");

        when(pautaInteractor.findById(anyLong())).thenReturn(pautaDto);
        when(associadoInteractor.findByDocumento(anyString())).thenReturn(associadoDto);

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> interactor.create(votoDto));

        assertEquals("Voto de associado já contabilizado!", exception.getMessage());
    }
}