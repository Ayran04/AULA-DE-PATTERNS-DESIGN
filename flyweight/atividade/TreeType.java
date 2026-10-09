public final class TreeType {

    private final String name;
    private final String color;
    private final byte[] texture;

    public TreeType(String name, String color, byte[] texture) {
        this.name = name;
        this.color = color;
        this.texture = texture.clone();
    }

    public void draw(int x, int y, double scale) {
        System.out.println("Drawing " + name + " (" + color + ") at ("
                + x + ", " + y + "), scale: " + scale
                + ", texture: " + texture.length + " bytes");
    }
}
