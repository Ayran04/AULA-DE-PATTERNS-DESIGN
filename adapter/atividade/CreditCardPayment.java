public class CreditCardPayment implements PaymentProcessor {

    @Override
    public void pay(double amount) {
        System.out.println("Payment approved: $" + amount);
    }
}
