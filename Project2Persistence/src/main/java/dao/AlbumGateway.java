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
    //great albums
//    public int createPost(Post post, int attachID) {
//        Connection connection = DBConnection.getConnection();
//
//        try {
//            String query;
//            if (attachID == 0) {
//                query = "INSERT INTO posts (post_title,post_content,post_author_id,post_created_date,post_modified_date,post_group_id) VALUES(?, ?, ?, ?, ?, ?)";
//            } else {
//                query = "INSERT INTO posts (post_title,post_content,post_author_id,post_created_date,post_modified_date,post_group_id,post_attach_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
//            }
//
//            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//
//            ps.setString(1, post.getPostTitle());
//            ps.setString(2, post.getPostContent());
//            ps.setInt(3, (int) post.getPostAuthorID());
//            ps.setLong(4, post.getPostCreatedDate());
//            ps.setLong(5, post.getPostModifiedDate());
//            ps.setLong(6, post.getPostGroupID());
//            if (attachID != 0) {
//                ps.setInt(7, attachID);
//            }
//
//            int i = ps.executeUpdate();
//
//            // get the postID
//            if (i == 1) {
//
//                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        post.setPostID(generatedKeys.getInt(1));
//                    } else {
//                        throw new SQLException("Creating post failed, no PostID obtained.");
//                    }
//                }
//            }
//
//            return post.getPostID();
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            return -1;
//        } finally {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }


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
