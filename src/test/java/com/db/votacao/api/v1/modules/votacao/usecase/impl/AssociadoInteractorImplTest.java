package com.db.votacao.api.v1.modules.votacao.usecase.impl;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssociadoDto;
import com.db.votacao.api.v1.modules.votacao.model.entity.Associado;
import com.db.votacao.api.v1.modules.votacao.model.enums.AssociadoStatus;
import com.db.votacao.api.v1.modules.votacao.repository.AssociadoRepository;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.BadRequestException;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AssociadoInteractorImplTest {

    @Mock
    private AssociadoRepository repository;

    @InjectMocks
    private AssociadoInteractorImpl interactor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("testing findByDoc on Success")
    void testFindByDocOnSuccess() throws Exception {
        String doc = "123456789";
        Associado associado = new Associado();
        associado.setId(1L);

        when(repository.findByDocumento(doc)).thenReturn(Optional.of(associado));

        AssociadoDto result = interactor.findByDocumento(doc);

        AssociadoDto associadoDtoExpected = new AssociadoDto();
        associadoDtoExpected.setId(1L);

        assertEquals(associadoDtoExpected, result);
    }

    @Test
    @DisplayName("Testing findByDoc on NotFoundException")
    void testFindByDocOnNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> interactor.findByDocumento("1"));

        assertEquals("Associado não encontrada para documento: 1", exception.getMessage());
    }

    @Test
    @DisplayName("Testing create")
    void testCreate() throws Exception {
        AssociadoDto associadoDto = new AssociadoDto();
        associadoDto.setNome("Nome");
        associadoDto.setDocumento("123456789");

        Associado associado = new Associado();
        associado.setNome("Nome");
        associado.setDocumento("123456789");
        associado.setStatus(AssociadoStatus.ABLE_TO_VOTE);

        when(repository.save(associado)).thenReturn(associado);

        AssociadoDto result = interactor.create(associadoDto);

        assertEquals(associadoDto, result);
        verify(repository).save(associado);
    }

    @Test
    @DisplayName("Testing findByDoc BadRequestException - existing associado")
    void testFindByDocExistingAssociado() {
        AssociadoDto associadoDto = new AssociadoDto();
        associadoDto.setDocumento("123456789");

        when(repository.findByDocumento(anyString())).thenReturn(Optional.of(new Associado()));

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> interactor.create(associadoDto));

        assertEquals("Associado ja existente para documento: 123456789", exception.getMessage());
    }
}