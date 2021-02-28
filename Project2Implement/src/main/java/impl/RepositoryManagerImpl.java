package impl;

import dao.AlbumGateway;
import dao.ImageGateway;
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

    @Override
    public int addAlbum(Album album) {
        return albumGateway.createAlbum(album);
    }

    @Override
    public boolean removeAlbum(int albumID) {
        return albumGateway.deleteAlbumByID(albumID);
    }

    @Override
    public Album getAlbumByISRC(String isrc) {
        return albumGateway.getAlbumByISRC(isrc);
    }

    @Override
    public Album getAlbumByID(int albumID) {
        return albumGateway.getAlbumByID(albumID);
    }

    @Override
    public ArrayList<Album> getAlbumByTitle(String title) {
        return albumGateway.getAlbumByTitle(title);
    }

    @Override
    public ArrayList<Album> getAlbumByYear(int year) {
        return albumGateway.getAlbumByYear(year);
    }

    @Override
    public ArrayList<Album> getAlbumByArtist(String artist) {
        return albumGateway.getAlbumByArtist(artist);
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

}
