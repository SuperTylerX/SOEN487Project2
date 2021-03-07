package interfacedef;

import MyException.RepException;
import model.Album;
import model.Image;
import model.Logs;

import java.util.ArrayList;

public interface RepositoryManager {

    int addAlbum(Album album);

    boolean removeAlbum(int albumID);

    ArrayList<Album> getAlbums();

    ArrayList<Album> filterAlbum(Album album);

    boolean updateAlbum(Album album);

    int addImage(Image image);

    boolean removeImage(int id);

    Image getImage(int id);

    ArrayList<Logs> getLogsWithFilter(String type, long startDate, long endDate);

    void clearLogs() throws RepException;
}
