public class Main {

    public static void main(String[] args) {

        ChatRoom room = new ChatRoom();
        ChatUser ana = new ChatUser("Ana", room);
        ChatUser bruno = new ChatUser("Bruno", room);
        ChatUser carla = new ChatUser("Carla", room);
        ChatUser diego = new ChatUser("Diego", room);

        System.out.println("=== Registering Ana, Bruno and Carla ===");
        room.register(ana);
        room.register(bruno);
        room.register(carla);
        room.register(bruno);

        System.out.println();
        System.out.println("=== Ana sends to the room ===");
        ana.send("Hello, everyone!");

        System.out.println();
        System.out.println("=== Diego sends before registering ===");
        diego.send("Am I in?");

        System.out.println();
        System.out.println("=== Diego joins and Bruno sends to the room ===");
        room.register(diego);
        bruno.send("Hello, Ana!");

        System.out.println();
        System.out.println("=== Private messages ===");
        ana.sendPrivate("Carla", "Hi Carla, just for you.");
        ana.sendPrivate("Eduardo", "Are you there?");
        ana.sendPrivate("Ana", "Talking to myself.");
        ana.sendPrivate("carla", "Wrong case.");

        System.out.println();
        System.out.println("=== Another user named Ana ===");
        ChatUser otherAna = new ChatUser("Ana", room);
        room.register(otherAna);
        bruno.sendPrivate("Ana", "Which Ana gets this?");
        otherAna.send("Can anyone hear me?");
    }
}
