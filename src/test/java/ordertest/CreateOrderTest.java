package orderTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
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

public class CreateOrderTest {
    private StepsOrder stepsOrder = new StepsOrder();

    @BeforeEach
    public void before() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }
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
       Order order = new Order(FIRST_NAME,LAST_NAME,ADDRESS,METRO_STATION,PHONE,RENTTIME,DELIVERY_DATE,COMMENT,color);
        Number track = stepsOrder.createOrder(order).then().statusCode(201).extract().path("track");
        System.out.println("track:"+track);
        stepsOrder.searchOrder(track).then().statusCode(200);
      stepsOrder.cancelOrder(track).then().statusCode(200);

    }


}
