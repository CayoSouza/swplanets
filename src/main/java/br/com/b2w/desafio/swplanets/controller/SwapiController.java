package br.com.b2w.desafio.swplanets.controller;

import br.com.b2w.desafio.swplanets.model.Response;
import br.com.b2w.desafio.swplanets.model.Swapi;
import br.com.b2w.desafio.swplanets.service.SwapiService;
import br.com.b2w.desafio.swplanets.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwapiController {
    @Autowired
    SwapiService swapiService;

    @GetMapping("/swapi/consumir")
    public Swapi consumeSwapi(){
        return swapiService.getPlanetsFromSwapi();
    }

    @GetMapping("/swapi/importar")
    public ResponseEntity<Response> importaSwapi(){
        return swapiService.importToDatabase() ?
                ResponseEntity.ok(new Response(ResponseMessage.SWAPI_SUCCESSFULLY_IMPORTED.getMessage()))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(ResponseMessage.SWAPI_FAIL_IMPORT.getMessage()));
    }
}
