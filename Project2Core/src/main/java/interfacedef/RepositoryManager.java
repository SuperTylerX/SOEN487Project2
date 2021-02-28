package interfacedef;

import model.Album;
import model.Image;

import java.util.ArrayList;

public interface RepositoryManager {

    int addAlbum(Album album);

    boolean removeAlbum(int albumID);

    Album getAlbumByISRC(String isrc);

    Album getAlbumByID(int albumID);

    ArrayList<Album> getAlbumByTitle(String title);

    ArrayList<Album> getAlbumByYear(int year);

    ArrayList<Album> getAlbumByArtist(String artist);

    ArrayList<Album> getAlbums();

    boolean updateAlbum(Album album);

    int addImage(Image image);

    boolean removeImage(int id);

    boolean updateImage(Image image, int id);

    Image getImage(int id);

}
