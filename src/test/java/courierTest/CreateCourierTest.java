package courierTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import steps.StepsCourier;

import java.util.stream.Stream;

import static constants.ConstantCourier.*;
import static org.hamcrest.Matchers.equalTo;


public class CreateCourierTest {
    private int idCouirer;
    private StepsCourier stepsCourier = new StepsCourier();

    @BeforeEach
    public void before() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

    }


    @Test
    @DisplayName("Успешное создание курьера")
    void createCouirerSuccessTest() {
        ValidatableResponse response = stepsCourier.createCouirer(LOGIN, PASSWORD, NAME).then().statusCode(201);
        idCouirer = stepsCourier.getIdCouirer(LOGIN, PASSWORD);
       stepsCourier.deleteCouirer(idCouirer).then().statusCode(200);

    }

    @Test
    @DisplayName("Проверка уникальность логина при создании курьера")
    void createNotDistinctLoginCouirerTest() {
        stepsCourier.createCouirer(LOGIN, PASSWORD, NAME).then().statusCode(201);
        stepsCourier.createCouirer(LOGIN, PASSWORD, NAME).then().statusCode(409);
        idCouirer = stepsCourier.getIdCouirer(LOGIN, PASSWORD);
        stepsCourier.deleteCouirer(idCouirer);
    }


    static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of("", PASSWORD, NAME),//  Пустой логин
                Arguments.of(LOGIN, "", NAME),// Пустой пароль
                Arguments.of(LOGIN, PASSWORD, "")//  Пустое имя (Курьер создался)
        );
    }

    @ParameterizedTest
    @MethodSource("params")
    @DisplayName("Проверка на полноту данных для создания курьера")
    void InsufficientDataForCreateCouirerTest(String login, String password, String name) {
        Response response = stepsCourier.
                createCouirer(login, password, name);
        ;

       if(response.getStatusCode()==400) {
    stepsCourier.
            createCouirer(login, password, name)
            .then()
            .statusCode(400).
            body("message", equalTo("Недостаточно данных для создания учетной записи"));
}
else {
            stepsCourier.loginCouirer(login, password).then().statusCode(200);
            idCouirer = stepsCourier.getIdCouirer(LOGIN, PASSWORD);
            stepsCourier.deleteCouirer(idCouirer).then().statusCode(200);
        }
}


    @AfterEach
    public void tearDown() {
        if (idCouirer != 0) {
            stepsCourier.deleteCouirer(idCouirer);

            idCouirer = 0;
        }
    }


}
