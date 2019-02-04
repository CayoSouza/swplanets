package br.com.b2w.desafio.swplanets.model;

import br.com.b2w.desafio.swplanets.util.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {

    private String message;

    public Response(ResponseMessage responseMessage) {
        this.message = responseMessage.getMessage();
    }
}
