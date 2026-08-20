public class Application {

    private final Button button;
    private final TextBox textBox;

    public Application(GUIFactory factory) {
        this.button = factory.createButton();
        this.textBox = factory.createTextBox();
    }

    public void render() {
        button.render();
        textBox.render();
    }
}