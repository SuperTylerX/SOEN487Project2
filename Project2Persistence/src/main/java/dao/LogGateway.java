package dao;

import model.Logs;

import java.sql.*;
import java.util.ArrayList;

public class LogGateway {
    // return 1 if log create successfully, -1 not successfully
    public int createLog(String type, String content, String isrc) {
        Connection connection = DBConnection.getConnection();
        try {
            String query = "INSERT INTO logs (log_type, log_date, log_content, log_isrc) VALUES(?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, type);
            long date = System.currentTimeMillis();
            ps.setLong(2, date);
            ps.setString(3, content);
            ps.setString(4, isrc);
            return ps.executeUpdate();
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

    public ArrayList<Logs> getLogsWithFilter(String type, long startDate, long endDate) {
        ArrayList<Logs> result = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            Statement stmt = connection.createStatement();
            String queryWithoutDateRange = "select * from logs where log_type LIKE ? ;";
            String queryWithDateRange = "select * from logs where log_type LIKE ? AND log_date between ? And ?";
            String query;
            if (type == null || type.isEmpty()) {
                type = "%";
            }
            if (startDate == 0 || endDate == 0) {
                query = queryWithoutDateRange;
            } else {
                query = queryWithDateRange;
            }
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, type);
            if (startDate != 0 && endDate != 0) {
                ps.setLong(2, startDate);
                ps.setLong(3, endDate);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Logs log = new Logs();
                log.setLog_date(rs.getLong("log_date"));
                log.setLog_id(rs.getInt("log_id"));
                log.setLog_type(rs.getString("log_type"));
                log.setLog_content(rs.getString("log_content"));
                log.setLog_isrc(rs.getString("log_isrc"));
                result.add(log);
            }
            return result;
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
