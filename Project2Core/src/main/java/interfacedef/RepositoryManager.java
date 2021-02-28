package interfacedef;

import MyException.RepException;
import model.Album;
import model.Image;
import model.Logs;

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

    Image getImage(int id);

    ArrayList<Logs> getLogsByType(String type);

    ArrayList<Logs> getAllLogs();

    ArrayList<Logs> getLogsByDate(long start, long end);

    void clearLogs() throws RepException;
}
