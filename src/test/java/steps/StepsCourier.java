package steps;

import io.restassured.response.ValidatableResponse;
import models.Courier;

import static constants.ConstantCourier.*;
import static io.restassured.RestAssured.given;
import static api.CourierApi.*;

import io.qameta.allure.Step;

import io.restassured.response.Response;

public class StepsCourier {
    @Step("Создание курьера")
    public Response createCouirer(String login,String password,String name) {
        Courier courier = new Courier(login, password, name);

        return given()
                .header("Content-Type", "application/json")
                .body(courier)
                .when()
                .post(API_CREATE_COURIER);
    }

    @Step("Удаление Курьера")
    public Response deleteCouirer(int idCouirer) {
        return given()
                .header("Content-Type", "application/json")
                .delete(API_DELETE_COURIER + idCouirer);
    }

    @Step("Авторизация курьера")
    public Response loginCouirer(String login, String password) {
        Courier courier = new Courier(login, password);

        return given()
                .header("Content-Type", "application/json")
                .body(courier)
                .when()
                .post(API_LOGIN_COURIER);

    }
    @Step("Получение id Курьера")
    public  int getIdCouirer (String login,String password) {
        int idCouirer = loginCouirer(login,password).then().extract().path("id");
        return idCouirer;
    }

}
