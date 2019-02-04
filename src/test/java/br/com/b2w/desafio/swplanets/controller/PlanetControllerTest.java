package br.com.b2w.desafio.swplanets.controller;

import br.com.b2w.desafio.swplanets.model.Planet;
import br.com.b2w.desafio.swplanets.repository.PlanetRepository;
import br.com.b2w.desafio.swplanets.service.PlanetService;
import br.com.b2w.desafio.swplanets.service.SwapiService;
import br.com.b2w.desafio.swplanets.util.Constants;
import br.com.b2w.desafio.swplanets.util.ResponseMessage;
import com.fasterxml.jackson.core.JsonParser;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PlanetController.class)
public class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanetService planetService;

    private Planet planet;
    private List<Planet> planets;

    @Before
    public void setUp(){
        planet = new Planet();
        planet.setName("PlanetaTeste");
        planet.set_id(ObjectId.get());
        planet.setClimate("ClimaTeste");

        planets = new ArrayList<>();
        planets.add(planet);
    }

    @Test
    public void getPlanetasShouldReturnNotFound() throws Exception {
        given(planetService.getAll()).willReturn(new ArrayList<Planet>());

        mockMvc.perform(get("/planetas"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(ResponseMessage.NO_PLANET_FOUND.getMessage()))
                .andDo(print());
    }

    @Test
    public void getPlanetasShouldReturnOk() throws Exception {
        given(planetService.getAll()).willReturn(planets);

        mockMvc.perform(get("/planetas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(planet.getName()));
    }

    @Test
    public void getPlanetasShouldReturnOkAndManyPlanets() throws Exception {
        Planet planet2 = new Planet();
        planet2.set_id(ObjectId.get());
        planet2.setName("Planeta2");
        planets.add(planet2);

        given(planetService.getAll()).willReturn(planets);

        mockMvc.perform(get("/planetas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(planet.getName()))
                .andExpect(jsonPath("$[1].name").value(planet2.getName()));
    }

    @Test
    public void getPlanetasByNameShouldReturnNotFound() throws Exception {
        given(planetService.getAllByName("placeholder")).willReturn(new ArrayList<>());

        final String name = "placeholder";
        mockMvc.perform(get("/planetas").param("nome", name))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(ResponseMessage.NO_PLANET_FOUND_BY_NAME.getMessage() + name));
    }

    @Test
    public void getPlanetasByNameShouldReturnOk() throws Exception {
        given(planetService.getAllByName("placeholder")).willReturn(planets);

        mockMvc.perform(get("/planetas").param("nome", "placeholder"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(planet.getName()));
    }
}
