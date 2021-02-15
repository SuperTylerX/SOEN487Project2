package dao;

import model.Image;

import java.sql.*;

public class ImageAttachmentGateway {
    //return image id after image is created. return -1 means creat fail
    public int createAttachment(Image image) {

        Connection connection = DBConnection.getConnection();

        try {
            String query = "INSERT INTO images (image_mime,image_content) VALUES(?,?)";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, image.getMime());

            Blob blob = connection.createBlob();
            blob.setBytes(1, image.getContent());
            ps.setBlob(2, blob);

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
                return i == 1;
            }
            return false;
        } catch (SQLException ex) {
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

                return image;
            } else {
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
