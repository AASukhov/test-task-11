package task.entity;

import javax.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue
    private int id;
    private String header;
    private String text;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public Notification(String header, String text, User user) {
        this.header = header;
        this.text = text;
        this.user = user;
    }

    public Notification(String header, String text) {
        this.header = header;
        this.text = text;
    }

    public Notification (){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
