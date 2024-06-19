package com.db.votacao.api.v1.modules.votacao.application;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssembleiaDto;
import com.db.votacao.api.v1.modules.votacao.usecase.AssembleiaInteractor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AssembleiaApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AssembleiaInteractor assembleiaInteractor;

    @InjectMocks
    private AssembleiaApplication assembleiaApplication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(assembleiaApplication).build();
    }

    @Test
    @DisplayName("test endpoint create call")
    public void testCreate() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        AssembleiaDto assembleiaDto = new AssembleiaDto();
        assembleiaDto.setName("Assembleia");
        assembleiaDto.setStartDate(LocalDate.now());
        assembleiaDto.setEndDate(LocalDate.now());

        AssembleiaDto createdAssembleiaDto = new AssembleiaDto();
        createdAssembleiaDto.setName("Assembleia");
        createdAssembleiaDto.setDescription("");
        createdAssembleiaDto.setId(1L);

        when(assembleiaInteractor.create(any(AssembleiaDto.class))).thenReturn(createdAssembleiaDto);

        mockMvc.perform(post("/api/v1/assembleia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assembleiaDto)))
                .andExpect(status().isCreated());
    }
}