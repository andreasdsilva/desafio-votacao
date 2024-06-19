package com.db.votacao.api.v1.modules.votacao.usecase.impl;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssembleiaDto;
import com.db.votacao.api.v1.modules.votacao.model.entity.Assembleia;
import com.db.votacao.api.v1.modules.votacao.repository.AssembleiaRepository;
import com.db.votacao.api.v1.modules.votacao.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AssembleiaInteractorImplTest {

    @Mock
    private AssembleiaRepository repository;

    @InjectMocks
    private AssembleiaInteractorImpl interactor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("testing findById on Success")
    void testFindByIdOnSuccess() throws Exception {
        Assembleia assembleia = new Assembleia();
        assembleia.setId(1);

        when(repository.findById(1L)).thenReturn(Optional.of(assembleia));

        AssembleiaDto result = interactor.findById(1L);

        AssembleiaDto assembleiaDtoExpected = new AssembleiaDto();
        assembleiaDtoExpected.setId(1);

        assertEquals(assembleiaDtoExpected, result);
    }

    @Test
    @DisplayName("Testing findById on NotFoundException")
    void testFindByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> interactor.findById(1L));

        assertEquals("Assembleia não encontrada para id: 1", exception.getMessage());
    }

    @Test
    @DisplayName("Testing create")
    void testCreate() throws Exception {
        AssembleiaDto assembleiaDto = new AssembleiaDto();
        assembleiaDto.setName("assembleia");
        assembleiaDto.setDescription("assembleia teste");
        assembleiaDto.setStartDate(LocalDate.now());
        assembleiaDto.setEndDate(LocalDate.now());

        Assembleia assembleia = new Assembleia();
        assembleia.setName("assembleia");
        assembleia.setDescription("assembleia teste");
        assembleia.setStartDate(LocalDate.now());
        assembleia.setEndDate(LocalDate.now());

        when(repository.save(assembleia)).thenReturn(assembleia);

        AssembleiaDto result = interactor.create(assembleiaDto);

        assertEquals(assembleiaDto, result);
        verify(repository).save(assembleia);
    }
}