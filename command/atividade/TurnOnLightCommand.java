import java.util.Objects;

public class TurnOnLightCommand implements Command {

    private final Light light;
    private boolean previousState;

    public TurnOnLightCommand(Light light) {
        this.light = Objects.requireNonNull(light, "Light must not be null");
    }

    @Override
    public void execute() {
        previousState = light.isOn();
        light.turnOn();
    }

    @Override
    public void undo() {
        if (previousState) {
            light.turnOn();
        } else {
            light.turnOff();
        }
    }
}
