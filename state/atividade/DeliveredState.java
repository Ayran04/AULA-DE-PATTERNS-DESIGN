public class DeliveredState implements OrderState {

    @Override
    public void pay(Order order) {
        System.out.println("Payment not allowed: order is already delivered");
    }

    @Override
    public void ship(Order order) {
        System.out.println("Shipping not allowed: order is already delivered");
    }

    @Override
    public void cancel(Order order) {
        System.out.println("Cancellation not allowed: order is already delivered");
    }

    @Override
    public void deliver(Order order) {
        System.out.println("Delivery not allowed: order is already delivered");
    }

    @Override
    public String getName() {
        return "DELIVERED";
    }
}
