package br.com.b2w.desafio.swplanets.controller;

import br.com.b2w.desafio.swplanets.model.Swapi;
import br.com.b2w.desafio.swplanets.service.SwapiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwapiController {
    @Autowired
    SwapiService swapiService;

    @GetMapping("/swapi")
    public Swapi consumeSwapi(){
        return swapiService.getPlanets();
    }
}
