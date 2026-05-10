package ie.setu.nationalgalleryroutefinder.model;

import java.util.Objects;

public class Exhibit {
    // fields
    private String title;
    private String artist;

    // constructor
    public Exhibit(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }
    public String getArtist() {
        return artist;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Exhibit))
            return false;
        Exhibit e = (Exhibit) o;
        return title.equals(e.title) && artist.equals(e.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artist);
    }
}