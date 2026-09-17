public class SmsNotificationService extends NotificationService {

    @Override
    protected Notification createNotification() {
        return new SmsNotification();
    }
}
