package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Logs {

    private int log_id;
    private String log_type;
    private long log_date;
    private String log_content;
    private String log_isrc;

    public Logs() {
    }

    public Logs(int log_id, String log_type, long log_date, String log_content, String log_isrc) {
        this.log_id = log_id;
        this.log_type = log_type;
        this.log_date = log_date;
        this.log_content = log_content;
        this.log_isrc = log_isrc;
    }

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public String getLog_type() {
        return log_type;
    }

    public void setLog_type(String log_type) {
        this.log_type = log_type;
    }

    public long getLog_date() {
        return log_date;
    }

    public void setLog_date(long log_date) {
        this.log_date = log_date;
    }

    public String getLog_content() {
        return log_content;
    }

    public void setLog_content(String log_content) {
        this.log_content = log_content;
    }

    public String getLog_isrc() {
        return log_isrc;
    }

    public void setLog_isrc(String log_isrc) {
        this.log_isrc = log_isrc;
    }
}
