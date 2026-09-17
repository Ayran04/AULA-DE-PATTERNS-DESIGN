public class WhatsAppNotificationService extends NotificationService {

    @Override
    protected Notification createNotification() {
        return new WhatsAppNotification();
    }
}
