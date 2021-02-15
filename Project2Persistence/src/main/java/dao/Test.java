package dao;

import model.Album;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        AlbumGateway alg=new AlbumGateway();
        ArrayList<Album> albums = alg.getAllAlbums();
        System.out.println(albums);

    }
}
