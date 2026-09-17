public class Main {

    public static void main(String[] args) {

        OrderService localOrder = new OrderService(new CreditCardPayment());
        localOrder.checkout(150.00);

        OrderService brlOrder = new OrderService(new PaymentGatewayAdapter("BRL"));
        brlOrder.checkout(200.00);

        OrderService usdOrder = new OrderService(new PaymentGatewayAdapter("USD"));
        usdOrder.checkout(75.50);

        OrderService eurOrder = new OrderService(new PaymentGatewayAdapter("EUR"));
        eurOrder.checkout(60.00);
    }
}
