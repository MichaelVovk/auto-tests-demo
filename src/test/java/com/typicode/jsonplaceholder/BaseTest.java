package com.typicode.jsonplaceholder;

import com.typicode.jsonplaceholder.dp.BaseDP;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BaseTest {
    protected List<Integer> getPostsIdByUserId(Integer userId) {
        return given().baseUri(BaseDP.BASE_URL + "posts/").queryParam("userId", userId).get().jsonPath().getList("id");
    }

    protected List<Integer> getUserId(String userName) {
        return given().baseUri(BaseDP.BASE_URL + "users").queryParam("username", userName).get().jsonPath().getList("id");
    }
}
