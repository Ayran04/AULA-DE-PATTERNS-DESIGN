import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Playlist implements SongCollection {

    private final List<Song> songs = new ArrayList<>();

    public void addSong(Song song) {
        songs.add(Objects.requireNonNull(song, "song must not be null"));
    }

    @Override
    public SongIterator createIterator() {
        return new PlaylistIterator();
    }

    public SongIterator createReverseIterator() {
        return new ReversePlaylistIterator();
    }

    private class PlaylistIterator implements SongIterator {

        private int nextIndex = 0;

        @Override
        public boolean hasNext() {
            return nextIndex < songs.size();
        }

        @Override
        public Song next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more songs in the playlist");
            }
            return songs.get(nextIndex++);
        }
    }

    private class ReversePlaylistIterator implements SongIterator {

        private int nextIndex = songs.size() - 1;

        @Override
        public boolean hasNext() {
            return nextIndex >= 0;
        }

        @Override
        public Song next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more songs in the playlist");
            }
            return songs.get(nextIndex--);
        }
    }
}
