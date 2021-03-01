package dao;

import MyException.RepException;
import model.Logs;

import java.sql.*;
import java.util.ArrayList;

public class LogGateway {
    // return 1 if log create successfully, -1 not successfully
    public int createLog(String type, String content) {
        Connection connection = DBConnection.getConnection();
        try {
            String query = "INSERT INTO logs (log_type,log_date,log_content) VALUES(?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, type);
            long date = System.currentTimeMillis();
            ps.setLong(2, date);
            ps.setString(3, content);
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

    // search logs with time range
    public ArrayList<Logs> readLogsByDate(long startDate, long endDate) {
        ArrayList<Logs> Logs = new ArrayList<>();
        Connection connection = DBConnection.getConnection();

        try {
            Statement stmt = connection.createStatement();
            String query = "select * from logs where log_date between ? And ? ;";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, startDate);
            ps.setLong(2, endDate);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Logs log = new Logs();
                log.setLog_date(rs.getLong("log_date"));
                log.setLog_id(rs.getInt("log_id"));
                log.setLog_content(rs.getString("log_content"));

                Logs.add(log);
            }
            return Logs;
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

    // search all logs
    public ArrayList<Logs> readAllLogs() {
        ArrayList<Logs> Logs = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            Statement stmt = connection.createStatement();
            String query = "select * from logs;";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Logs log = new Logs();
                log.setLog_date(rs.getLong("log_date"));
                log.setLog_id(rs.getInt("log_id"));
                log.setLog_content(rs.getString("log_content"));
                Logs.add(log);
            }
            return Logs;
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

    // search logs by type
    public ArrayList<Logs> readLogsByType(String type) {
        ArrayList<Logs> Logs = new ArrayList<>();
        Connection connection = DBConnection.getConnection();

        try {
            Statement stmt = connection.createStatement();
            String query = "select * from logs where log_type= ? ;";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Logs log = new Logs();
                log.setLog_date(rs.getLong("log_date"));
                log.setLog_id(rs.getInt("log_id"));
                log.setLog_content(rs.getString("log_content"));
                Logs.add(log);
            }
            return Logs;
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

    //clear logs
    public void clearLogs() throws RepException {
        throw new RepException("The method is not yet supported.");
    }
}
