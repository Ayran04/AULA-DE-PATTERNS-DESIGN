public abstract class BeverageDecorator implements Beverage {

    protected Beverage beverage;

    protected BeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }
}
