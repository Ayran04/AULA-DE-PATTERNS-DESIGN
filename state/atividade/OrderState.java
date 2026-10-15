public interface OrderState {
    void pay(Order order);

    void ship(Order order);

    void cancel(Order order);

    void deliver(Order order);

    String getName();
}
