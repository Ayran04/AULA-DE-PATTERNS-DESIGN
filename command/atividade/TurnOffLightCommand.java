import java.util.Objects;

public class TurnOffLightCommand implements Command {

    private final Light light;
    private boolean previousState;

    public TurnOffLightCommand(Light light) {
        this.light = Objects.requireNonNull(light, "Light must not be null");
    }

    @Override
    public void execute() {
        previousState = light.isOn();
        light.turnOff();
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
