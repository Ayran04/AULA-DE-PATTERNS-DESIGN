import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        TreeTypeFactory factory = new TreeTypeFactory();
        List<Tree> trees = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            double oakScale = 0.5 + (i % 4) * 0.5;
            double pineScale = 1.0 + (i % 3) * 0.25;

            trees.add(new Tree(i, i * 2, oakScale,
                    factory.getTreeType("Oak", "Green", "oak-texture")));
            trees.add(new Tree(i, i * 3, pineScale,
                    factory.getTreeType("Pine", "Dark green", "pine-texture")));
        }

        for (Tree tree : trees) {
            tree.draw();
        }

        System.out.println();
        System.out.println("Trees created: " + trees.size());
        System.out.println("Tree types created: " + factory.getTypeCount());

        TreeType oak1 = factory.getTreeType("Oak", "Green", "oak-texture");
        TreeType oak2 = factory.getTreeType("Oak", "Green", "oak-texture");
        TreeType pine = factory.getTreeType("Pine", "Dark green", "pine-texture");

        System.out.println("oak1 == oak2: " + (oak1 == oak2));
        System.out.println("oak1 == pine: " + (oak1 == pine));
        System.out.println("Tree types after extra requests: " + factory.getTypeCount());
    }
}
