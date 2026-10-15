public class EconomyShipping implements ShippingStrategy {

    @Override
    public double calculate(double weight) {
        return 5.0 + weight * 1.5;
    }
}
