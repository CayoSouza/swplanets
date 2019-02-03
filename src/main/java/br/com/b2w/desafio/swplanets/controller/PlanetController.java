package br.com.b2w.desafio.swplanets.controller;

import br.com.b2w.desafio.swplanets.model.Planet;
import br.com.b2w.desafio.swplanets.service.PlanetService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/planetas")
public class PlanetController {

    @Autowired
    PlanetService planetService;

    @GetMapping
    public List<Planet> getAllPlanets() {
        return planetService.getAll();
    }

    @GetMapping("/id/{id}")
    public Planet getPlanetById(@PathVariable ObjectId id) {
        return planetService.getById(id);
    }

    @GetMapping("/nome/{nome}")
    public List<Planet> getPlanetByName(@PathVariable String nome) {
        return planetService.getAllByName(nome);
    }

    @PostMapping
    public Planet createPlanet(@Valid @RequestBody Planet planet) {
        planet.set_id(ObjectId.get());
        planetService.save(planet);

        return planet;
    }

    //TODO: RESTFULL
    @PutMapping("/id/{id}")
    public void modifyPlanetByObjectId(@PathVariable ObjectId id, @Valid @RequestBody Planet planet) {
        planet.set_id(id);
        planetService.save(planet);
    }

    @DeleteMapping("/id/{id}")
    public void deletePlanetByObjectId(@PathVariable ObjectId id) {
        planetService.delete(id);
    }

//    @PutMapping(value = "/{id}")
//    public void modifyPlanetById(@PathVariable("id") String id, @Valid @RequestBody Planet planet) {
//        planet.setId(id);
//        planetService.updatePlanet(planet);
//    }
}
