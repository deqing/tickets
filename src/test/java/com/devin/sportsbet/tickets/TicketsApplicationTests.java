package com.devin.sportsbet.tickets;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class TicketsApplicationTests {

  @LocalServerPort private Integer port;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @Test
  void shouldCalculateCosts_example1() {
    given()
        .contentType(ContentType.JSON)
        .body(
            """
			{
				"transactionId": 1,
				"customers": [
					{
						"name": "John Smith",
						"age": 70
					},
					{
						"name": "Jane Doe",
						"age": 5
					},
					{
						"name": "Boe Doe",
						"age": 6
					}
				]
			}
			""")
        .when()
        .post("/costs")
        .then()
        .statusCode(HTTP_OK)
        .body("transactionId", equalTo(1))
        .body("tickets.size()", equalTo(2))
        .body("tickets[0].ticketType", equalTo("CHILDREN"))
        .body("tickets[0].quantity", equalTo(2))
        .body("tickets[0].totalCost", equalTo(10.0f))
        .body("tickets[1].ticketType", equalTo("SENIOR"))
        .body("tickets[1].quantity", equalTo(1))
        .body("tickets[1].totalCost", equalTo(17.5f))
        .body("totalCost", equalTo(27.5f));
  }

  @Test
  void shouldCalculateCosts_example2() {
    given()
        .contentType(ContentType.JSON)
        .body(
            """
			{
				"transactionId": 2,
				"customers": [
					{
						"name": "Billy Kidd",
						"age": 36
					},
					{
						"name": "Zoe Daniels",
						"age": 3
					},
					{
						"Name": "George White",
						"Age": 8
					},
					{
						"name": "Tommy Anderson",
						"age": 9
					},
					{
						"name": "Joe Smith",
						"age": 17
					}
				]
			}
			""")
        .when()
        .post("/costs")
        .then()
        .statusCode(HTTP_OK)
        .body("transactionId", equalTo(2))
        .body("tickets.size()", equalTo(3))
        .body("tickets[0].ticketType", equalTo("ADULT"))
        .body("tickets[0].quantity", equalTo(1))
        .body("tickets[0].totalCost", equalTo(25.0f))
        .body("tickets[1].ticketType", equalTo("CHILDREN"))
        .body("tickets[1].quantity", equalTo(3))
        .body("tickets[1].totalCost", equalTo(11.25f))
        .body("tickets[2].ticketType", equalTo("TEEN"))
        .body("tickets[2].quantity", equalTo(1))
        .body("tickets[2].totalCost", equalTo(12.0f))
        .body("totalCost", equalTo(48.25f));
  }

  @Test
  void shouldCalculateCosts_example3() {
    given()
        .contentType(ContentType.JSON)
        .body(
            """
			{
				"transactionId": 3,
				"customers": [
					{
						"name": "Jesse James",
						"age": 36
					},
					{
						"name": "Daniel Anderson",
						"age": 95
					},
					{
						"name": "Mary Jones",
						"age": 15
					},
					{
						"name": "Michelle Parker",
						"age": 10
					}
				]
			}
			""")
        .when()
        .post("/costs")
        .then()
        .statusCode(HTTP_OK)
        .body("transactionId", equalTo(3))
        .body("tickets.size()", equalTo(4))
        .body("tickets[0].ticketType", equalTo("ADULT"))
        .body("tickets[0].quantity", equalTo(1))
        .body("tickets[0].totalCost", equalTo(25.0f))
        .body("tickets[1].ticketType", equalTo("CHILDREN"))
        .body("tickets[1].quantity", equalTo(1))
        .body("tickets[1].totalCost", equalTo(5.0f))
        .body("tickets[2].ticketType", equalTo("SENIOR"))
        .body("tickets[2].quantity", equalTo(1))
        .body("tickets[2].totalCost", equalTo(17.5f))
        .body("tickets[3].ticketType", equalTo("TEEN"))
        .body("tickets[3].quantity", equalTo(1))
        .body("tickets[3].totalCost", equalTo(12.0f))
        .body("totalCost", equalTo(59.50f));
  }

  @Test
  void shouldHandleEmptyBlockOrNullName() {
    given()
        .contentType(ContentType.JSON)
        .body(
            """
			{
				"transactionId": 4,
				"customers": [
					{
					},
					{
						"AGE": 33
					}
				]
			}
			""")
        .when()
        .post("/costs")
        .then()
        .statusCode(HTTP_OK)
        .body("tickets.size()", equalTo(1))
        .body("tickets[0].ticketType", equalTo("ADULT"))
        .body("tickets[0].quantity", equalTo(1))
        .body("tickets[0].totalCost", equalTo(25.0f))
        .body("totalCost", equalTo(25.0f));
  }
}
