public class Main {

    public static void main(String[] args) {

        System.out.println("=== Order 1: full flow (pay, ship, deliver) ===");
        Order order1 = new Order();
        showStatus(order1);
        order1.ship();
        showStatus(order1);
        order1.pay();
        showStatus(order1);
        order1.ship();
        showStatus(order1);
        order1.cancel();
        showStatus(order1);
        order1.deliver();
        showStatus(order1);
        order1.cancel();
        showStatus(order1);

        System.out.println();
        System.out.println("=== Order 2: cancel a pending order ===");
        Order order2 = new Order();
        showStatus(order2);
        order2.cancel();
        showStatus(order2);
        order2.pay();
        showStatus(order2);

        System.out.println();
        System.out.println("=== Order 3: cancel a paid order ===");
        Order order3 = new Order();
        showStatus(order3);
        order3.pay();
        showStatus(order3);
        order3.deliver();
        showStatus(order3);
        order3.cancel();
        showStatus(order3);
    }

    private static void showStatus(Order order) {
        System.out.println("Status: " + order.getStatus());
    }
}
