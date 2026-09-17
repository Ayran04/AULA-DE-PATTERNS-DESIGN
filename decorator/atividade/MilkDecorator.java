public class MilkDecorator extends BeverageDecorator {

    public MilkDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", milk";
    }

    @Override
    public double getCost() {
        return beverage.getCost() + 1.50;
    }
}
