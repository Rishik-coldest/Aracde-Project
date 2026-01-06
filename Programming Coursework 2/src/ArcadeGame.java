public abstract class ArcadeGame {

    private final String name;
    private final String gameID;
    private final int pricePerPlay;

    // Constructor
    public ArcadeGame(String name, String gameID, int pricePerPlay) throws InvalidGameIDException {
        if (gameID.length() != 10) throw new InvalidGameIDException();
        this.name = name;
        this.gameID = gameID;
        this.pricePerPlay = pricePerPlay;
    }

    // Accessor methods
    public String getName() {
        return name;
    }
    public String getGameID() {
        return gameID;
    }
    public int getPricePerPlay() {
        return pricePerPlay;
    }

    // Abstract method to be overridden by child-classes
    public abstract int calculatePrice(boolean peak);
}