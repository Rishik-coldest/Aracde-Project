// ActiveGame inherits from ArcadeGame
public class ActiveGame extends ArcadeGame {

    private final int minAge;

    // Constructor
    public ActiveGame(String name, String gameID, int pricePerPlay, int minAge) throws InvalidGameIDException {

        super(name, gameID, pricePerPlay);
        if (gameID.length() != 10 || gameID.charAt(0) != 'A') throw new InvalidGameIDException();
        this.minAge = minAge;
    }

    // Accessor method
    public int getMinAge() {
        return minAge;
    }

    // Overrides the calculatePrice method from ArcadeGame
    @Override
    public int calculatePrice(boolean peak) {
        if (peak) {
            return getPricePerPlay();
        } else {
            return (int) Math.floor(getPricePerPlay() * 0.8);
        }
    }

    // Overrides the toString method
    @Override
    public String toString() {
        return "ActiveGame{" +
                "name=" + getName() +
                ", gameID=" + getGameID() +
                ", pricePerPlay=" + getPricePerPlay() +
                ", minAge=" + getMinAge() +
                '}';
    }

    // Test harness
    public static void main(String[] args) {
        try {
            ActiveGame game1 = new ActiveGame("Foosball", "A579678967", 200, 10);
            System.out.println(game1); // Checking toString method
            System.out.println("Peak Price: " + game1.calculatePrice(true));
            System.out.println("OffPeak Price: " + game1.calculatePrice(false));
        } catch (InvalidGameIDException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
