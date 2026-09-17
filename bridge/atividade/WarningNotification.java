public class WarningNotification extends Notification {

    public WarningNotification(NotificationSender sender) {
        super(sender);
    }

    @Override
    public void notify(String message) {
        sender.send("[Warning] " + message);
    }
}
