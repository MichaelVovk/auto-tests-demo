package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.*;

public class ResponseSpec {


    public static ResponseSpecification okResponse() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(2000L))
                .expectBody(is(not(empty())))
                .build();
    }

    public static ResponseSpecification okEmptyArrayResponse() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(2000L))
                .expectBody(is("[]"))
                .build();
    }

    public static ResponseSpecification notFoundResponse() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_NOT_FOUND)
                .expectResponseTime(lessThan(2000L))
                .build();
    }
}
