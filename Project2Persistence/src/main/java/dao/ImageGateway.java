package dao;

import model.Image;

import java.sql.*;

public class ImageGateway {
    // for log
    LogGateway log = new LogGateway();

    // return image id after image has been created. return -1 means creat fail
    public int createImage(Image image) {

        Connection connection = DBConnection.getConnection();

        try {
            String query = "INSERT INTO images (image_mime,image_content) VALUES(?,?)";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, image.getMime());
            ps.setBytes(2, image.getContent());

            int i = ps.executeUpdate();

            // get the attachID
            if (i == 1) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        image.setId(generatedKeys.getInt(1));
                    }
                }
            } else {
                return -1;
            }
            return image.getId();
        } catch (SQLException ex) {
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

    // return true if delete image successfully
    public boolean deleteImage(int imageID) {
        Connection connection = DBConnection.getConnection();
        try {
            //delete image
            if (imageID != 0) {
                String query = "DELETE FROM images WHERE image_id = ?";
                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, imageID);
                int i = ps.executeUpdate();
                if (i == 1) {
//                    log.createLog("DELETE", "delete image with id " + imageID + " successfully");
                } else {
//                    log.createLog("DELETE", "delete image with id " + imageID + " successfully, invalid ID");
                }
                // Set Album image_id to null
                String query_set_image_id_null = "UPDATE albums set image_id = NULL where image_id = ?";
                ps = connection.prepareStatement(query_set_image_id_null, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, imageID);
                i = ps.executeUpdate();
                return i == 1;
            }
            return false;
        } catch (SQLException ex) {
//            log.createLog("DELETE", "delete image with id " + imageID + " failed");
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

    // return an image obj
    public Image getImage(int imageID) {

        Connection connection = DBConnection.getConnection();
        try {

            Statement stmt = connection.createStatement();
            String query = "select * from images as im where im.image_id = ?;";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, imageID);
            ResultSet rs = ps.executeQuery();

            Image image = new Image();

            if (rs.next()) {
                image.setMime(rs.getString("image_mime"));
                image.setId(rs.getInt("image_id"));
                image.setContent(rs.getBytes("image_content"));
                connection.close();
                return image;
            } else {
                connection.close();
                return null;
            }

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
