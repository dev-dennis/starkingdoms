package starkingdoms.buildorder.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameResultTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		// placeholder
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {

		// placeholder
	}

	@BeforeEach
	void setUp() throws Exception {

		// placeholder
	}

	@AfterEach
	void tearDown() throws Exception {

		// placeholder
	}

	@Test
	void shouldBeEqual() {

		GameResult gameResult = new GameResult(0, 0);
		GameResult gameResult2 = new GameResult(0, 0);
		assertEquals(gameResult, (gameResult2));
	}

}
