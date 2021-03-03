package com.typicode.jsonplaceholder.test;

import com.typicode.jsonplaceholder.BaseTest;
import com.typicode.jsonplaceholder.dp.BaseDP;
import models.CommentsModel;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.validator.routines.EmailValidator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class GetCommentsByPostIdTest extends BaseTest {
    private static RequestSpecification spec;

    @BeforeClass
    public static void initSpec() {
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BaseDP.BASE_URL)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @Test()
    public void checkEmailsProperFormat() {
        List<Integer> userId = getUserId(BaseDP.USERNAME);
        assertThat(userId).isNotNull();
        assertThat(userId.size()).isEqualTo(1);
        List<Integer> postId = getPostsIdByUserId(userId.get(0));
        List<CommentsModel> emails = getEmailsFromEachPost(postId);
        assertThat(emails).isNotNull().isNotEmpty();
        emails.forEach(s -> assertThat(EmailValidator.getInstance().isValid(s.getEmail())).isTrue());

    }


    private List<CommentsModel> getEmailsFromEachPost(List<Integer> postId) {
        List<CommentsModel> emails = new ArrayList<>();
        postId.forEach(s ->
                emails.addAll(Arrays.asList(given()
                        .spec(spec)
                        .when()
                        .get("posts/" + s + "/comments")
                        .then()
                        .contentType(ContentType.JSON)
                        .assertThat()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(CommentsModel[].class))));

        return emails;
    }

}
