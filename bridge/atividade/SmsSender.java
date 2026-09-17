public class SmsSender implements NotificationSender {

    @Override
    public void send(String message) {
        System.out.println("Sending by SMS: " + message);
    }
}
