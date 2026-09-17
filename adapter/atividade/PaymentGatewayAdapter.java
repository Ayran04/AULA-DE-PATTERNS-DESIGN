public class PaymentGatewayAdapter implements PaymentProcessor {

    private final ExternalPaymentGateway gateway;
    private final String currency;

    public PaymentGatewayAdapter(String currency) {
        this(new ExternalPaymentGateway(), currency);
    }

    public PaymentGatewayAdapter(ExternalPaymentGateway gateway, String currency) {
        this.gateway = gateway;
        this.currency = currency;
    }

    @Override
    public void pay(double amount) {
        gateway.makePayment(currency, amount);
    }
}
