package com.henriquechaves.cashcard;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CashCardApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;
	@Test
	void shouldReturnACashCardWhenDataIsSaved(){
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/cashcards/99", String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		DocumentContext documentContext = JsonPath.parse(response.getBody());

		Number id = documentContext.read("$.id");
		assertNotNull(id);
		assertEquals(99, id);

		Number amount = documentContext.read("$.amount");
		assertEquals(123.45, amount);
	}

  @Test
  void shouldNotReturnACashCardWithAnUnknownId(){
    ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/1000", String.class);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

}
