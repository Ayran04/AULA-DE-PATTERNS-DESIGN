public class StandardShipping implements ShippingStrategy {

    @Override
    public double calculate(double weight) {
        return 10.0 + weight * 2.0;
    }
}
