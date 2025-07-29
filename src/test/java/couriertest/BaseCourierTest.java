package couriertest;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import steps.StepsCourier;

import static api.BaseApi.BASE_URL;
import static org.apache.http.HttpStatus.SC_OK;

public class BaseCourierTest {
    public int  idCouirer;
    public StepsCourier stepsCourier = new StepsCourier();
    @BeforeEach
    public void before() {
        RestAssured.baseURI = BASE_URL;

    }


    @AfterEach
    public void after() {
        if (idCouirer != 0) {
            stepsCourier.deleteCouirer(idCouirer).then().statusCode(SC_OK);
            idCouirer = 0;
        }
    }
}
