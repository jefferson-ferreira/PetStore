// 1 - Pacote
package petStore;

// 2 - Bibliotecas

import io.restassured.http.ContentType;
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
    @Test // Identifica o método ou função como um teste para o TestNG.
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
        ;
    }

}
