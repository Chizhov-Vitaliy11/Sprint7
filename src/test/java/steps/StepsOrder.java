package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import io.restassured.response.ValidatableResponse;
import models.Order;

import static api.OrderApi.*;
import static io.restassured.RestAssured.given;

public class StepsOrder {
    @Step("Получить список заказов")
    public Response getOrdersList() {
        return given()
                .get(API_GET_ORDERS);

    }


    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return given()
                .header("Content-Type", "application/json")
                .body(order)
                .when()
                .post(API_CREATE_ORDER);
    }


    @Step("Отменить заказ")
    public Response cancelOrder(Number trackId) {
        return given()
               // .header("Content-Type", "application/json")
                //.body("{\"track\": "+trackId+"}")
                .put(API_CANCEL_ORDER+trackId);

    }

    @Step("Поиск заказа")
    public Response searchOrder(int trackId) {
        return given()
              .when()
                .get(API_SEARCH_ORDER+ trackId);
    }

}
