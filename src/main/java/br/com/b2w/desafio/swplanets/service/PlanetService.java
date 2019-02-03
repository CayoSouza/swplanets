package br.com.b2w.desafio.swplanets.service;

import br.com.b2w.desafio.swplanets.model.Planet;
import br.com.b2w.desafio.swplanets.repository.PlanetsRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanetService {
    @Autowired
    PlanetsRepository planetsRepository;
    @Autowired
    NextSequenceService nextSequenceService;

   Logger logger = LoggerFactory.getLogger(PlanetService.class);

    public List<Planet> getAll(){
        logger.debug("[INFO] Getting all planets.");
        return planetsRepository.findAll();
    }

    public Planet getById(ObjectId id){
        logger.debug("[INFO] Returning planet with _id {}", id);
        return planetsRepository.findBy_id(id);
    }

    public List<Planet> getAllByName(String nome){
        logger.debug("[INFO] Returning planets with name {}", nome);
        return planetsRepository.findAllByName(nome);
    }

    public void save(Planet planet){
        logger.debug("[INFO] Saving planet {}", planet);
        planetsRepository.save(planet);
    }

    public void delete(ObjectId id){
        logger.debug("[INFO] Deleting planet with _id {}", id);
        planetsRepository.deleteById(id.toHexString());
    }

    public void deleteAll(){
        logger.debug("[INFO] Deleting all planets");
        planetsRepository.deleteAll();
    }

//    public void updatePlanet(Planet planet){
//        logger.debug("[INFO] Updating planet {}", planet);
//        planetsRepository.save(planet);
//    }
}
