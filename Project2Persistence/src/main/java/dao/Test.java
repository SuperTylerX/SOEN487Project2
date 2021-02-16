package dao;

import model.Album;
import model.Image;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ImageAttachmentGateway img=new ImageAttachmentGateway();
        AlbumGateway alg=new AlbumGateway();
        Album a1=new Album("12345","title","descrip 1",1999,"xiangxiang");
//        alg.createAlbum(a1,0);

//        ArrayList<Album> albums = alg.getAllAlbums();
//        System.out.println(albums);
//        ArrayList<Album> albums1=alg.getAlbumByArtist("xiangxiang");
//        System.out.println(albums1);
//        ArrayList<Album> albums2=alg.getAlbumByTitle("title");
//        System.out.println(albums2);
//        ArrayList<Album> albums3=alg.getAlbumByYear(1999);
//        System.out.println(albums3);


//        Album a2=alg.getAlbumByID(1);
//        System.out.println(a2);
//        Album a3=alg.getAlbumByISRC("12345");
//        System.out.println(a3);

//        alg.deleteAlbumByID(1);
//        alg.deleteAlbumByISRC("12345");
//        alg.deleteAlbumByISRC("12345");
        Album a2=new Album("2222","title","descrip 1",1999,"xiangxiang");

//        alg.updateAlbum(a2,3,0);
        Image i=new Image(new byte[5],"mime2");
//        img.createAttachment(i);
//        Image i2=img.getAttachment(1,3);
//        System.out.println(i2);
        img.deleteAttachment(3);


    }
}
