package br.com.b2w.desafio.swplanets.controller;

import br.com.b2w.desafio.swplanets.model.Planet;
import br.com.b2w.desafio.swplanets.repository.PlanetRepository;
import br.com.b2w.desafio.swplanets.service.PlanetService;
import br.com.b2w.desafio.swplanets.service.SwapiService;
import br.com.b2w.desafio.swplanets.util.Constants;
import br.com.b2w.desafio.swplanets.util.ResponseMessage;
import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.Gson;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private Gson gson;

    @Before
    public void setUp(){
        planet = new Planet();
        planet.setName("PlanetaTeste");
        planet.set_id(ObjectId.get());
        planet.setClimate("ClimaTeste");

        planets = new ArrayList<>();
        planets.add(planet);

        gson = new Gson();
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
        final String nome = "placeholder";
        given(planetService.getAllByName(nome)).willReturn(planets);

        mockMvc.perform(get("/planetas").param("nome", nome))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(planet.getName()));
    }

    @Test
    public void getPlanetasByIdShouldReturnNotFound() throws Exception {
        given(planetService.getById(ObjectId.get())).willReturn(Optional.empty());

        final ObjectId id = ObjectId.get();
        mockMvc.perform(get("/planetas/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(ResponseMessage.NO_PLANET_FOUND_BY_ID.getMessage() + id));
    }

    @Test
    public void getPlanetasByIdShouldReturnOk() throws Exception {
        ObjectId id = new ObjectId(planet.get_id());
        given(planetService.getById(id)).willReturn(Optional.of(planet));

        mockMvc.perform(get("/planetas/{id}", planet.get_id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._id").value(planet.get_id()));
    }

    @Test
    public void createPlanetaShouldReturnCreated() throws Exception {
        Planet createPlanet = new Planet();
        createPlanet.set_id(ObjectId.get());
        createPlanet.setName("PlanetaCriado");
        createPlanet.setClimate("ClimaCriado");
        createPlanet.setTerrain("TerrenoCriado");
        createPlanet.setFilms(Arrays.asList("1","2"));
        createPlanet.setFilmsApparitions(2);

        given(planetService.save(any(Planet.class))).willReturn(createPlanet);


        mockMvc.perform(post("/planetas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(createPlanet)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(createPlanet.getName()));
    }

}
