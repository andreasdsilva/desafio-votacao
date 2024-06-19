package com.db.votacao.api.v1.modules.votacao.application;

import com.db.votacao.api.v1.modules.votacao.model.dto.VotoDto;
import com.db.votacao.api.v1.modules.votacao.model.enums.VotoResult;
import com.db.votacao.api.v1.modules.votacao.usecase.VotoInteractor;
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
class VotoApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private VotoInteractor votoInteractor;

    @InjectMocks
    private VotoApplication votoApplication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(votoApplication).build();
    }

    @Test
    @DisplayName("test endpoint create call")
    public void testCreate() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        VotoDto votoDto = new VotoDto();
        votoDto.setAssociadoDocumento("123456789");
        votoDto.setPautaId(1L);
        votoDto.setVotoResult(VotoResult.SIM);

        VotoDto createdVotoDto = new VotoDto();

        when(votoInteractor.create(any(VotoDto.class))).thenReturn(createdVotoDto);

        mockMvc.perform(post("/api/v1/voto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(votoDto)))
                .andExpect(status().isCreated());
    }
}