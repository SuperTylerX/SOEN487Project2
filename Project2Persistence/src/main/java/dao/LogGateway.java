package dao;

import MyException.RepException;
import model.Image;

import java.sql.*;

public class LogGateway {
    public int createLog(String type, long date) {

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
    // todo search logs with time range
    //todo search All logs
    //todo place all logs

    //clear logs
    public void clearLog() throws RepException {
        throw new RepException("The method is not yet supported.");
    }
}
