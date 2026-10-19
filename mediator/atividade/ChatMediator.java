public interface ChatMediator {
    void register(ChatUser user);

    void send(String message, ChatUser sender);

    void sendPrivate(String message, ChatUser sender, String recipientName);
}
