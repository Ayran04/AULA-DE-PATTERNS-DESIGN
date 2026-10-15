import java.util.Objects;

public class ShippingCalculator {

    private ShippingStrategy strategy;

    public ShippingCalculator(ShippingStrategy strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(ShippingStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "Shipping strategy must not be null");
    }

    public double calculate(double weight) {
        return strategy.calculate(weight);
    }
}
