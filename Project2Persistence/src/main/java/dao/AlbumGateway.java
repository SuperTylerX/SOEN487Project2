package dao;

import model.Album;

import java.sql.*;
import java.util.ArrayList;

public class AlbumGateway {
    //get all albums
    public ArrayList<Album> getAllAlbums() {
        ArrayList<Album> posts = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            Statement stmt = connection.createStatement();
            String query = "select * from albums;";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Album album = new Album();
                album.setArtist(rs.getString("album_artist"));
                album.setISRC(rs.getString("album_isrc"));
                album.setTitle(rs.getString("album_title"));
                album.setDescription(rs.getString("album_description"));
                album.setYear(rs.getInt("album_year"));
                posts.add(album);
            }
            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    private void removeDuplicated(ArrayList<Album> Albums) {
        for (int i = 0; i < Albums.size(); i++) {
            for (int j = i + 1; j < Albums.size(); j++) {
                if (Albums.get(i).getISRC().equals(Albums.get(j).getISRC())) {
                    Albums.remove(j);
                    j--;
                }
            }
        }

    }

}
