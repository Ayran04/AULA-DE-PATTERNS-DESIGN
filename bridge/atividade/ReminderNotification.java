public class ReminderNotification extends Notification {

    public ReminderNotification(NotificationSender sender) {
        super(sender);
    }

    @Override
    public void notify(String message) {
        sender.send("[Reminder] " + message);
    }
}
