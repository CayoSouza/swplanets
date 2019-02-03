package br.com.b2w.desafio.swplanets.service;

import br.com.b2w.desafio.swplanets.model.Swapi;
import br.com.b2w.desafio.swplanets.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SwapiService {
    @Autowired
    RestTemplate restTemplate;

    public Swapi getPlanets(){
        Swapi result = restTemplate.getForObject(Constants.SWAPI_PLANETS, Swapi.class);
        System.out.println(result);

        return result;
    }
}
