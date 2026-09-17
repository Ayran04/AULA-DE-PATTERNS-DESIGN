public class Main {

    public static void main(String[] args) {

        NotificationService emailService = new EmailNotificationService();
        emailService.sendNotification("Your order has been shipped!");

        NotificationService smsService = new SmsNotificationService();
        smsService.sendNotification("Your order has been shipped!");

        NotificationService pushService = new PushNotificationService();
        pushService.sendNotification("Your order has been shipped!");

        NotificationService whatsAppService = new WhatsAppNotificationService();
        whatsAppService.sendNotification("Your order has been shipped!");
    }
}
