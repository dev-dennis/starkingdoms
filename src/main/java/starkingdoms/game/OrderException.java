package starkingdoms.game;

public class OrderException extends Exception {

    public OrderException(OrderException e, int pTick) {
	super("Tick " + pTick + ":" + e.getMessage());
    }

    public OrderException(int pLand, int pOrderable) {
	super("not ordered:" + pLand + ", max:" + pOrderable);
    }

    private static final long serialVersionUID = 8660909965211540370L;

}
