public class ShippedState implements OrderState {

    @Override
    public void pay(Order order) {
        System.out.println("Payment not allowed: order is already shipped");
    }

    @Override
    public void ship(Order order) {
        System.out.println("Shipping not allowed: order is already shipped");
    }

    @Override
    public void cancel(Order order) {
        System.out.println("Cancellation not allowed: order is already shipped");
    }

    @Override
    public void deliver(Order order) {
        System.out.println("Order delivered");
        order.changeState(new DeliveredState());
    }

    @Override
    public String getName() {
        return "SHIPPED";
    }
}
