public class Main {

    public static void main(String[] args) {

        Beverage plainCoffee = new Coffee();
        print(plainCoffee);

        Beverage coffeeWithMilkAndChocolate = new ChocolateDecorator(new MilkDecorator(new Coffee()));
        print(coffeeWithMilkAndChocolate);

        Beverage teaWithMilk = new MilkDecorator(new Tea());
        print(teaWithMilk);

        Beverage doubleChocolateCoffee = new ChocolateDecorator(new ChocolateDecorator(new Coffee()));
        print(doubleChocolateCoffee);

        Beverage fullyLoadedTea = new CaramelDecorator(new WhippedCreamDecorator(new MilkDecorator(new Tea())));
        print(fullyLoadedTea);
    }

    private static void print(Beverage beverage) {
        System.out.println(beverage.getDescription() + " -> R$ " + beverage.getCost());
    }
}
