public class DocumentFile implements FileSystemComponent {

    private String name;
    private long size;

    public DocumentFile(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "- " + name + " (" + size + " KB)");
    }
}
