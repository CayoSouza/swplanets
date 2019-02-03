package br.com.b2w.desafio.swplanets.service;

import br.com.b2w.desafio.swplanets.model.Planet;
import br.com.b2w.desafio.swplanets.model.Swapi;
import br.com.b2w.desafio.swplanets.model.dto.SwapiDTO;
import br.com.b2w.desafio.swplanets.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SwapiService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PlanetService planetService;

    Logger logger = LoggerFactory.getLogger(SwapiService.class);

    public Swapi getPlanetsFromSwapi(){
        final String url = Constants.SWAPI_PLANETS;
        logger.debug("[INFO] Consuming URL {}", url);
        Swapi result = restTemplate.getForObject(url, Swapi.class);

        return result;
    }

    public Swapi getPlanetsFromSwapiByPage(String url){
        logger.debug("[INFO] Consuming URL {}", url);
        Swapi result = restTemplate.getForObject(url, Swapi.class);

        return result;
    }

    public boolean importToDatabase(){
        try {
            Swapi result = getPlanetsFromSwapi();
            importPlanetsFromPage(result);

            while (result.getNext() != null) {
                result = getPlanetsFromSwapiByPage(result.getNext());
                importPlanetsFromPage(result);
            }
        } catch (Exception e) {
            logger.error("Error importing to database.");
            return false;
        }
        return true;
    }

    private void importPlanetsFromPage(Swapi result) {
        for (SwapiDTO p : result.getResults()) {
            Planet planet = new Planet(p);
            logger.debug("[INFO] Importing planet {}", planet);
            planetService.save(planet);
        }
    }
}
