public class ExpressShipping implements ShippingStrategy {

    @Override
    public double calculate(double weight) {
        return 20.0 + weight * 5.0;
    }
}
