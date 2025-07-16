package couriertest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.stream.Stream;

import static constants.ConstantCourier.*;
import static org.hamcrest.Matchers.equalTo;
import static org.apache.http.HttpStatus.*;

public class CreateCourierTest extends BaseCourierTest {






    @Test
    @DisplayName("Успешное создание курьера")
    void createCouirerSuccessTest() {
        ValidatableResponse response =
                stepsCourier.createCouirer(LOGIN, PASSWORD, NAME).
                        then().
                        statusCode(SC_CREATED)
                        .body("ok", equalTo(true));
        this.idCouirer = stepsCourier.getIdCouirer(LOGIN, PASSWORD);


    }

    @Test
    @DisplayName("Проверка уникальность логина при создании курьера")
    void createNotDistinctLoginCouirerTest() {
        stepsCourier.createCouirer(LOGIN, PASSWORD, NAME).
                then().
                statusCode(SC_CREATED)
                .body("ok", equalTo(true));
        stepsCourier.createCouirer(LOGIN, PASSWORD, NAME)
                .then()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        this.idCouirer = stepsCourier.getIdCouirer(LOGIN, PASSWORD);

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
    void insufficientDataForCreateCouirerTest(String login, String password, String name) {
        Response response = stepsCourier.
                createCouirer(login, password, name);


        if (response.getStatusCode() == SC_BAD_REQUEST) {
            stepsCourier.
                    createCouirer(login, password, name)
                    .then()
                    .statusCode(SC_BAD_REQUEST).
                    body("message", equalTo("Недостаточно данных для создания учетной записи"));
        } else {
            this.idCouirer = stepsCourier.getIdCouirer(LOGIN, PASSWORD);
        }
    }





}
