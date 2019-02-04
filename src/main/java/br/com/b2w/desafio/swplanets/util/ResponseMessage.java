package br.com.b2w.desafio.swplanets.util;

public enum ResponseMessage {

    NO_PLANET_FOUND("Nenhum planeta encontrado no universo."),
    NO_PLANET_FOUND_BY_NAME("Nenhum planeta encontrado com o nome: "),
    NO_PLANET_FOUND_BY_ID("Nenhum planeta encontrado com o id: "),
    SWAPI_SUCCESSFULLY_IMPORTED("Importado com sucesso."),
    SWAPI_FAIL_IMPORT("Erro na importacao."),
    ALL_PLANETS_DELETED("Todos planetas foram deletados do universo!"),
    ERROR_DELETING_ALL_PLANETS("Algum milagre aconteceu e todos os planetas nao foram deletados!"),
    ERROR_UPDATING_PLANET("Erro na atualizacao do planeta "),
    ERROR_DELETING_PLANET("Erro ao deletar o planeta "),
    PLANET_SUCCESSFULLY_DELETED("Planeta %s deletado com sucesso.");


    private String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
