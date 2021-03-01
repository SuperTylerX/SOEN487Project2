package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Logs {

    private int log_id;
    private String log_type;
    private long log_date;
    private String log_content;

    public Logs() {
    }

    public Logs(int log_id, String log_type, long log_date, String log_content) {
        this.log_id = log_id;
        this.log_type = log_type;
        this.log_date = log_date;
        this.log_content = log_content;
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

    @Override
    public String toString() {
        return  "[" + log_type +
                "] log_id:" + log_id +
                ", log_date:" + log_date +
                ", log_content:'" + log_content + '\n';
    }
}
