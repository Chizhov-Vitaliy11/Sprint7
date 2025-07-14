package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import models.Order;

import static io.restassured.RestAssured.given;

public class StepsOrder {
    @Step("Получить список заказов")
    public Response getOrdersList() {
        return given()
                .get("/api/v1/orders");

    }


    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return given()
                .header("Content-Type", "application/json")
                .body(order)
                .when()
                .post("/api/v1/orders");
    }


    @Step("Отменить заказ")
    public Response cancelOrder(Number trackId) {
        return given()
               // .header("Content-Type", "application/json")
                //.body("{\"track\": "+trackId+"}")
                .put("/api/v1/orders/cancel?track="+trackId);

    }

    @Step("Поиск заказа")
    public Response searchOrder(Number trackId) {
        return given()
                .when()
                .get("/api/v1/orders/track?t="+ trackId);
    }

}
