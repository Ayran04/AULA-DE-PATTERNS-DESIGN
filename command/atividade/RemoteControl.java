import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RemoteControl {

    private final Map<String, Command> commands = new HashMap<>();
    private Command lastCommand;

    public void setCommand(String button, Command command) {
        if (button == null || button.isBlank()) {
            throw new IllegalArgumentException("Button name must not be null or empty");
        }
        Objects.requireNonNull(command, "Command must not be null");
        commands.put(button, command);
    }

    public void pressButton(String button) {
        Command command = commands.get(button);
        if (command == null) {
            System.out.println("Warning: button " + button + " is not configured");
            return;
        }
        command.execute();
        lastCommand = command;
    }

    public void undoLast() {
        if (lastCommand == null) {
            System.out.println("Warning: there is no action to undo");
            return;
        }
        System.out.println("Undoing last action");
        lastCommand.undo();
        lastCommand = null;
    }
}
