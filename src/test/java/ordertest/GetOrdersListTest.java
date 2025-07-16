package orderTest;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import steps.StepsCourier;
import steps.StepsOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrdersListTest {
    private StepsOrder stepsOrder = new StepsOrder();
    @BeforeEach
    public void before() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

    }

@Test
    public void getOrdersListTest() {
    stepsOrder.getOrdersList()
            .then()
            .statusCode(200)
            .body("orders",notNullValue())
            .body("orders.size()",greaterThan(0));

}

}
