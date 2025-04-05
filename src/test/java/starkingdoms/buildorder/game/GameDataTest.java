package starkingdoms.buildorder.game;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameDataTest {

	private GameData mData;

	@BeforeAll
	static void setUpBeforeClass() {

		// placeholder
	}

	@AfterAll
	static void tearDownAfterClass() {

		// placeholder
	}

	@BeforeEach
	void setUp() {

		mData = new GameData(300);
	}

	@AfterEach
	void tearDown() {

		// placeholder
	}

	@Test
	final void testDataInt() {

		mData = new GameData(600);
		assertThat(mData.getLand(), is(600));
	}

	@Test
	final void testDataIntIntArray() {

		mData = new GameData(500, new int[24]);
		assertThat(mData.getLand(), is(500));
		assertThat(mData.getLandInQue(), is(0));
		assertThat(mData.isCloned(), is(true));
	}

	@Test
	final void testGetLandInQue1() {

		assertDoesNotThrow(() -> mData.requestOrder(24));
		assertThat(mData.getLandInQue(), is(24));
	}

	@Test
	final void testGetLandInQue2() {

		assertDoesNotThrow(() -> mData.requestOrder(0));
		assertThat(mData.getLandInQue(), is(0));
	}

	@Test
	final void testGetLandInQue3() {

		assertDoesNotThrow(() -> mData.requestOrder(30));
		assertThat(mData.getLandInQue(), is(30));
	}

	@Test
	final void testGetLandInQue4() {

		assertThrows(OrderException.class, () -> mData.requestOrder(31));
	}

	@Test
	final void testGetMaxOrderable() {

		assertThat(mData.getMaxOrderable(), is(30));
	}

	@Test
	final void testGetOrderable1() {

		assertThat(mData.getOrderable(), is(30));
	}

	@Test
	final void testGetOrderable2() {

		assertDoesNotThrow(() -> mData.requestOrder(30));
		assertThat(mData.getOrderable(), is(0));
	}

	@Test
	final void testGetOrderable3() {

		assertDoesNotThrow(() -> mData.requestOrder(24));
		assertThat(mData.getOrderable(), is(6));
	}

	@Test
	final void testGetNextPotencyOf() {

		assertThat(mData.getNextPotencyOf(5), is(305));
	}

	@Test
	final void testGetNextTenPot() {

		assertThat(mData.getNextTenPot(), is(310));
	}

	@Test
	final void testGetQueSize() {

		assertThat(mData.getQueSize(), is(24));
	}

	@Test
	final void testRequestOrder1() {

		assertDoesNotThrow(() -> mData.requestOrder(24));
		assertThat(mData.getQue()[0], is(1));
		assertThat(mData.getQue()[23], is(1));
	}

	@Test
	final void testRequestOrder2() {

		assertDoesNotThrow(() -> mData.requestOrder(25));
		assertThat(mData.getQue()[0], is(1));
		assertThat(mData.getQue()[23], is(2));
	}

	@Test
	final void testRequestOrder3() {

		assertDoesNotThrow(() -> mData.requestOrder(0));
		assertThat(mData.getQue()[0], is(0));
		assertThat(mData.getQue()[23], is(0));
	}

	@Test
	final void testTurn1() {

		assertDoesNotThrow(() -> mData.requestOrder(24));
		mData.turn();

		assertThat(mData.getLand(), is(301));
	}

	@Test
	final void testTurn2() {

		assertDoesNotThrow(() -> mData.requestOrder(24));
		mData.turn();

		assertThat(mData.getQue()[23], is(0));
	}

	@Test
	final void testClone() {

		GameData lClone = new GameData(mData);
		assertThat(lClone.getLand(), is(300));
		assertThat(lClone.getLandInQue(), is(0));
	}

	@Test
	final void testGetQue() {

		assertThat(mData.getQue()[0], is(0));
	}

	@Test
	final void testGetLand() {

		assertThat(mData.getLand(), is(300));
	}

	@Test
	final void testIsCloned() {

		GameData lClone = new GameData(mData);
		assertThat(lClone.isCloned(), is(true));
	}

}
