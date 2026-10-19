import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatRoom implements ChatMediator {

    private final List<ChatUser> users = new ArrayList<>();

    @Override
    public void register(ChatUser user) {
        Objects.requireNonNull(user, "User must not be null");
        if (!user.isAssociatedWith(this)) {
            System.out.println("Registration refused: " + user.getName() + " belongs to another room");
            return;
        }
        if (users.contains(user)) {
            System.out.println("Registration ignored: " + user.getName() + " is already registered");
            return;
        }
        if (findByName(user.getName()) != null) {
            System.out.println("Registration refused: name " + user.getName() + " is already in use");
            return;
        }
        users.add(user);
        System.out.println(user.getName() + " joined the room");
    }

    @Override
    public void send(String message, ChatUser sender) {
        if (!isRegistered(sender)) {
            System.out.println("Message refused: " + sender.getName() + " is not registered");
            return;
        }
        for (ChatUser user : users) {
            if (user != sender) {
                user.receive(sender.getName(), message);
            }
        }
    }

    @Override
    public void sendPrivate(String message, ChatUser sender, String recipientName) {
        if (!isRegistered(sender)) {
            System.out.println("Private message refused: " + sender.getName() + " is not registered");
            return;
        }
        ChatUser recipient = findByName(recipientName);
        if (recipient == null) {
            System.out.println("Private message refused: " + recipientName + " is not in the room");
            return;
        }
        if (recipient == sender) {
            System.out.println("Private message refused: " + sender.getName() + " cannot send to themselves");
            return;
        }
        recipient.receive(sender.getName() + " (private)", message);
    }

    private boolean isRegistered(ChatUser user) {
        return users.contains(user);
    }

    private ChatUser findByName(String name) {
        for (ChatUser user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }
}
