package impl;

import interfacedef.*;
import model.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RepositoryManagerImpl implements RepositoryManager {

    private List<Album> albums = new CopyOnWriteArrayList<>();
    private static final RepositoryManagerImpl single_instance = new RepositoryManagerImpl();

    public static RepositoryManagerImpl getInstance() {
        return single_instance;
    }

    @Override
    public void addAlbum(Album album) {
        albums.add(album);
    }

    @Override
    public boolean removeAlbum(String isrc) {
        for (int i = 0; i < albums.size(); i++) {
            if (albums.get(i).getISRC().equals(isrc)) {
                albums.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public Album getAlbum(String isrc) {
        for (Album album : albums) {
            if (album.getISRC().equals(isrc)) {
                return album.clone();
            }
        }
        return null;
    }

    @Override
    public List<Album> getAlbums() {
        return (CopyOnWriteArrayList) ((CopyOnWriteArrayList) albums).clone();
    }

    @Override
    public boolean updateAlbum(Album album) {
        for (Album a : albums) {
            if (a.getISRC().equals(album.getISRC())) {
                a.setArtist(album.getArtist());
                a.setDescription(album.getDescription());
                a.setTitle(album.getTitle());
                a.setYear(album.getYear());
                return true;
            }
        }
        return false;
    }

    @Override
    public void addImage(Image image) {

    }

    @Override
    public boolean removeImage(int id) {
        return false;
    }

    @Override
    public Image getImage(int id) {
        return null;
    }

}
