package com.henriquechaves.cashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CashcardApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void myFirstTest(){
		assertEquals(42, 42);
	}

}
