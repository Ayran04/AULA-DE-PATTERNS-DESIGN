public abstract class Notification {

    protected NotificationSender sender;

    protected Notification(NotificationSender sender) {
        this.sender = sender;
    }

    public abstract void notify(String message);
}
