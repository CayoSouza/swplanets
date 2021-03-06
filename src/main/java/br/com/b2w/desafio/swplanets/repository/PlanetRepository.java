package br.com.b2w.desafio.swplanets.repository;

import br.com.b2w.desafio.swplanets.model.Planet;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface PlanetRepository extends MongoRepository<Planet, String> {

    Optional<Planet> findBy_id(ObjectId _id);

    List<Planet> findAllByName(String name);

}
