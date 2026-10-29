public class Main {

    public static void main(String[] args) {

        Light livingRoom = new Light("Living room");
        Light kitchen = new Light("Kitchen");

        RemoteControl remote = new RemoteControl();
        remote.setCommand("button1", new TurnOnLightCommand(livingRoom));
        remote.setCommand("button2", new TurnOffLightCommand(livingRoom));
        remote.setCommand("button3", new TurnOnLightCommand(kitchen));

        System.out.println("=== Initial states ===");
        printStates(livingRoom, kitchen);

        System.out.println();
        System.out.println("=== Pressing button1, button2 and button3 ===");
        remote.pressButton("button1");
        remote.pressButton("button2");
        remote.pressButton("button3");
        printStates(livingRoom, kitchen);

        System.out.println();
        System.out.println("=== Reconfiguring button1 to turn off the kitchen ===");
        remote.setCommand("button1", new TurnOffLightCommand(kitchen));
        remote.pressButton("button1");
        printStates(livingRoom, kitchen);

        System.out.println();
        System.out.println("=== Pressing an unconfigured button ===");
        remote.pressButton("button4");
        remote.pressButton("BUTTON1");

        System.out.println();
        System.out.println("=== Turning on the kitchen (off) and undoing ===");
        remote.pressButton("button3");
        printStates(livingRoom, kitchen);
        remote.pressButton("button4");
        remote.setCommand("button1", new TurnOnLightCommand(livingRoom));
        remote.undoLast();
        printStates(livingRoom, kitchen);

        System.out.println();
        System.out.println("=== Undoing again without a new action ===");
        remote.undoLast();

        System.out.println();
        System.out.println("=== Turning on the kitchen (already on) and undoing ===");
        remote.pressButton("button3");
        remote.pressButton("button3");
        printStates(livingRoom, kitchen);
        remote.undoLast();
        printStates(livingRoom, kitchen);
    }

    private static void printStates(Light... lights) {
        for (Light light : lights) {
            System.out.println("State - " + light.getLocation() + ": " + (light.isOn() ? "on" : "off"));
        }
    }
}
