package com.db.votacao.api.v1.modules.votacao.application;

import com.db.votacao.api.v1.modules.votacao.model.dto.AssociadoDto;
import com.db.votacao.api.v1.modules.votacao.usecase.AssociadoInteractor;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AssociadoApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AssociadoInteractor AssociadoInteractor;

    @InjectMocks
    private AssociadoApplication AssociadoApplication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(AssociadoApplication).build();
    }

    @Test
    @DisplayName("test endpoint create call")
    public void testCreate() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        AssociadoDto associadoDto = new AssociadoDto();
        associadoDto.setNome("nome");
        associadoDto.setDocumento("050.811.130-77");

        AssociadoDto createdAssociadoDto = new AssociadoDto();

        when(AssociadoInteractor.create(any(AssociadoDto.class))).thenReturn(createdAssociadoDto);

        mockMvc.perform(post("/api/v1/associado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(associadoDto)))
                .andExpect(status().isCreated());
    }
}