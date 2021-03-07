package com.typicode.jsonplaceholder;

import org.testng.annotations.Listeners;
import org.testng.reporters.JUnitXMLReporter;
import org.uncommons.reportng.HTMLReporter;
import specs.RequestSpec;

import java.util.List;

import static io.restassured.RestAssured.given;

@Listeners({HTMLReporter.class, JUnitXMLReporter.class})
public class BaseTest {

    protected List<Integer> getPostsIdByUserId(Integer userId) {
        return given(RequestSpec.get()).queryParam("userId", userId).get("posts/").jsonPath().getList("id");
    }

    protected List<Integer> getUserId(String userName) {
        return given(RequestSpec.get()).queryParam("username", userName).get("users").jsonPath().getList("id");
    }
}
