// CabinetGame inherits from ArcadeGame
public class CabinetGame extends ArcadeGame {

    private final boolean rewardPayOut;

    // Constructor
    public CabinetGame(String name, String gameID, int pricePerPlay, boolean rewardPayOut) throws InvalidGameIDException {

        super(name, gameID, pricePerPlay);
        if (gameID.length() != 10 || gameID.charAt(0) != 'C') throw new InvalidGameIDException();
        this.rewardPayOut = rewardPayOut;
    }

    // Accessor method
    public boolean getRewardPayOut() {
        return rewardPayOut;
    }

    // Overriding the calculatePrice method from ArcadeGame
    @Override
    public int calculatePrice(boolean peak) {
        if (peak) {
            return getPricePerPlay();
        } else {
            if (rewardPayOut) {
                return (int) Math.floor(getPricePerPlay() * 0.8); // 20% discount
            } else {
                return (int) Math.floor( getPricePerPlay() * 0.5); // 50% discount
            }
        }
    }

    // Overrides the toString method
    @Override
    public String toString() {
        return "CabinetGame{" +
                "name=" + getName() +
                ", gameID=" + getGameID() +
                ", pricePerPlay=" + getPricePerPlay() +
                ", rewardPayOut=" + getRewardPayOut() +
                '}';
    }

    // Test method (the first character of gameID can be changed to test Exception)
    public static void main(String[] args) {
        try {
            CabinetGame game = new CabinetGame("Atari", "C987654321", 200, false);
            System.out.println(game); // Checking toString method
            System.out.println("Peak Price: " + game.calculatePrice(true));
            System.out.println("OffPeak Price: " + game.calculatePrice(false));
        } catch (InvalidGameIDException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
