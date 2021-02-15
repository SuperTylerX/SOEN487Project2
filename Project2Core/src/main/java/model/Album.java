package model;

public class Album {

    private int albumID;
    private String ISRC;
    private String title;
    private String description;
    private int year;
    private String artist;

    public Album(String ISRC, String title, String description, int year, String artist) {
        this.ISRC = ISRC;
        this.title = title;
        this.description = description;
        this.year = year;
        this.artist = artist;
    }

    public Album(Album album) {
        this.ISRC = album.ISRC;
        this.title = album.title;
        this.description = album.description;
        this.year = album.year;
        this.artist = album.artist;
    }

    public Album() {

    }

    public Album clone() {
        return new Album(this);
    }

    public String getISRC() {
        return ISRC;
    }

    public void setISRC(String ISRC) {
        this.ISRC = ISRC;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    @Override
    public String toString() {
        return "Album{" +
                "ISRC='" + ISRC + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", artist='" + artist + '\'' +
                '}';
    }

    public String printTitle() {
        return "Album {" +
                "ISRC='" + ISRC + '\'' +
                ", title='" + title + '\'' + "}";
    }
}
