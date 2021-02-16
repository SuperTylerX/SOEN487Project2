package dao;

import model.Album;
import model.Image;

import java.sql.*;

public class ImageAttachmentGateway {
    //for log
    LogGateway log = new LogGateway();

    //return image id after image is created. return -1 means creat fail
    public int createAttachment(Image image) {

        Connection connection = DBConnection.getConnection();

        try {
            String query = "INSERT INTO images (image_mime,image_content) VALUES(?,?)";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, image.getMime());
            ps.setBytes(2, image.getContent());

            int i = ps.executeUpdate();

            // get the attachID
            if (i == 1) {
                log.createLog("CREATE", "create image Attachment " + image.printImg() + " success");
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        image.setId(generatedKeys.getInt(1));
                    }
                }
            } else {
                log.createLog("CREATE", "create image Attachment " + image.printImg() + " FAILED");

                return -1;
            }
            return image.getId();
        } catch (SQLException ex) {
            log.createLog("CREATE", "create image Attachment " + image.printImg() + " FAILED");

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

    //return true when success delete
    public boolean deleteAttachment(int imageID) {
        Connection connection = DBConnection.getConnection();
        try {
            //delete attachment
            if (imageID != 0) {
                String query = "DELETE FROM images WHERE image_id = ?";
                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, imageID);
                int i = ps.executeUpdate();
                if (i == 1) {
                    log.createLog("DELETE", "delete image Attachment with id " + imageID + " success");
                } else {
                    log.createLog("DELETE", "delete image Attachment with id " + imageID + " success, no image has this ID");

                }
                return i == 1;
            }
            return false;
        } catch (SQLException ex) {
            log.createLog("DELETE", "delete image Attachment with id " + imageID + " FAILED");

            ex.printStackTrace();
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    //return an image obj
    public Image getAttachment(int ImageID, int AlbumID) {

        Connection connection = DBConnection.getConnection();
        try {

            Statement stmt = connection.createStatement();
            String query = "select * from albums as al,images as im where al.image_id=im.image_id and album_id = ? and im.image_id = ?;";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, AlbumID);
            ps.setInt(2, ImageID);
            ResultSet rs = ps.executeQuery();

            Image image = new Image();

            if (rs.next()) {
                image.setMime(rs.getString("image_mime"));
                image.setId(rs.getInt("image_id"));
                image.setContent(rs.getBytes("image_content"));
                connection.close();
                log.createLog("GET", "Get image Attachment with imageID " + ImageID + " and albumID: " + AlbumID + " SUCCESS");

                return image;
            } else {
                connection.close();
                log.createLog("GET", "Get image Attachment with imageID " + ImageID + " and albumID: " + AlbumID + " SUCCESS, no such image");

                return null;
            }

        } catch (SQLException e) {

            log.createLog("GET", "Get image Attachment with imageID " + ImageID + " and albumID: " + AlbumID + " FAILED");

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

    //update image by ID
    public boolean updateImage(Image image,  int imageID) {

        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement ps;
            ps = connection.prepareStatement("UPDATE images SET image_content=?,image_mime=? WHERE image_id=?");

            ps.setBytes(1, image.getContent());
            ps.setString(2, image.getMime());
            ps.setInt(3,imageID);

            int i = ps.executeUpdate();
            if (i == 1) {
                log.createLog("UPDATE", "UPDATE: update image: " + image.printImg() + " success");
                return true;
            }
        } catch (Exception e) {
            log.createLog("UPDATE", "UPDATE: update image: " + image.printImg() + " FAILED");
            e.printStackTrace();
            return false;
        }
        log.createLog("UPDATE", "UPDATE: update image: " + image.printImg() + " FAILED, no image has this ID");
        return false;
    }


}
