public class Customer {

    private final String accountID;
    private final String customerName;
    private final int age;
    public enum discountLevel {
        NONE,
        STAFF,
        STUDENT
    }
    private int balance;
    private final discountLevel discount;

    // 2 constructors created to give balance a default value of 0
    public Customer(String accountID, String customerName, int age, discountLevel discount) throws InsufficientBalanceException, InvalidCustomerException {
        this(accountID, customerName, age, discount, 0);
    }

    public Customer(String accountID, String customerName, int age, discountLevel discount, int balance) throws InvalidCustomerException, InsufficientBalanceException{
        if (accountID.length() != 6) throw new InvalidCustomerException();
        if (balance < 0) throw new InsufficientBalanceException();

        this.accountID = accountID;
        this.customerName = customerName;
        this.age = age;
        this.discount = discount;
        this.balance = balance;
    }

    // Accessor methods
    public String getAccountID() {
        return accountID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getAge() {
        return age;
    }

    public int getBalance() {
        return balance;
    }

    public discountLevel getDiscount() {
        return discount;
    }

    public void addFunds(int amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    // Method that charges the user the cost of playing a game
    public int chargeAccount(ArcadeGame game, boolean peak) throws AgeLimitException, InsufficientBalanceException{
        int price = game.calculatePrice(peak);

        if (game instanceof ActiveGame activeGame) {
            if (getAge() < activeGame.getMinAge()) {
                throw new AgeLimitException();
            }
        }

        if (discount == discountLevel.STAFF) {
            price = (int) Math.floor(price * 0.9);
        } else if (discount == discountLevel.STUDENT) {
            price = (int) Math.floor(price * 0.95);

            if ((getBalance() - price) < -500) {
                throw new InsufficientBalanceException();
            }
        } else if (balance < price) {
            throw new InsufficientBalanceException();
        }

        balance -= price;
        return price;
    }

    // Overrides the toString method
    @Override
    public String toString() {
        return "Customer{" +
                "accountID=" + getAccountID() +
                ", customerName=" + getCustomerName() +
                ", age=" + getAge() +
                ", balance=" + getBalance() +
                ", discount=" + getDiscount() +
                '}';
    }

    // Test harness
    public static void main(String[] args) {
        try {
            Customer customer = new Customer("AB4879", "Manor", 15, discountLevel.STUDENT, 200);
            ActiveGame game = new ActiveGame("Air Hockey", "A738238956", 100, 14);
            System.out.println("Before playing: " + customer);
            System.out.println("Charged: " + customer.chargeAccount(game, false));
            System.out.println("After playing: " + customer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
