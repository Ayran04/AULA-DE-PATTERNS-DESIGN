public class ProtectedImageProxy implements Image {

    private final Image image;
    private final boolean hasAccess;

    public ProtectedImageProxy(Image image, boolean hasAccess) {
        this.image = image;
        this.hasAccess = hasAccess;
    }

    @Override
    public void display() {
        if (!hasAccess) {
            System.out.println("Access denied: you do not have permission to view this image");
            return;
        }
        image.display();
    }
}
