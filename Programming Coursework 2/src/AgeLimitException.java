public class AgeLimitException extends Exception {

    public AgeLimitException() {
        super("Customer is too young");
    }
}
