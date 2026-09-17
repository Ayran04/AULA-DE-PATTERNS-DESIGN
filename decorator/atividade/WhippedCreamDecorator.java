public class WhippedCreamDecorator extends BeverageDecorator {

    public WhippedCreamDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", whipped cream";
    }

    @Override
    public double getCost() {
        return beverage.getCost() + 2.50;
    }
}
