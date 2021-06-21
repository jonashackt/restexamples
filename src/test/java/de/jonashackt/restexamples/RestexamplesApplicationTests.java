package de.jonashackt.restexamples;

import de.jonashackt.restexamples.controller.Controller;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
		classes = RestexamplesApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class RestexamplesApplicationTests {

	@LocalServerPort
	private int port;

	@BeforeEach
	public void init() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	public void testWithSpringRestTemplate() {
	    // Given
	    RestTemplate restTemplate = new RestTemplate();
	    
	    // When
	    String response = restTemplate.getForObject("http://localhost:"+ port + "/restexamples/hello", String.class);
	    
	    // Then
	    assertEquals(Controller.RESPONSE, response);
	}
	
	/**
	 * Using Restassured for elegant REST-Testing, see https://github.com/jayway/rest-assured
	 */
	@Test
    public void testWithRestAssured() {
	    
	    given() // can be ommited when GET only
        .when() // can be ommited when GET only
            .get("/restexamples/hello")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .assertThat()
				.body(is(Controller.RESPONSE));
    }

    @Test public void
	check_if_responded_branch_name_is_correct() {
		get("/restexamples/branchname")
		.then()
			.statusCode(HttpStatus.SC_OK)
			.equals(Controller.BRANCH_RESPONSE + "master");
	}

}
