public class OrderService {

    private final PaymentProcessor payment;

    public OrderService(PaymentProcessor payment) {
        this.payment = payment;
    }

    public void checkout(double amount) {
        payment.pay(amount);
    }
}
