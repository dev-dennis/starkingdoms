package starkingdoms.buildorder.game;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {

	private Game mGame;

	@BeforeEach
	void setup() {

		// placeholder
	}

	@Test
	void whenGameConstructedThenGameHasLand() {

		mGame = new Game(300);

		assertThat(mGame.getLand(), is(300));
	}

	@Test
	void whenTurnThenLandProduced() throws OrderException {

		mGame = new Game(300);
		mGame.requestOrder(24);
		mGame.turn();

		assertThat(mGame.getLand(), is(301));
	}

	@Test
	void whenTurnThenLastQuePositionCleared() throws OrderException {

		mGame = new Game(300);
		mGame.requestOrder(24);
		mGame.turn();

		assertThat(mGame.getQue()[23], is(0));
	}

	@Test
	void shouldTake10TurnsForNextTenPot() throws OrderException {

		mGame = new Game(300);
		mGame.requestOrder(24);

		assertThat(mGame.getTurnsUntilNextTenPot(), is(10));
	}

	@Test
	void shouldTake5TurnsForNextTenPot() throws OrderException {

		mGame = new Game(600);
		mGame.requestOrder(48);

		assertThat(mGame.getTurnsUntilNextTenPot(), is(5));
	}

	@Test
	void shouldTake5TurnsForNextTenPotWhenOrderMax() {

		mGame = new Game(600);

		assertThat(mGame.getTurnsUntilNextTenPotencyWhenOrderMax(), is(5));
	}

	@Test
	void shouldTake10TurnsForNextTenPotWhenOrderMax() {

		mGame = new Game(300);

		assertThat(mGame.getTurnsUntilNextTenPotencyWhenOrderMax(), is(10));
	}
}
