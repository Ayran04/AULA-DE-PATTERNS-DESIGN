public class Main {

    public static void main(String[] args) {

        Notification emailAlert = new AlertNotification(new EmailSender());
        emailAlert.notify("Your account was accessed from a new device.");

        Notification smsReminder = new ReminderNotification(new SmsSender());
        smsReminder.notify("Your appointment starts in one hour.");

        Notification smsAlert = new AlertNotification(new SmsSender());
        smsAlert.notify("Suspicious login attempt detected.");

        Notification emailReminder = new ReminderNotification(new EmailSender());
        emailReminder.notify("Your subscription renews tomorrow.");

        Notification whatsAppWarning = new WarningNotification(new WhatsAppSender());
        whatsAppWarning.notify("Your storage is almost full.");
    }
}
