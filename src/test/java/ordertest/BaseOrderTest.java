package ordertest;

import io.restassured.RestAssured;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import steps.StepsOrder;

import static api.BaseApi.BASE_URL;
import static org.apache.http.HttpStatus.SC_OK;

public class BaseOrderTest {
    public int trackId;
    public StepsOrder stepsOrder = new StepsOrder();
    @BeforeEach
    public void before() {
        RestAssured.baseURI = BASE_URL;
    }
    @AfterEach
    public void after() {
        if (trackId != 0) {
            stepsOrder.cancelOrder(trackId).then().statusCode(SC_OK);
            trackId = 0;
        }
    }
}
