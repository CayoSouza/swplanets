package br.com.b2w.desafio.swplanets.controller;


import br.com.b2w.desafio.swplanets.service.SwapiService;
import br.com.b2w.desafio.swplanets.util.ResponseMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SwapiController.class)
public class SwapiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SwapiService swapiService;

    @Test
    public void getSwapiImportarShouldReturnOk() throws Exception {
        given(swapiService.importToDatabase()).willReturn(true);

        mockMvc.perform(get("/swapi/importar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(ResponseMessage.SWAPI_SUCCESSFULLY_IMPORTED.getMessage()));

    }
    @Test
    public void getSwapiImportarShouldReturnInternalServerError() throws Exception {
        given(swapiService.importToDatabase()).willReturn(false);

        mockMvc.perform(get("/swapi/importar"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(ResponseMessage.SWAPI_FAIL_IMPORT.getMessage()));

    }
}
