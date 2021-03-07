package com.typicode.jsonplaceholder;

import common.Constants;
import org.testng.annotations.Listeners;
import org.testng.reporters.JUnitXMLReporter;
import org.uncommons.reportng.HTMLReporter;
import specs.RequestSpec;
import specs.ResponseSpec;

import java.util.List;

import static io.restassured.RestAssured.given;

@Listeners({HTMLReporter.class, JUnitXMLReporter.class})
public class BaseTest {

  protected List<Integer> getPostsIdByUserId(Integer userId) {
    return given(RequestSpec.get())
                .queryParam("userId", userId)
            .when()
                .get(Constants.POSTS)
            .then()
                .spec(ResponseSpec.okResponse())
                .extract()
                .body()
                .jsonPath()
                .getList("id");
  }

  protected List<Integer> getUserId(String userName) {
    return given(RequestSpec.get())
                .queryParam("username", userName)
            .when()
                .get(Constants.USERS)
            .then()
                .spec(ResponseSpec.okResponse())
                .extract()
                .body()
                .jsonPath()
                .getList("id");
  }
}
