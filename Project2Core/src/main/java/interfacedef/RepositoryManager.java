package interfacedef;

import model.Album;
import model.Image;

import java.util.List;

public interface RepositoryManager {

    void addAlbum(Album album);

    boolean removeAlbum(String isrc);

    Album getAlbum(String isrc);

    List<Album> getAlbums();

    boolean updateAlbum(Album album);

    int addImage(Image image);

    boolean removeImage(int id);

    Image getImage(int id);

}
