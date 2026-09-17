public class WhatsAppSender implements NotificationSender {

    @Override
    public void send(String message) {
        System.out.println("Sending by WhatsApp: " + message);
    }
}
