import java.util.Objects;

public class TextEditor {

    private String text = "";
    private int cursorPosition;

    public String getText() {
        return text;
    }

    public int getCursorPosition() {
        return cursorPosition;
    }

    public void setText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text is required");
        }
        this.text = text;
        cursorPosition = text.length();
    }

    public void setCursorPosition(int cursorPosition) {
        if (cursorPosition < 0 || cursorPosition > text.length()) {
            throw new IllegalArgumentException("Invalid cursor position");
        }
        this.cursorPosition = cursorPosition;
    }

    public EditorMemento save() {
        return new EditorMemento(text, cursorPosition);
    }

    public void restore(EditorMemento memento) {
        Objects.requireNonNull(memento, "memento must not be null");
        this.text = memento.text;
        this.cursorPosition = memento.cursorPosition;
    }

    public static final class EditorMemento {

        private final String text;
        private final int cursorPosition;

        private EditorMemento(String text, int cursorPosition) {
            this.text = text;
            this.cursorPosition = cursorPosition;
        }
    }
}
