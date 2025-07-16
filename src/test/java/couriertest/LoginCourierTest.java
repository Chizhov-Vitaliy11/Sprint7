package couriertest;

import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import steps.StepsCourier;

import java.util.stream.Stream;

import static constants.ConstantCourier.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.*;

public class LoginCourierTest extends BaseCourierTest {


    @Test
    @DisplayName("Успешная авторизация курьера")
    void loginCourierSuccessTest() {
        stepsCourier.createCouirer(LOGIN, PASSWORD, NAME)
                .then()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
        this.idCouirer = stepsCourier.getIdCouirer(LOGIN, PASSWORD);

    }


    static Stream<Arguments> paramsLoginFailed() {
        return Stream.of(
                Arguments.of(LOGIN + "123", PASSWORD),//  Неправильный ввод логина
                Arguments.of(LOGIN, PASSWORD + "123"),// Неправильный ввод пароля
                Arguments.of(LOGIN + "123", PASSWORD + "123")//  Неправильный ввод логина и пароля.
        );
    }

    @ParameterizedTest
    @MethodSource("paramsLoginFailed")
    @DisplayName("Неуспешная авторизация курьера")
    @Description("Ввод некорректно пароля или логина курьера")
    void LoginCourierFailedTest(String login, String password) {

        stepsCourier.createCouirer(LOGIN, PASSWORD, NAME)
                .then()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
       stepsCourier.loginCouirer(login, password)
               .then()
               .statusCode(SC_NOT_FOUND)
               .body("message", equalTo("Учетная запись не найдена"));
        idCouirer = stepsCourier.getIdCouirer(LOGIN, PASSWORD);

    }


    static Stream<Arguments> paramsInsufficientDataForLogin() {
        return Stream.of(
                Arguments.of("", PASSWORD),//  Нет логина
                Arguments.of(LOGIN, ""),// Нет пароля
                Arguments.of("", "")// Нет пароля и логина
        );
    }

    @ParameterizedTest
    @MethodSource("paramsInsufficientDataForLogin")
    @DisplayName("Проверка на полноту данных для авторизации курьера")
    void InsufficientDataForLoginCourierTest(String login, String password) {
        stepsCourier.createCouirer(LOGIN, PASSWORD, NAME)
                .then()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
        stepsCourier.loginCouirer(login, password)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
        idCouirer = stepsCourier.getIdCouirer(LOGIN, PASSWORD);

    }


    @Test
    @DisplayName("Получение id курьера")
    void getIdCourierTest() {
        stepsCourier.createCouirer(LOGIN, PASSWORD, NAME)
                .then()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
        stepsCourier
                .loginCouirer(LOGIN, PASSWORD)
                .then()
                .statusCode(SC_OK)
                .body("id", notNullValue());
        idCouirer = stepsCourier.getIdCouirer(LOGIN, PASSWORD);
    }


}
