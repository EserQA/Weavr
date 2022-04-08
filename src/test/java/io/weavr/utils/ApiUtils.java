package io.weavr.utils;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriverException;

import static io.restassured.RestAssured.*;

public class ApiUtils {

        public static String generateToken(String email, String password){

            String payload = "{\n" +
                    "  \"email\": \""+email+"\",\n" +
                    "  \"password\": {\n" +
                    "    \"value\": \""+password+"\"\n" +
                    "  }\n" +
                    "}";

            Response response = given().contentType(ContentType.JSON)
                    .and()
                    .header("api-key", PropertyReader.get("api-key"))
                    .header("idempotency-ref", "eser")
                    .and()
                    .body(payload)
                    .when()
                    .post(PropertyReader.get("apiUrl") + "/gateway/login");


            if (response.statusCode()!=200) {

                response.prettyPrint();
                throw new WebDriverException("Token does NOT generated");
            }

            String token = response.path("token");
            String bearerToken = "Bearer "+ token;

            return bearerToken;
        }

}
