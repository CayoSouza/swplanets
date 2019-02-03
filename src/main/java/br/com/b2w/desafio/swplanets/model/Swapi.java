package br.com.b2w.desafio.swplanets.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Swapi {
    private String next;
    private List<Planet> results;
}
