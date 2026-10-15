import java.util.Locale;

public class Main {

    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    public static void main(String[] args) {

        double weight = 3.0;

        ShippingCalculator calculator = new ShippingCalculator(new StandardShipping());
        print("Standard", calculator.calculate(weight));

        calculator.setStrategy(new ExpressShipping());
        print("Express", calculator.calculate(weight));

        calculator.setStrategy(new StorePickup());
        print("Pickup", calculator.calculate(weight));

        calculator.setStrategy(new EconomyShipping());
        print("Economy", calculator.calculate(weight));
    }

    private static void print(String label, double value) {
        System.out.println(label + ": R$ " + String.format(PT_BR, "%.2f", value));
    }
}
