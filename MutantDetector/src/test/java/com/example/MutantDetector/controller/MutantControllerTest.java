package com.example.MutantDetector.controller;

import com.example.MutantDetector.dto.StatsResponse;
import com.example.MutantDetector.service.MutantService;
import com.example.MutantDetector.service.StatsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;

    @Test
    void testMutanteDevuelveOK() throws Exception {
        when(mutantService.esMutante(any(String[].class))).thenReturn(true);

        String json = """
                { "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"] }
                """;

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testHumanoValidoDevuelveForbidden() throws Exception {
        when(mutantService.esMutante(any(String[].class))).thenReturn(false);

        String json = """
                { "dna": ["ATGC","CAGT","TTAT","AGAC"] }
                """;

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    void testAdnInvalidoCaracteresDevuelveBadRequest() throws Exception {
        String json = """
                { "dna": ["ATGX","CAGT","TTAT","AGAC"] }
                """;

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mutantService);
    }

    @Test
    void testAdnInvalidoMatrizNoCuadradaDevuelveBadRequest() throws Exception {
        String json = """
                { "dna": ["ATGC","CAG","TTAT"] }
                """;

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mutantService);
    }

    @Test
    void testBodyVacioDevuelveBadRequest() throws Exception {
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testStatsDevuelveOkConJsonCorrecto() throws Exception {

        when(statsService.obtenerEstadisticas())
                .thenReturn(new StatsResponse(40, 100, 0.4));

        mockMvc.perform(get("/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.4));
    }
}
