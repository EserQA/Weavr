package io.weavr.stepDefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.weavr.pages.LoginPage;
import io.weavr.utils.ApiUtils;
import io.weavr.utils.Driver;
import io.weavr.utils.PropertyReader;
import org.hamcrest.Matchers;
import org.junit.Assert;


public class LoginStepsDef {

    LoginPage loginPage = new LoginPage();

    @Given("the user navigates to the login page")
    public void the_user_navigates_to_the_login_page() {

        // This step occurs in to the @BeforeMethod
    }

    @When("the user enters email")
    public void the_user_enters_email() {

        loginPage.emailBox.sendKeys(PropertyReader.get("email"));
    }

    @When("the user enters password")
    public void the_user_enters_password() {

        loginPage.passwordBox.sendKeys(PropertyReader.get("password"));
    }


    @When("the user clicks on the Sign in button")
    public void the_user_clicks_on_the_Sign_in_button() {

        loginPage.signInBtn.click();
    }

    @Then("the user logs in and lands on {string} page")
    public void the_user_logs_in_and_lands_on_page(String expectedTitle) {

        String actualTitle = Driver.get().getTitle();
        Assert.assertEquals("Title does NOT match",expectedTitle,actualTitle);
    }

    @Then("the user logs in with {string}, {string} and status code is {int}")
    public void the_user_logs_in_with_and_status_code_is(String email, String password, Integer expectedStatusCode) {

        String token = ApiUtils.generateToken(PropertyReader.get(email), PropertyReader.get(password));
        String expectedEmail = PropertyReader.get(email);

        // verification with hamcrest
        given().log().all().accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .header("authorization",token)
                .when()
                .post(PropertyReader.get("apiUrl")+"/innovator/get")
                .then()
                .assertThat().statusCode(expectedStatusCode)
                .and()
                .assertThat().contentType("application/json")
                .and()
                .header("Date",Matchers.notNullValue())
                .and()
                .assertThat().body("rootUser.email", Matchers.equalTo(expectedEmail))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("LoginSchema.json"))
                .log().all();
    }

    @When("the user enters {string}")
    public void the_user_enters(String email) {

        loginPage.emailBox.sendKeys(PropertyReader.get(email));
    }

    @When("the user enters the {string}")
    public void the_user_enters_the(String password) {

        loginPage.passwordBox.sendKeys(PropertyReader.get(password));
    }

    @Then("the user can not be able to log in and the following message should be displayed")
    public void the_user_can_not_be_able_to_log_in_and_the_following_message_should_be_displayed(String expectedErrorMessage) {

        String[] errorMessage = loginPage.errorMsg.getText().trim().split("\n");
        String actualErrorMessage = errorMessage[1].trim();

        Assert.assertEquals("Message does NOT displayed",expectedErrorMessage,actualErrorMessage);
    }

    @Then("the user can not be able log in with invalid {string}, {string} and status code is {int}")
    public void the_user_can_not_be_able_log_in_with_invalid_and_status_code_is(String email, String password, Integer expectedStatusCode) {

        String payload = "{\n" +
                "  \"email\": \""+PropertyReader.get(email)+"\",\n" +
                "  \"password\": {\n" +
                "    \"value\": \""+PropertyReader.get(password)+"\"\n" +
                "  }\n" +
                "}";

        given().log().all().contentType(ContentType.JSON)
                .and()
                .header("api-key", PropertyReader.get("api-key"))
                .header("idempotency-ref", "eser")
                .and()
                .body(payload)
                .when()
                .post(PropertyReader.get("apiUrl") + "/gateway/login")
                .then()
                .assertThat().statusCode(expectedStatusCode)
                .and().contentType("application/json")
                .and().header("Date",Matchers.notNullValue())
                .and()
                .assertThat().body("errorCode",Matchers.equalTo("INVALID_CREDENTIALS"))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("InvalidLoginSchema.json"))
                .log().all();
    }


}
