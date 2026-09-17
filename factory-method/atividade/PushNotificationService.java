public class PushNotificationService extends NotificationService {

    @Override
    protected Notification createNotification() {
        return new PushNotification();
    }
}
