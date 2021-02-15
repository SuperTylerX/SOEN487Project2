package dao;


import model.Album;

import java.sql.*;
import java.util.ArrayList;

public class PostDAO {

    public ArrayList<Album> getAlbums() {
        ArrayList<Album> posts = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            Statement stmt = connection.createStatement();
            String query = "select * from albums;";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Album album = new Album();
//                p.setPostID(rs.getInt("post_id"));

                album.setArtist(rs.getString("artist"));
                album.setISRC(rs.getString("isrc"));
                album.setTitle(rs.getString("title"));
                album.setDescription(rs.getString("description"));
                album.setYear(rs.getInt("year"));

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

}
