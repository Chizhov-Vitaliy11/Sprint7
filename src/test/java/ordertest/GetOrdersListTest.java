package ordertest;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import steps.StepsOrder;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrdersListTest extends BaseOrderTest {



@Test
    public void getOrdersListTest() {
    stepsOrder.getOrdersList()
            .then()
            .statusCode(SC_OK)
            .body("orders",notNullValue())
            .body("orders.size()",greaterThan(0));

}

}
