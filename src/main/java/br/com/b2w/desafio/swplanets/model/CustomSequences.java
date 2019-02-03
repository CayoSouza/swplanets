package br.com.b2w.desafio.swplanets.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Document(collection = "customSequences")
@Getter
@Component
public class CustomSequences {
    @Id
    private String id;
    private int seq;

}