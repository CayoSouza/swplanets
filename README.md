# Star Wars Planets

API que serve planetas do Star Wars, consumindo informação do [swapi.co](https://swapi.co).

## Uso

### Necessário

* Java 11
* Maven
* MongoDB

### Instalação

Tendo instalado o MongoDB, execute:

```
mongod
```

E crie um banco de dados e uma coleção com o nome **planets**

```
use swplanets;
db.createCollection("planets");
```

### Configurando o banco

Mude o arquivo de configuração **application.properties** para o **host**, **port** e **database** do seu MongoDB.
```
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=swplanets
```

### Building

Com Maven(ou use o build do Spring Boot):

```
clean install spring-boot:run
```

**Importante**: para evitar erro de handshake, use essas configurações de VM:

```
-Dhttps.protocols=TLSv1.2,TLSv1.1,TLSv1
```

### Postman Collection

Coleção com todos as requisições disponíveis.

Download: [Google Drive](https://drive.google.com/open?id=17R-QTISGFrerDgA2Ez6Mn30kVIgmRg1a)

![Screenshot](https://i.imgur.com/1849wwQ.png)

### Endpoints


```
GET: /planetas
Retorna todos os planetas.

GET: /planetas/{id}
Retorna o planeta pelo seu ObjectId.

GET: /planetas?nome={nome}
Retorna planetas pelo seu nome.

GET: /planetas/?page=0&limit=20&sort=climate
Retorna planetas filtrando por page, limit e sort.

GET: /swapi/importar
Consome o Swapi.co e persiste todos os planetas no banco.

POST: /planetas
Cria um planeta (JSON no body).

PUT: /planetas/{id}
Atualiza um planeta pelo seu ObjectId.

DELETE: /planetas
Deleta todos os planetas.

DELETE: /planetas/{id}
Deleta um planeta pelo seu ObjectId.
```
