package courierTest;

import io.restassured.RestAssured;
import jdk.jfr.Description;
import models.Courier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import steps.StepsCourier;

import java.util.stream.Stream;

import static constants.ConstantCourier.*;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {

    private int idCouirer;
    private StepsCourier stepsCourier = new StepsCourier();

    @BeforeEach
    public void before() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

    }


    @Test
    @DisplayName("Успешная авторизация курьера")
    void LoginCourierSuccessTest() {
        stepsCourier.createCouirer(LOGIN, PASSWORD, NAME).then().statusCode(201);
        stepsCourier.loginCouirer(LOGIN, PASSWORD).then().statusCode(200);
        idCouirer = stepsCourier.getIdCouirer(LOGIN, PASSWORD);
        stepsCourier.deleteCouirer(idCouirer).then().statusCode(200);
    }


    static Stream<Arguments> paramsLoginFailed(){
        return Stream.of(
                Arguments.of(LOGIN+"123",PASSWORD),//  Неправильный ввод логина
                Arguments.of(LOGIN,PASSWORD+"123" ),// Неправильный ввод пароля
                Arguments.of(LOGIN+"123", PASSWORD+"123")//  Неправильный ввод логина и пароля.
        );
    }
    @ParameterizedTest
    @MethodSource("paramsLoginFailed")
    @DisplayName("Неуспешная авторизация курьера")
    @Description("Ввод некорректно пароля или логина курьера")
    void LoginCourierFailedTest(String login,String password){
        stepsCourier.createCouirer(LOGIN, PASSWORD, NAME).then().statusCode(201);
        stepsCourier.loginCouirer(login, password).then().statusCode(404);
        idCouirer = stepsCourier.getIdCouirer(LOGIN, PASSWORD);
        stepsCourier.deleteCouirer(idCouirer).then().statusCode(200);
    }




    static Stream<Arguments> paramsInsufficientDataForLogin(){
        return Stream.of(
                Arguments.of("",PASSWORD),//  Нет логина
                Arguments.of(LOGIN,"" ),// Нет пароля
                Arguments.of("", "")// Нет пароля и логина
        );
    }
    @ParameterizedTest
    @MethodSource("paramsInsufficientDataForLogin")
    @DisplayName("Проверка на полноту данных для авторизации курьера")
    void InsufficientDataForLoginCourierTest(String login,String password){
        stepsCourier.createCouirer(LOGIN, PASSWORD, NAME).then().statusCode(201);
        stepsCourier.loginCouirer(login, password).then().statusCode(400);
        idCouirer = stepsCourier.getIdCouirer(LOGIN, PASSWORD);
        stepsCourier.deleteCouirer(idCouirer).then().statusCode(200);
    }


    @Test
    @DisplayName("Получение id курьера")
    void getIdCourierTest(){

        stepsCourier
                .createCouirer(LOGIN, PASSWORD, NAME)
                .then()
                .statusCode(201);
        stepsCourier
                .loginCouirer(LOGIN, PASSWORD)
                .then().
                statusCode(200).
                body("id",notNullValue());
        idCouirer = stepsCourier.getIdCouirer(LOGIN, PASSWORD);
        stepsCourier.deleteCouirer(idCouirer).then().statusCode(200);
    }



}
