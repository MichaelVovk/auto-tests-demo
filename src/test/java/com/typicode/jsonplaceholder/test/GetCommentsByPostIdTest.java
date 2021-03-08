package com.typicode.jsonplaceholder.test;

import com.typicode.jsonplaceholder.BaseTest;
import common.Config;
import common.Constants;
import models.CommentsModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.testng.annotations.Test;
import specs.RequestSpec;
import specs.ResponseSpec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class GetCommentsByPostIdTest extends BaseTest {

  @Test(description = "Verify for each comment in post by user emails format is correct.")
  public void checkEmailsProperFormat() {
    List<CommentsModel> emails = getEmailsFromEachPost(getValidPostId());
    assertThat(emails).isNotNull().isNotEmpty();
    emails.forEach(s ->
            assertThat(EmailValidator.getInstance().isValid(s.getEmail())).isTrue());
  }

  @Test(description = "Verify comments empty if Post Is is empty.")
  public void checkCommentsByEmptyPostIdProperFormat() {
    given()
        .spec(RequestSpec.get())
    .when()
        .get(Constants.POSTS + "/ /" + Constants.COMMENTS)
    .then()
        .spec(ResponseSpec.okEmptyArrayResponse());

  }

  @Test(description = "Verify comments empty if Post is invalid.")
  public void checkCommentsByInvalidPostId() {
    given()
        .spec(RequestSpec.get())
    .when()
        .get(Constants.POSTS + "/" + RandomStringUtils.randomAlphanumeric(10) + "/"+ Constants.COMMENTS)
    .then()
        .spec(ResponseSpec.okEmptyArrayResponse());

  }

  private List<CommentsModel> getEmailsFromEachPost(List<Integer> postId) {
    List<CommentsModel> emails = new ArrayList<>();
    postId.forEach(
        s ->
            emails.addAll(
                Arrays.asList(
                    given()
                        .spec(RequestSpec.get())
                    .when()
                        .get(Constants.POSTS + "/" + s + "/" + Constants.COMMENTS)
                    .then()
                        .spec(ResponseSpec.okResponse())
                        .extract()
                        .body()
                        .as(CommentsModel[].class))));

    return emails;
  }

  public List<Integer> getValidPostId() {
    List<Integer> userId = getUserId(Config.getProperty("user.name"));
    assertThat(userId).isNotNull();
    assertThat(userId.size()).isEqualTo(1);
    return getPostsIdByUserId(userId.get(0));
  }
}
