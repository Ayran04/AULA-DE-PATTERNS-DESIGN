public class ExternalPaymentGateway {

    public void makePayment(String currency, double value) {
        System.out.println("External payment approved: " + currency + " " + value);
    }
}
