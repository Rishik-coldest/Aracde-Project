import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Main class
public class Simulation {

    // Method to initialise the arcade by reading games and customers files
    public static Arcade initialiseArcade(String arcadeName, File arcadeGamesFile, File customerFile)
            throws FileNotFoundException, InsufficientBalanceException, InvalidCustomerException, InvalidGameIDException {
        Arcade arcade = new Arcade(arcadeName);

        Scanner customerScan = new Scanner(customerFile);
        customerScan.useDelimiter("\n");
        Scanner lineScan;

        Scanner arcadeGamesScan = new Scanner(arcadeGamesFile);
        arcadeGamesScan.useDelimiter("\n");
        Scanner lineScan2;

        while (customerScan.hasNextLine()) {
            lineScan = new Scanner(customerScan.next());
            lineScan.useDelimiter("#");

            String CustomerID = lineScan.next();
            String CustomerName = lineScan.next();
            int balance = lineScan.nextInt();
            int CustomerAge = lineScan.nextInt();
            String customerDiscount;
            if (lineScan.hasNext()) {
                customerDiscount = lineScan.next();
            } else  {
                customerDiscount = "NONE";
            }

            Customer.discountLevel discount = Customer.discountLevel.valueOf(customerDiscount);
            Customer customer = new Customer(CustomerID, CustomerName, CustomerAge, discount, balance);
            arcade.addCustomer(customer);
        }

        while (arcadeGamesScan.hasNextLine()) {
            lineScan2 = new Scanner(arcadeGamesScan.next());
            lineScan2.useDelimiter("@");

            String GameID = lineScan2.next();
            String GameName = lineScan2.next();
            String GameType = lineScan2.next();
            int GamePrice = lineScan2.nextInt();

            if (GameType.equals("cabinet")) {
                boolean rewardPayOut = lineScan2.nextBoolean();
                CabinetGame game = new CabinetGame(GameName, GameID, GamePrice, rewardPayOut);
                arcade.addGame(game);
            } else if (GameType.equals("active")) {
                int minAge = lineScan2.nextInt();
                ActiveGame game = new ActiveGame(GameName, GameID, GamePrice, minAge);
                arcade.addGame(game);
            } else if (GameType.equals("virtualReality")) {
                int minAge = lineScan2.nextInt();
                String gameEquipment = lineScan2.next();
                VirtualRealityGame.EquipmentType equipment = VirtualRealityGame.EquipmentType.valueOf(gameEquipment);
                VirtualRealityGame game = new VirtualRealityGame(GameName, GameID, GamePrice, minAge, equipment);
                arcade.addGame(game);
            }
        }
        return arcade;
    }

    // Simulate running the arcade by processing transactions from file
    public static void simulateFun(Arcade arcade, File transactionFile)
            throws FileNotFoundException, AgeLimitException, InsufficientBalanceException, InvalidCustomerException, InvalidGameIDException {
        Scanner transactionScan = new Scanner(transactionFile);
        transactionScan.useDelimiter("\n");
        Scanner lineScan3;

        while (transactionScan.hasNextLine()) {
            lineScan3 = new Scanner(transactionScan.next());
            lineScan3.useDelimiter(",");

            String performAction =  lineScan3.next();

            if (performAction.equals("PLAY")) {
                String customerID = lineScan3.next();
                String gameID = lineScan3.next();
                String peakReader = lineScan3.next();
                boolean peak;
                if (peakReader.equals("OFF_PEAK")) {
                    peak = false;
                } else {
                    peak = true;
            }
                arcade.processTransaction(customerID, gameID, peak);
                System.out.println(customerID + " played " +  gameID + " during " + peakReader);
            }
            else if (performAction.equals("NEW_CUSTOMER")) {
                String customerID = lineScan3.next();
                String customerName = lineScan3.next();
                String customerDiscount = lineScan3.next();
                Customer.discountLevel discount = Customer.discountLevel.valueOf(customerDiscount);
                int balance = lineScan3.nextInt();
                int age = lineScan3.nextInt();
                Customer customer = new Customer(customerID, customerName, age, discount, balance);
                arcade.addCustomer(customer);
                System.out.println("Added customer " + customerName);
            }
            else if (performAction.equals("ADD_FUNDS")) {
                String customerID = lineScan3.next();
                int amount = lineScan3.nextInt();
                Customer customer = arcade.getCustomer(customerID);
                customer.addFunds(amount);
                System.out.println("Added " + amount + " pence to customer " + customerID);
            }
        }
        System.out.println(arcade.findRichestCustomer().getCustomerName() + " is the  Richest Customer");

        System.out.println(arcade.getMedianGamePrice() + " is the Median game price");

        System.out.println(arcade.countArcadeGames()[0] + " Cabinet Games: " +
                arcade.countArcadeGames()[1] +  " Active Games: " +
                arcade.countArcadeGames()[2] +  " VR Games: ");

        System.out.println("Total revenue: " + arcade.getRevenue());
    }

    // Main method to run entire simulation
    public static void main(String[] args) throws AgeLimitException, FileNotFoundException, InsufficientBalanceException, InvalidCustomerException, InvalidGameIDException {
        File customerFile = new File("customers.txt");
        File arcadeGamesFile = new File("games.txt");
        File transactionFile = new File("transactions.txt");

        Arcade arcade = initialiseArcade("Fun Box", arcadeGamesFile, customerFile);
        simulateFun(arcade, transactionFile);
    }
}