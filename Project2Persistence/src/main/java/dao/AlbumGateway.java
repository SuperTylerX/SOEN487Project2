package dao;

import model.Album;
import model.Image;

import java.sql.*;
import java.util.ArrayList;

public class AlbumGateway {
    // for log
    LogGateway log = new LogGateway();

    // create album and return album id
    public int createAlbum(Album album) {
        Connection connection = DBConnection.getConnection();
        int imageId = album.getImg().getId();
        try {
            String query;
            if (imageId == 0) {
                query = "INSERT INTO albums (album_isrc,album_title,album_description,album_year,album_artist) VALUES(?, ?, ?, ?, ?)";
            } else {
                query = "INSERT INTO albums (album_isrc,album_title,album_description,album_year,album_artist,image_id) VALUES(?, ?, ?, ?, ?, ?)";
            }

            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, album.getISRC());
            ps.setString(2, album.getTitle());
            ps.setString(3, album.getDescription());
            ps.setInt(4, album.getYear());
            ps.setString(5, album.getArtist());

            if (imageId != 0) {
                ps.setInt(6, imageId);
            }

            int i = ps.executeUpdate();

            // get the album ID
            if (i == 1) {
                log.createLog("CREATE", "create album: " + album);
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        album.setAlbumID(generatedKeys.getInt(1));
                    } else {
                        log.createLog("CREATE", "create album " + album + " failed");
                        throw new SQLException("create post failed, no Album ID obtained.");
                    }
                }
            } else {
                log.createLog("CREATE", "create album " + album + " failed");
            }

            return album.getAlbumID();

        } catch (SQLException ex) {
            log.createLog("CREATE", "FAIL: create album " + album + " failed");
            ex.printStackTrace();
            return -1;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // delete album
    public boolean deleteAlbumByID(int albumID) {
        Connection connection = DBConnection.getConnection();
        try {
            int j = 1;
            String query_get_image_id = "select image_id from albums where album_id= ? ";
            PreparedStatement ps = connection.prepareStatement(query_get_image_id, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, albumID);
            ResultSet rs = ps.executeQuery();
            int album_image_id = -1;
            while (rs.next()) {
                album_image_id = rs.getInt("image_id");
            }

            // delete album
            String query = "delete from albums where album_id = ? ;";
            PreparedStatement ps1 = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps1.setInt(1, albumID);
            int i = ps1.executeUpdate();

            // delete image
            if (album_image_id != 0) {
                String del_att = "delete from images where image_id = ? ;";
                PreparedStatement ps2 = connection.prepareStatement(del_att, Statement.RETURN_GENERATED_KEYS);
                ps2.setInt(1, album_image_id);
                j = ps2.executeUpdate();
            }
            if (i == 1 && j == 1) {
                log.createLog("DELETE", "delete album with id " + albumID);
            } else {
                log.createLog("DELETE", "delete album with id " + albumID + " failed. invalid album id");
            }
            return i == 1 && j == 1;
        } catch (Exception e) {
            log.createLog("DELETE", "delete album  with id " + albumID + " failed");
            e.printStackTrace();
            return false;
        }
    }

    // update album
    public boolean updateAlbum(Album album) {

        Connection connection = DBConnection.getConnection();
        int imageId = album.getImg().getId();
        try {
            PreparedStatement ps;
            if (imageId == 0) {
                ps = connection.prepareStatement("UPDATE albums SET album_isrc=?,album_title=?,album_description=?,album_year=?,album_artist=?,image_id= NULL WHERE album_id=?");
            } else {
                ps = connection.prepareStatement("UPDATE albums SET album_isrc=?,album_title=?,album_description=?,album_year=?,album_artist=?,image_id=? WHERE album_id=?");
            }

            ps.setString(1, album.getISRC());
            ps.setString(2, album.getTitle());
            ps.setString(3, album.getDescription());
            ps.setInt(4, album.getYear());
            ps.setString(5, album.getArtist());

            if (imageId == 0) {
                ps.setInt(6, album.getAlbumID());
            } else {
                ps.setInt(6, imageId);
                ps.setInt(7, album.getAlbumID());
            }

            int i = ps.executeUpdate();

            if (i == 1) {
                log.createLog("UPDATE", "update album: " + album + " successfully");
                return true;
            }
        } catch (Exception e) {
            log.createLog("UPDATE", "update album: " + album + " failed");
            e.printStackTrace();
            return false;
        }
        log.createLog("UPDATE", "update album: " + album + " failed");
        return false;
    }

    // get all albums
    public ArrayList<Album> getAllAlbums() {
        ArrayList<Album> albums = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            Statement stmt = connection.createStatement();
            String query = "select * from albums;";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Album album = new Album();
                album.setAlbumID(rs.getInt("album_id"));
                album.setArtist(rs.getString("album_artist"));
                album.setISRC(rs.getString("album_isrc"));
                album.setTitle(rs.getString("album_title"));
                album.setDescription(rs.getString("album_description"));
                album.setYear(rs.getInt("album_year"));
                Image image = new Image();
                image.setId(rs.getInt("image_id"));
                album.setImg(image);
                albums.add(album);
            }
            return albums;
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

    // search album
    public ArrayList<Album> filterAlbums(Album album) {
        String ISRC = album.getISRC();
        String title = album.getTitle();
        String artist = album.getArtist();
        int year = album.getYear();
        int id = album.getAlbumID();

        ArrayList<Album> result = new ArrayList<>();
        String query = "SELECT * " +
                "FROM albums " +
                "LEFT JOIN images i ON albums.image_id = i.image_id " +
                "WHERE album_isrc LIKE ?" +
                "AND album_year LIKE ?" +
                "AND album_artist LIKE ?" +
                "AND album_title LIKE ?" +
                "AND album_id LIKE ?";

        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            if (ISRC == null || ISRC.isEmpty()) {
                ps.setString(1, "%");
            } else {
                ps.setString(1, ISRC);
            }
            if (year == 0) {
                ps.setString(2, "%");
            } else {
                ps.setInt(2, year);
            }
            if (artist == null || artist.isEmpty()) {
                ps.setString(3, "%");
            } else {
                ps.setString(3, artist);
            }
            if (title == null || title.isEmpty()) {
                ps.setString(4, "%");
            } else {
                ps.setString(4, title);
            }
            if (id == 0) {
                ps.setString(5, "%");
            } else {
                ps.setInt(5, id);
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Album album1 = new Album();
                album1.setAlbumID(rs.getInt("album_id"));
                album1.setArtist(rs.getString("album_artist"));
                album1.setISRC(rs.getString("album_isrc"));
                album1.setTitle(rs.getString("album_title"));
                album1.setDescription(rs.getString("album_description"));
                album1.setYear(rs.getInt("album_year"));
                Image image = new Image();
                image.setId(rs.getInt("image_id"));
                album1.setImg(image);
                result.add(album1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
