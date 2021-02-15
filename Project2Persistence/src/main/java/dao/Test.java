package dao;

import model.Album;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        PostDAO p = new PostDAO();
        ArrayList<Album> albums = p.getAlbums();
        System.out.println(albums);

    }
}
