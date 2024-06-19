package com.db.votacao.api.v1.modules.votacao.application;

import com.db.votacao.api.v1.modules.votacao.model.dto.PautaDto;
import com.db.votacao.api.v1.modules.votacao.usecase.PautaInteractor;
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

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PautaApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PautaInteractor pautaInteractor;

    @InjectMocks
    private PautaApplication pautaApplication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(pautaApplication).build();
    }

    @Test
    @DisplayName("test endpoint create call")
    public void testCreate() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        PautaDto pautaDto = new PautaDto();
        pautaDto.setName("pauta");
        pautaDto.setDescription("pauta");
        pautaDto.setStartTime(LocalDateTime.now());
        pautaDto.setAssembleiaId(1L);

        PautaDto createdPautaDto = new PautaDto();

        when(pautaInteractor.create(any(PautaDto.class))).thenReturn(createdPautaDto);

        mockMvc.perform(post("/api/v1/pauta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pautaDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("test endpoint getPautaResult call")
    public void testGetPautaResult() throws Exception {
        PautaDto pautaDto = new PautaDto();
        pautaDto.setName("pauta");
        pautaDto.setDescription("pauta");
        pautaDto.setStartTime(LocalDateTime.now());
        pautaDto.setAssembleiaId(1L);

        PautaDto createdPautaDto = new PautaDto();

        when(pautaInteractor.create(any(PautaDto.class))).thenReturn(createdPautaDto);

        mockMvc.perform(get("/api/v1/pauta/result/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}