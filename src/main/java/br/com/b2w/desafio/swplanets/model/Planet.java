package br.com.b2w.desafio.swplanets.model;

import br.com.b2w.desafio.swplanets.model.dto.SwapiDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "planets")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Planet {

    @Id
    private ObjectId _id;
    private String name;
    private String climate;
    private String terrain;
    private int filmsApparitions;
    private List<String> films;

    public Planet(){
    }

    public Planet(SwapiDTO swapiDTO){
        set_id(ObjectId.get());
        setName(swapiDTO.getName());
        setClimate(swapiDTO.getClimate());
        setTerrain(swapiDTO.getTerrain());
        setFilmsApparitions(swapiDTO.getFilmsApparitions());
        setFilms(swapiDTO.getFilms());
    }

    public String get_id() {
        return _id.toHexString();
    }
}
