import java.util.Objects;

public class Order {

    private OrderState state = new PendingState();

    public void pay() {
        state.pay(this);
    }

    public void ship() {
        state.ship(this);
    }

    public void cancel() {
        state.cancel(this);
    }

    public void deliver() {
        state.deliver(this);
    }

    public void changeState(OrderState state) {
        this.state = Objects.requireNonNull(state, "Order state must not be null");
    }

    public String getStatus() {
        return state.getName();
    }
}
