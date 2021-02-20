package dao;

import model.Album;
import model.Image;

import java.sql.*;
import java.util.ArrayList;

public class AlbumGateway {
    //for log
    LogGateway log = new LogGateway();

    //creat album return album id
    public int createAlbum(Album album, int attachID) {
        Connection connection = DBConnection.getConnection();

        try {
            String query;
            if (attachID == 0) {
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

            if (attachID != 0) {
                ps.setInt(6, attachID);
            }

            int i = ps.executeUpdate();

            // get the postID
            if (i == 1) {
                log.createLog("CREATE", "SUCCESS: creat album: " + album);
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        album.setAlbumID(generatedKeys.getInt(1));
                    } else {
                        log.createLog("CREATE", "FAIL: creat album " + album + " failed");
                        throw new SQLException("Creating post failed, no PostID obtained.");
                    }
                }
            } else {
                log.createLog("CREATE", "FAIL: creat album " + album + " failed");
            }

            return album.getAlbumID();

        } catch (SQLException ex) {
            log.createLog("CREATE", "FAIL: creat album " + album + " failed");
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

    //delete album by ID
    public boolean deleteAlbumByID(int albumID) {
        Connection connection = DBConnection.getConnection();

        try {
            int j = 1;
            String query_get_attach_id = "select image_id from albums where album_id= ? ";
            PreparedStatement ps = connection.prepareStatement(query_get_attach_id, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, albumID);
            ResultSet rs = ps.executeQuery();
            int album_attach_id = -1;
            while (rs.next()) {
                album_attach_id = rs.getInt("image_id");
            }
            //delete post
            String query = "delete from albums where album_id = ? ;";
            PreparedStatement ps1 = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps1.setInt(1, albumID);
            int i = ps1.executeUpdate();
            //delete attachment
            if (album_attach_id != 0) {
                String del_att = "delete from images where image_id = ? ;";
                PreparedStatement ps2 = connection.prepareStatement(del_att, Statement.RETURN_GENERATED_KEYS);
                ps2.setInt(1, album_attach_id);
                j = ps2.executeUpdate();
            }
            if (i == 1 && j == 1) {
                log.createLog("DELETE", "SUCCESS: delete album which id is: " + albumID);
            } else {
                log.createLog("DELETE", "FAILED: delete album which id is: " + albumID + " success, there is no albums has this ID");
            }
            return i == 1 && j == 1;
        } catch (Exception e) {
            log.createLog("DELETE", "FAILED: delete album which id is: " + albumID + " FIALED");
            e.printStackTrace();
            return false;
        }
    }

//    //delete album by isrc
//    public boolean deleteAlbumByISRC(String isrc) {
//        Connection connection = DBConnection.getConnection();
//
//        try {
//            int j = 1;
//            String query_get_attach_id = "select image_id from albums where album_isrc= ? ";
//            PreparedStatement ps = connection.prepareStatement(query_get_attach_id, Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, isrc);
//            ResultSet rs = ps.executeQuery();
//            int album_attach_id = -1;
//            while (rs.next()) {
//                album_attach_id = rs.getInt("image_id");
//            }
//            //delete post
//            String query = "delete from albums where album_isrc = ? ;";
//            PreparedStatement ps1 = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//            ps1.setString(1, isrc);
//            int i = ps1.executeUpdate();
//            //delete attachment
//            if (album_attach_id != 0) {
//                String del_att = "delete from images where image_id = ? ;";
//                PreparedStatement ps2 = connection.prepareStatement(del_att, Statement.RETURN_GENERATED_KEYS);
//                ps2.setInt(1, album_attach_id);
//                j = ps2.executeUpdate();
//            }
//            if (i == 1 && j == 1) {
//                log.createLog("DELETE", "SUCCESS: delete album which isrc is: " + isrc);
//            } else {
//                log.createLog("DELETE", "FAILED: delete album which isrc is: " + isrc + " success, there is no album has this isrc");
//            }
//            return i == 1 && j == 1;
//        } catch (Exception e) {
//            log.createLog("DELETE", "FAILED: delete album which isrc is: " + isrc + " FIALED");
//            e.printStackTrace();
//            return false;
//        }
//    }

    //update album
    public boolean updateAlbum(Album album, int imageID) {

        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement ps;
            if (imageID == 0) {
                ps = connection.prepareStatement("UPDATE albums SET album_isrc=?,album_title=?,album_description=?,album_year=?,album_artist=?,image_id= NULL WHERE album_id=?");
            } else {
                ps = connection.prepareStatement("UPDATE albums SET album_isrc=?,album_title=?,album_description=?,album_year=?,album_artist=?,image_id=? WHERE album_id=?");
            }

            ps.setString(1, album.getISRC());
            ps.setString(2, album.getTitle());
            ps.setString(3, album.getDescription());
            ps.setInt(4, album.getYear());
            ps.setString(5, album.getArtist());

            if (imageID == 0) {
                ps.setInt(6, album.getAlbumID());
            } else {
                ps.setInt(6, imageID);
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

    //get all albums
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
                album.setArtist(rs.getString("album_artist"));
                album.setISRC(rs.getString("album_isrc"));
                album.setTitle(rs.getString("album_title"));
                album.setDescription(rs.getString("album_description"));
                album.setYear(rs.getInt("album_year"));
                albums.add(album);
            }
            log.createLog("SEARCH", "get all albums successfully");
            return albums;
        } catch (SQLException e) {
            log.createLog("SEARCH", "get all albums failed");
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

    //get albums by id
    public Album getAlbumByID(int album_id) {
        Connection connection = DBConnection.getConnection();
        try {
            Statement stmt = connection.createStatement();
            String query = "select * from albums LEFT JOIN images i on albums.image_id = i.image_id where album_id=?;";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, album_id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Album al = new Album();
                al.setAlbumID(rs.getInt("album_id"));
                al.setISRC(rs.getString("album_isrc"));
                al.setTitle(rs.getString("album_title"));
                al.setDescription(rs.getString("album_description"));
                al.setYear(rs.getInt("album_year"));
                al.setArtist(rs.getString("album_artist"));

                if (rs.getInt("image_id") != 0) {
                    Image img = new Image();
                    img.setId(rs.getInt("image_id"));
                    img.setMime(rs.getString("image_mime"));
                    al.setImg(img);
                }
                connection.close();
                log.createLog("SEARCH", "get albums by ID: " + album_id + " successfully");
                return al;
            } else {
                connection.close();
                log.createLog("SEARCH", "get albums by ID: " + album_id + " successfully, invalid ID");
                return null;
            }

        } catch (SQLException e) {
            log.createLog("SEARCH", "get albums by ID: " + album_id + " failed");
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

    //get albums by isrc
    public Album getAlbumByISRC(String isrc) {
        Connection connection = DBConnection.getConnection();
        try {
            Statement stmt = connection.createStatement();
            String query = "select * from albums LEFT JOIN images i on albums.image_id = i.image_id where album_isrc=?;";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, isrc);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Album al = new Album();
                al.setAlbumID(rs.getInt("album_id"));

                al.setISRC(rs.getString("album_isrc"));
                al.setTitle(rs.getString("album_title"));
                al.setDescription(rs.getString("album_description"));
                al.setYear(rs.getInt("album_year"));
                al.setArtist(rs.getString("album_artist"));

                if (rs.getInt("image_id") != 0) {
                    Image img = new Image();
                    img.setId(rs.getInt("image_id"));
                    img.setMime(rs.getString("image_mime"));
                    al.setImg(img);
                }
                connection.close();
                log.createLog("SEARCH", "get albums by isrc: " + isrc + " success");
                return al;
            } else {
                connection.close();
                log.createLog("SEARCH", "get albums by isrc: " + isrc + " success, no album has this isrc");

                return null;
            }

        } catch (SQLException e) {
            log.createLog("SEARCH", "get albums by isrc: " + isrc + " FAILED");

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

    //get albums by title
    public ArrayList<Album> getAlbumByTitle(String title) {
        Connection connection = DBConnection.getConnection();
        try {
            ArrayList<Album> albums = new ArrayList<>();
            Statement stmt = connection.createStatement();
            String query = "select * from albums LEFT JOIN images i on albums.image_id = i.image_id where album_title=?;";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, title);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Album al = new Album();
                al.setAlbumID(rs.getInt("album_id"));

                al.setISRC(rs.getString("album_isrc"));
                al.setTitle(rs.getString("album_title"));
                al.setDescription(rs.getString("album_description"));
                al.setYear(rs.getInt("album_year"));
                al.setArtist(rs.getString("album_artist"));

                if (rs.getInt("image_id") != 0) {
                    Image img = new Image();
                    img.setId(rs.getInt("image_id"));
                    img.setMime(rs.getString("image_mime"));
                    al.setImg(img);
                }
                albums.add(al);
            }
            log.createLog("SEARCH", "get albums by title: " + title + " success");
            removeDuplicated(albums);
            return albums;

        } catch (SQLException e) {
            log.createLog("SEARCH", "get albums by title: " + title + " FAILED");

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

    //get albums by year
    public ArrayList<Album> getAlbumByYear(int year) {
        Connection connection = DBConnection.getConnection();
        ArrayList<Album> albums = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String query = "select * from albums LEFT JOIN images i on albums.image_id = i.image_id where album_year=?;";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, year);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Album al = new Album();
                al.setAlbumID(rs.getInt("album_id"));

                al.setISRC(rs.getString("album_isrc"));
                al.setTitle(rs.getString("album_title"));
                al.setDescription(rs.getString("album_description"));
                al.setYear(rs.getInt("album_year"));
                al.setArtist(rs.getString("album_artist"));

                if (rs.getInt("image_id") != 0) {
                    Image img = new Image();
                    img.setId(rs.getInt("image_id"));
                    img.setMime(rs.getString("image_mime"));
                    al.setImg(img);
                }
                albums.add(al);
            }
            log.createLog("SEARCH", "get albums by year: " + year + " success");
            removeDuplicated(albums);
            return albums;
        } catch (SQLException e) {
            log.createLog("SEARCH", "get albums by year: " + year + " FAILED");
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

    //get albums by artist
    public ArrayList<Album> getAlbumByArtist(String artist) {
        Connection connection = DBConnection.getConnection();
        ArrayList<Album> albums = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String query = "select * from albums LEFT JOIN images i on albums.image_id = i.image_id where album_artist=?;";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, artist);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Album al = new Album();
                al.setAlbumID(rs.getInt("album_id"));
                al.setISRC(rs.getString("album_isrc"));
                al.setTitle(rs.getString("album_title"));
                al.setDescription(rs.getString("album_description"));
                al.setYear(rs.getInt("album_year"));
                al.setArtist(rs.getString("album_artist"));

                if (rs.getInt("image_id") != 0) {
                    Image img = new Image();
                    img.setId(rs.getInt("image_id"));
                    img.setMime(rs.getString("image_mime"));
                    al.setImg(img);
                }

                albums.add(al);
            }
            log.createLog("SEARCH", "get albums by artist: " + artist + " success");
            removeDuplicated(albums);
            return albums;
        } catch (SQLException e) {
            log.createLog("SEARCH", "get albums by artist: " + artist + " FAILED");

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

    //for remove duplicated
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
