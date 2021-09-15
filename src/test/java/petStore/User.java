package petStore;

import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class User {
    String uri = "https://petstore.swagger.io/v2/user";


    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void incluirUsuario() throws IOException {
        String jsonBody = lerJson("src/test/resources/pets/user1.json");

        String userID =
        given()
                .contentType("application/json")
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("code", is(200))
                .body("type", is("unknown"))
        .extract()
                .path("message")
        ;
        System.out.println("O user ID é "+ userID);
    }
}
