package impl;

import MyException.RepException;
import dao.AlbumGateway;
import dao.ImageGateway;
import dao.LogGateway;
import interfacedef.*;
import model.*;

import java.util.ArrayList;


public class RepositoryManagerImpl implements RepositoryManager {

    private static final RepositoryManagerImpl single_instance = new RepositoryManagerImpl();

    public static RepositoryManagerImpl getInstance() {
        return single_instance;
    }

    private final AlbumGateway albumGateway = new AlbumGateway();
    private final ImageGateway imageGateway = new ImageGateway();
    private final LogGateway logGateway = new LogGateway();

    @Override
    public int addAlbum(Album album) {
        return albumGateway.createAlbum(album);
    }

    @Override
    public boolean removeAlbum(int albumID) {
        return albumGateway.deleteAlbumByID(albumID);
    }

    @Override
    public ArrayList<Album> filterAlbum(Album album) {
        return albumGateway.filterAlbums(album);
    }

    @Override
    public ArrayList<Album> getAlbums() {
        return albumGateway.getAllAlbums();
    }

    @Override
    public boolean updateAlbum(Album album) {
        return albumGateway.updateAlbum(album);
    }

    @Override
    public int addImage(Image image) {
        return imageGateway.createImage(image);
    }

    @Override
    public boolean removeImage(int id) {
        return imageGateway.deleteImage(id);
    }

    @Override
    public Image getImage(int id) {
        return imageGateway.getImage(id);
    }

    @Override
    public ArrayList<Logs> getLogsByType(String type) {
        return logGateway.readLogsByType(type);
    }

    @Override
    public ArrayList<Logs> getAllLogs() {
        return logGateway.readAllLogs();
    }

    @Override
    public ArrayList<Logs> getLogsByDate(long start, long end) {
        return logGateway.readLogsByDate(start, end);
    }

    @Override
    public void clearLogs() throws RepException {
        throw new RepException("The method is not yet supported.");
    }

}
