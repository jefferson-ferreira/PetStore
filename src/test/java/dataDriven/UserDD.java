package dataDriven;

import com.opencsv.exceptions.CsvException;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserDD {
    String uri = "https://petstore.swagger.io/v2/user";

    @DataProvider
    public Iterator<Object[]> provider() throws IOException {
        List<Object[]> dadosTeste = new ArrayList<>();
        String[] testCase;
        String linhaTeste;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/test/resources/pets/users.csv"));
        while ((linhaTeste = bufferedReader.readLine()) != null) {
            testCase = linhaTeste.split(",");
            dadosTeste.add(testCase);
        }

        return dadosTeste.iterator();
    }

    @BeforeMethod
    public void setup() {
        Data data = new Data();
    }

    @Test(dataProvider = "provider")
    public void incluirUsuario(String id, String username, String firstName,
                               String lastName,String email,String password,
                               String phone,String userStatus) throws IOException {

        String jsonBody = new JSONObject()
                .put("id", id)
                .put("username", username)
                .put("firstName", firstName)
                .put("lastName", lastName)
                .put("email", email)
                .put("password", password)
                .put("phone", phone)
                .put("userStatus", userStatus)
        .toString();

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
