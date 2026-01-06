import java.util.ArrayList;

public class Arcade {
    private final String arcadeName;
    private int revenue;
    private final ArrayList<ArcadeGame> gameCollection;
    private final ArrayList<Customer> customerRegistry;

    // Constructor
    public Arcade(String arcadeName) {
        this.arcadeName = arcadeName;
        this.revenue = 0;
        this.gameCollection = new ArrayList<>();
        this.customerRegistry = new ArrayList<>();
    }

    // Accessor methods
    public String getArcadeName() {
        return arcadeName;
    }

    public int getRevenue() {
        return revenue;
    }

    public ArrayList<ArcadeGame> getGameCollection() {
        return gameCollection;
    }

    public ArrayList<Customer> getCustomerRegistry() {
        return customerRegistry;
    }

    public void addCustomer(Customer C) {
        customerRegistry.add(C);
    }

    public void addGame(ArcadeGame G) {
        gameCollection.add(G);
    }

    public Customer getCustomer(String customerID) throws InvalidCustomerException {
        for (int i = 0; i < getCustomerRegistry().size(); i++) {
            Customer C = getCustomerRegistry().get(i);
            if (C.getAccountID().equals(customerID)) {
                return C;
            }
        }
        throw new InvalidCustomerException();
    }

    public ArcadeGame getArcadeGame(String gameID) throws InvalidGameIDException {
        for (int j = 0; j < getGameCollection().size(); j++) {
            ArcadeGame G = getGameCollection().get(j);
            if (G.getGameID().equals(gameID)) {
                return G;
            }
        }
        throw new InvalidGameIDException();
    }

    // Overrides the toString method
    @Override
    public String toString() {
        return "Arcade{" +
                "arcadeName='" + getArcadeName() + '\'' +
                ", revenue=" + getRevenue() +
                ", gameCollection=" + getGameCollection() +
                ", customerRegistry=" + getCustomerRegistry() +
                '}';
    }

    // Method that updates the Arcade's revenue after charging customer
    public boolean processTransaction(String customerID, String gameID, boolean peak)
            throws InvalidCustomerException, InvalidGameIDException, InsufficientBalanceException, AgeLimitException {
        Customer C = getCustomer(customerID);
        ArcadeGame G = getArcadeGame(gameID);
        int charged = C.chargeAccount(G, peak);
        revenue += charged;
        return true;
    }

    // Method to find the richest customer registered
    public Customer findRichestCustomer() {
        if (customerRegistry.isEmpty()) {
            return null;
        }
        Customer currentRichest = customerRegistry.getFirst();
        for (int i = 1; i < customerRegistry.size(); i++) {
            Customer C = customerRegistry.get(i);
            if (C.getBalance() > currentRichest.getBalance()) {
                currentRichest = C;
            }
        }
        return currentRichest;
    }

    // Method to find the median price of all games registered
    public int getMedianGamePrice() {
        if (gameCollection.isEmpty()) {
            return 0;
        }
        ArrayList<Integer> allGamePrices = new ArrayList<>();
        for (int i = 0; i < gameCollection.size(); i++) {
            allGamePrices.add(gameCollection.get(i).getPricePerPlay());
        }
        // Bubble sort
        for (int i = 0; i < allGamePrices.size() - 1; i++) {
            for (int j = 0; j < allGamePrices.size() - i - 1; j++) {
                if (allGamePrices.get(j) > allGamePrices.get(j + 1)) {
                    int temp = allGamePrices.get(j);
                    allGamePrices.set(j, allGamePrices.get(j + 1));
                    allGamePrices.set(j + 1, temp);
                }
            }
        }
        if (allGamePrices.size() % 2 == 0) {
            return (allGamePrices.get(allGamePrices.size() / 2) + allGamePrices.get(allGamePrices.size() / 2 - 1)) / 2;
        } else {
            return allGamePrices.get(allGamePrices.size() / 2);
        }
    }

    // Method to count and return the number of each type of game
    public int[] countArcadeGames() {
        int cabinet = 0;
        int active = 0;
        int vr = 0;
        for (int i = 0; i < gameCollection.size(); i++) {
            ArcadeGame G = gameCollection.get(i);
            if (G instanceof CabinetGame) {
                cabinet++;
            } else if (G instanceof VirtualRealityGame) {
                vr++;
            } else if (G instanceof ActiveGame) {
                active++;
            }
        }
        return new int[]{cabinet, active, vr};
    }

    // Disclaimer
    public static void printCorporateJargon() {
        System.out.println("GamesCo does not take responsibility for any accidents or fits of rage that occur on the premises");
    }

    // Test method
    public static void main(String[] args) {
        try {
            Arcade A = new Arcade("HD Machine");
            Customer C = new Customer("A98765", "Oscar", 15, Customer.discountLevel.NONE, 800);
            Customer C1 = new Customer("A98764", "Solomon", 20, Customer.discountLevel.STUDENT, 1500);
            ArcadeGame G = new CabinetGame("Ping-Ball", "C987654321", 200, true);
            ArcadeGame G1 = new ActiveGame("Tin Shooters", "A887554421", 300, 15);
            ArcadeGame G2 = new VirtualRealityGame("VR Shooting", "AV87654321", 400, 17, VirtualRealityGame.EquipmentType.VR_HEADSET);

            A.addCustomer(C);
            A.addCustomer(C1);
            A.addGame(G);
            A.addGame(G1);
            A.addGame(G2);

            A.processTransaction("A98765", "C987654321", false);
            A.processTransaction("A98764", "A887554421", true);

            System.out.println(A.countArcadeGames()[0] + " Cabinet Games: " +
                    A.countArcadeGames()[1] +  " Active Games: " +
                    A.countArcadeGames()[2] +  " VR Games: ");
            System.out.println(A.findRichestCustomer().getCustomerName() + " is the  Richest Customer");
            System.out.println(A.getMedianGamePrice() + " is the Median game price");

            A.printCorporateJargon();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
