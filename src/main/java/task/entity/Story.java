package task.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "story")
public class Story {
    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "good_id")
    private Good good;

    private Date date;

    public Story(User user, Good good) {
        this.user = user;
        this.good = good;
    }

    public Story(User user, Good good, Date date) {
        this.user = user;
        this.good = good;
        this.date = date;
    }

    public Story() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
