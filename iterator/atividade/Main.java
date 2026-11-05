import java.util.NoSuchElementException;

public class Main {

    public static void main(String[] args) {

        Playlist playlist = new Playlist();
        playlist.addSong(new Song("First song"));
        playlist.addSong(new Song("Second song"));
        playlist.addSong(new Song("Third song"));

        SongCollection collection = playlist;

        System.out.println("=== Full traversal ===");
        printSongs(collection.createIterator());

        System.out.println();
        System.out.println("=== Independent iterators ===");
        SongIterator first = collection.createIterator();
        SongIterator second = collection.createIterator();
        System.out.println("First iterator, 1st next(): " + first.next().getTitle());
        System.out.println("First iterator, 2nd next(): " + first.next().getTitle());
        System.out.println("Second iterator, 1st next(): " + second.next().getTitle());

        System.out.println();
        System.out.println("=== Empty playlist ===");
        SongCollection empty = new Playlist();
        SongIterator emptyIterator = empty.createIterator();
        System.out.println("hasNext(): " + emptyIterator.hasNext());
        printSongs(emptyIterator);

        System.out.println();
        System.out.println("=== next() after the end ===");
        SongIterator finished = collection.createIterator();
        printSongs(finished);
        System.out.println("hasNext(): " + finished.hasNext());
        try {
            finished.next();
        } catch (NoSuchElementException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Reverse traversal ===");
        printSongs(playlist.createReverseIterator());

        System.out.println();
        System.out.println("=== New normal traversal after reverse ===");
        printSongs(collection.createIterator());

        System.out.println();
        System.out.println("=== Reverse traversal: next() after the end ===");
        SongIterator reverseFinished = playlist.createReverseIterator();
        printSongs(reverseFinished);
        try {
            reverseFinished.next();
        } catch (NoSuchElementException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Reverse traversal: empty playlist ===");
        SongIterator emptyReverse = new Playlist().createReverseIterator();
        System.out.println("hasNext(): " + emptyReverse.hasNext());
        printSongs(emptyReverse);
    }

    private static void printSongs(SongIterator iterator) {
        if (!iterator.hasNext()) {
            System.out.println("(no songs)");
            return;
        }
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getTitle());
        }
    }
}
