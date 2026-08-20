public abstract class Logistics {

    // Factory Method
    public abstract Transport createTransport();

    public void planDelivery() {

        System.out.println("Preparing delivery...");

        Transport transport = createTransport();

        transport.deliver();

        System.out.println("Delivery completed.");
    }
}