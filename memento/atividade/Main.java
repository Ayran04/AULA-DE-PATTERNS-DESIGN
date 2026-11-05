public class Main {

    public static void main(String[] args) {

        TextEditor editor = new TextEditor();
        EditorHistory history = new EditorHistory(editor);

        System.out.println("=== Editing ===");
        editor.setText("Hello");
        editor.setCursorPosition(2);
        print(editor);

        history.backup();
        editor.setText("Hello, world!");
        editor.setCursorPosition(5);
        print(editor);

        history.backup();
        editor.setText("Goodbye");
        print(editor);

        System.out.println();
        System.out.println("=== Undo ===");
        history.undo();
        print(editor);
        history.undo();
        print(editor);
        history.undo();
        print(editor);

        System.out.println();
        System.out.println("=== Undo and redo (additional challenge) ===");
        TextEditor draft = new TextEditor();
        EditorHistory draftHistory = new EditorHistory(draft);

        draft.setText("Draft");
        draft.setCursorPosition(1);
        print(draft);

        draftHistory.backup();
        draft.setText("Draft v2");
        draft.setCursorPosition(5);
        print(draft);

        draftHistory.undo();
        print(draft);
        draftHistory.redo();
        print(draft);

        System.out.println();
        System.out.println("=== New edit after undo discards redo ===");
        draftHistory.undo();
        print(draft);

        draftHistory.backup();
        draft.setText("Alternative");
        print(draft);

        draftHistory.redo();
        print(draft);

        draftHistory.undo();
        print(draft);
    }

    private static void print(TextEditor editor) {
        System.out.println("Text: " + editor.getText() + " | Cursor: " + editor.getCursorPosition());
    }
}
