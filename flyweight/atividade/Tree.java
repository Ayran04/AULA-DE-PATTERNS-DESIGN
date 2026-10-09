public class Tree {

    private final int x;
    private final int y;
    private final double scale;
    private final TreeType type;

    public Tree(int x, int y, double scale, TreeType type) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.type = type;
    }

    public void draw() {
        type.draw(x, y, scale);
    }
}
