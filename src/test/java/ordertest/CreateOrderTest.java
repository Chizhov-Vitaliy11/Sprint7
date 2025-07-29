package ordertest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import models.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import steps.StepsOrder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static constants.ConstantOrder.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTest extends BaseOrderTest {


    static Stream<List<String>> paramsColor() {
        return Stream.of(
                Collections.singletonList("BLACK"),
                Collections.singletonList("GREY"),
                Arrays.asList("BLACK", "GREY"),
                Collections.emptyList()
        );
    }


    @ParameterizedTest
    @MethodSource("paramsColor")
    @DisplayName("Создание заказа")
    void createOrderTest(List<String> color) {
        Order order = new Order(FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENTTIME, DELIVERY_DATE, COMMENT, color);
        Response response = stepsOrder.createOrder(order);
        response.then()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());

        int track = response.then().extract().path("track");
        stepsOrder.searchOrder(track).then().statusCode(SC_OK).body("order", notNullValue());
        this.trackId = track;


    }


}
