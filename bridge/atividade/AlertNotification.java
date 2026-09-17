public class AlertNotification extends Notification {

    public AlertNotification(NotificationSender sender) {
        super(sender);
    }

    @Override
    public void notify(String message) {
        sender.send("[Alert] " + message);
    }
}
