import java.util.ArrayList;
import java.util.List;

public class Folder implements FileSystemComponent {

    private String name;
    private List<FileSystemComponent> children = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public void add(FileSystemComponent component) {
        children.add(component);
    }

    public void remove(FileSystemComponent component) {
        children.remove(component);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getSize() {
        long total = 0;

        for (FileSystemComponent child : children) {
            total += child.getSize();
        }

        return total;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "+ " + name + " (" + getSize() + " KB)");

        for (FileSystemComponent child : children) {
            child.display(indent + "  ");
        }
    }
}
