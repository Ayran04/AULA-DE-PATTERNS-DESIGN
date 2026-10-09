import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TreeTypeFactory {

    private static final int TEXTURE_SIZE = 100_000;

    private final Map<Key, TreeType> cache = new HashMap<>();

    public TreeType getTreeType(String name, String color, String textureId) {
        Key key = new Key(name, color, textureId);
        TreeType type = cache.get(key);
        if (type == null) {
            type = new TreeType(name, color, loadTexture(textureId));
            cache.put(key, type);
        }
        return type;
    }

    public int getTypeCount() {
        return cache.size();
    }

    private byte[] loadTexture(String textureId) {
        System.out.println("Loading texture: " + textureId);
        return new byte[TEXTURE_SIZE];
    }

    private static final class Key {

        private final String name;
        private final String color;
        private final String textureId;

        Key(String name, String color, String textureId) {
            this.name = Objects.requireNonNull(name);
            this.color = Objects.requireNonNull(color);
            this.textureId = Objects.requireNonNull(textureId);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Key)) {
                return false;
            }
            Key other = (Key) o;
            return name.equals(other.name)
                    && color.equals(other.color)
                    && textureId.equals(other.textureId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, color, textureId);
        }
    }
}
