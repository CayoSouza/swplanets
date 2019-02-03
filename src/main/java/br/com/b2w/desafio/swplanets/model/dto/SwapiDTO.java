package br.com.b2w.desafio.swplanets.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SwapiDTO {
    private String name;
    private String climate;
    private String terrain;
    private int filmsApparitions;
    private List<String> films;
}
