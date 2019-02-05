package br.com.b2w.desafio.swplanets.controller;

import br.com.b2w.desafio.swplanets.model.Planet;
import br.com.b2w.desafio.swplanets.model.Response;
import br.com.b2w.desafio.swplanets.service.PlanetService;
import br.com.b2w.desafio.swplanets.util.ResponseMessage;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/planetas")
public class PlanetController {

    @Autowired
    PlanetService planetService;

    HttpHeaders headers;
    UriComponents uriComponents;

    @GetMapping
    public ResponseEntity<?> getAllPlanets(@RequestParam(required = false) String nome,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer limit,
                                           @RequestParam(required = false) String sort) {
        if( !StringUtils.isEmpty(nome) ) {
            return getPlanetByName(nome);
        }
        if( !StringUtils.isEmpty(limit)
                || !StringUtils.isEmpty(page)
                || !StringUtils.isEmpty(page)) {
                return getPlanetByPageAndLimit(page == null ? -1 : page,
                                                limit  == null ? -1 : limit,
                                                sort  == null ? "" : sort);
        }

        List<Planet> allPlanets = planetService.getAll();

        if (allPlanets.isEmpty()){
            return new ResponseEntity<Response>(new Response(ResponseMessage.NO_PLANET_FOUND.getMessage()),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(allPlanets, HttpStatus.OK);
    }

    public ResponseEntity<?> getPlanetByName(@RequestParam String nome) {
        List<Planet> planetsByName = planetService.getAllByName(nome);

        if(planetsByName.isEmpty()){
            return new ResponseEntity<Response>(new Response(ResponseMessage.NO_PLANET_FOUND_BY_NAME.getMessage() + nome),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(planetsByName, HttpStatus.OK);
    }

    private ResponseEntity<?> getPlanetByPageAndLimit(int page, int size, String sort) {
        return ResponseEntity.ok(planetService.getAllByPageAndSize(page, size, sort));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getPlanetById(@PathVariable ObjectId id) {
        Optional<Planet> planet = planetService.getById(id);

        if (!planet.isPresent()){
            return new ResponseEntity<Response>(new Response(ResponseMessage.NO_PLANET_FOUND_BY_ID.getMessage() + id),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(planet.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createPlanet(@Valid @RequestBody Planet planet) {
        ObjectId id = ObjectId.get();
        planet.set_id(id);
        planet = planetService.save(planet);

        headers = new HttpHeaders();
        uriComponents = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(id);
        headers.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(planet, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyPlanetByObjectId(@PathVariable ObjectId id, @Valid @RequestBody Planet planet) {
        planet.set_id(id);

        Optional<Planet> p = planetService.getById(id);

        if (!p.isPresent()){
            return new ResponseEntity<Response>(new Response(ResponseMessage.NO_PLANET_FOUND_BY_ID.getMessage() + id),
                    HttpStatus.NOT_FOUND);
        }

        planet = planetService.save(p.get());

        if (planet == null) {
            return new ResponseEntity<Response>(new Response(ResponseMessage.ERROR_UPDATING_PLANET.getMessage() + planet),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlanetByObjectId(@PathVariable ObjectId id) {
        Optional<Planet> planet = planetService.getById(id);

        if (!planet.isPresent()){
            return new ResponseEntity<Response>(new Response(ResponseMessage.NO_PLANET_FOUND_BY_ID.getMessage() + id),
                    HttpStatus.NOT_FOUND);
        }

        if(planetService.delete(id)){
            return ResponseEntity.ok().body(new Response(ResponseMessage.PLANET_SUCCESSFULLY_DELETED.getMessage() + id));
        }

        return new ResponseEntity<Response>(new Response(ResponseMessage.ERROR_DELETING_PLANET.getMessage() + id),
                HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @DeleteMapping
    public ResponseEntity<Response> deleteAllPlanets() {
        if (planetService.deleteAll()){
                return ResponseEntity.ok().body(new Response(ResponseMessage.ALL_PLANETS_DELETED.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(ResponseMessage.ERROR_DELETING_ALL_PLANETS.getMessage()));
    }
}
