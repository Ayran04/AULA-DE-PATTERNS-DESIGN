public class CaramelDecorator extends BeverageDecorator {

    public CaramelDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", caramel";
    }

    @Override
    public double getCost() {
        return beverage.getCost() + 1.00;
    }
}
