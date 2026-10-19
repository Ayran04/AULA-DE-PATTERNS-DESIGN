import java.util.Objects;

public class ChatUser {

    private final String name;
    private final ChatMediator mediator;

    public ChatUser(String name, ChatMediator mediator) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("User name must not be empty");
        }
        this.name = name;
        this.mediator = Objects.requireNonNull(mediator, "Chat mediator must not be null");
    }

    public String getName() {
        return name;
    }

    public boolean isAssociatedWith(ChatMediator mediator) {
        return this.mediator == mediator;
    }

    public void send(String message) {
        mediator.send(message, this);
    }

    public void sendPrivate(String recipientName, String message) {
        mediator.sendPrivate(message, this, recipientName);
    }

    public void receive(String senderName, String message) {
        System.out.println(name + " received from " + senderName + ": " + message);
    }
}
