package br.com.b2w.desafio.swplanets.service;

import br.com.b2w.desafio.swplanets.model.Planet;
import br.com.b2w.desafio.swplanets.repository.PlanetsRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PlanetService {
    @Autowired
    PlanetsRepository planetsRepository;

   Logger logger = LoggerFactory.getLogger(PlanetService.class);

    public List<Planet> getAll(){
        logger.debug("[INFO] Getting all planets.");
        return planetsRepository.findAll();
    }

    public Page<Planet> getAllByPageAndSize(int page, int limit, String sort){
        if(page == -1){
            page = 1;
        }
        if(limit == -1){
            limit = 10;
        }
        if(StringUtils.isEmpty(sort)){
            sort = "name";
        }
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sort));
        logger.debug("[INFO] Getting planets by page {} and limit {}", page, limit);
        return planetsRepository.findAll(pageable);
    }

    public Planet getById(ObjectId id){
        logger.debug("[INFO] Returning planet with _id {}", id);
        return planetsRepository.findBy_id(id);
    }

    public List<Planet> getAllByName(String nome){
        logger.debug("[INFO] Returning planets with name {}", nome);
        return planetsRepository.findAllByName(nome);
    }

    public Planet save(Planet planet){
        logger.debug("[INFO] Saving planet {}", planet);
        return planetsRepository.save(planet);
    }

    public boolean delete(ObjectId id){
        try {
            logger.debug("[INFO] Deleting planet with _id {}", id);
            planetsRepository.deleteById(id.toHexString());
        } catch (Exception e) {
            logger.error("[ERROR] Error on attempting to delete planet with _id {}", id);
            return false;
        }
        return true;
    }

    public boolean deleteAll(){
        try {
            logger.debug("[INFO] Deleting all planets");
            planetsRepository.deleteAll();
        } catch (Exception e) {
            logger.error("[ERROR] Error attempting to delete all planets.");
            return false;
        }
        return true;
    }
}
