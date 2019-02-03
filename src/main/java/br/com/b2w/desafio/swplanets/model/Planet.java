package br.com.b2w.desafio.swplanets.model;

import br.com.b2w.desafio.swplanets.model.dto.SwapiDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;

@Document(collection = "planets")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Planet {

    @Id
//    @Getter(AccessLevel.NONE)
    private ObjectId _id;
//    private String id;
    private String name;
    private String climate;
    private String terrain;
    private List<String> films;

    public Planet(){

    }

    public Planet(SwapiDTO swapiDTO){
        set_id(ObjectId.get());
        setName(swapiDTO.getName());
        setClimate(swapiDTO.getClimate());
        setTerrain(swapiDTO.getTerrain());
        setFilms(swapiDTO.getFilms());
    }

    public String get_id() {
        return _id.toHexString();
    }
//
//    public void set_id(ObjectId _id) {
//        this._id = _id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getClimate() {
//        return climate;
//    }
//
//    public void setClimate(String climate) {
//        this.climate = climate;
//    }
//
//    public String getTerrain() {
//        return terrain;
//    }
//
//    public void setTerrain(String terrain) {
//        this.terrain = terrain;
//    }
//
//    public List<Integer> getFilms() {
//        return films;
//    }
//
//    public void setFilms(List<Integer> films) {
//        this.films = films;
//    }
}
