package model;

public class Logs {
    private String log_id;
    private String log_type;
    private String log_date;

    public Logs() {
    }

    public Logs(String log_id, String log_type, String log_date) {
        this.log_id = log_id;
        this.log_type = log_type;
        this.log_date = log_date;
    }

    @Override
    public String toString() {
        return "Logs{" +
                "log_id='" + log_id + '\'' +
                ", log_type='" + log_type + '\'' +
                ", log_date='" + log_date + '\'' +
                '}';
    }

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getLog_type() {
        return log_type;
    }

    public void setLog_type(String log_type) {
        this.log_type = log_type;
    }

    public String getLog_date() {
        return log_date;
    }

    public void setLog_date(String log_date) {
        this.log_date = log_date;
    }


}
