package br.com.b2w.desafio.swplanets.model;

import br.com.b2w.desafio.swplanets.model.dto.SwapiDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Swapi {
    private String next;
    private List<SwapiDTO> results;
}
