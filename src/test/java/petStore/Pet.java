// 1 - Pacote
package petStore;

// 2 - Bibliotecas

import static org.hamcrest.Matchers.*;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.*;

// 3 - Classe
public class Pet {
    // 3.1 - Atributos
    String uri ="https://petstore.swagger.io/v2/pet"; // endereço da entidade Pet

    // 3.2 - Métodos e Funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir - Create - Post
    @Test(priority = 1) // Identifica o método ou função como um teste para o TestNG.
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("src/test/resources/pets/pet_01.json");

        // Sintaxe Gherkin
        // Dado - Quando - Então
        // Given - When - Then

        given() //Dado
                .contentType("application/json") // comum em API REST - antigas era "ext/xml"
                .log().all()
                .body(jsonBody)
        .when() // Quando
                .post(uri)
        .then() // Então
                .log().all()
                .statusCode(200)
                .body("name", is("Pantera"))
                .body("status", is("available"))
                .body("category.name", is("Cachorros"))
                .body("tags.name", contains("treinamento API"))
        ;
    }

    @Test(priority = 2)
    public void consultaarPet(){
        String petId = "1983083138";

        given()
                .contentType("application/json")
                .log().all()
        .when() // Quando
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Pantera"))
                .body("category.name", is("Cachorros"))
                .body("status", is("available"))
        ;
    }

    @Test(priority = 3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("src/test/resources/pets/pet_02.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Pantera"))
                .body("status", is("sold"))
        ;
    }

    @Test(priority = 4)
    public void excluirPet() {
        String petId = "1983083138";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))
        ;
    }

    @Test
    public void consultarPetPorStatus(){
        String status = "available";

        given()
                .contentType("application/json")
        .when()
                .log().all()
                .get(uri + "/findByStatus?status=" + status)
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("name[]", everyItem(equalTo("Pantera")))
        ;
    }
}
