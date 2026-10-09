import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Image> gallery = new ArrayList<>();

        gallery.add(new ImageProxy("forest.jpg"));
        gallery.add(new ImageProxy("beach.jpg"));
        gallery.add(new ImageProxy("mountain.jpg"));

        System.out.println("Gallery created");

        System.out.println("=== First image (twice) ===");
        show(gallery.get(0));
        show(gallery.get(0));

        System.out.println("=== Second image ===");
        show(gallery.get(1));

        System.out.println("=== Third image is never displayed, so it is never loaded ===");

        System.out.println();
        System.out.println("=== Protected images ===");
        Image denied = new ProtectedImageProxy(new ImageProxy("secret.jpg"), false);
        Image allowed = new ProtectedImageProxy(new ImageProxy("vip.jpg"), true);

        System.out.println("User without permission:");
        show(denied);

        System.out.println("User with permission:");
        show(allowed);
        show(allowed);
    }

    private static void show(Image image) {
        image.display();
    }
}
