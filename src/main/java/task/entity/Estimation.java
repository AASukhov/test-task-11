package task.entity;

import javax.persistence.*;

@Table (name = "estimations")
@Entity
public class Estimation {
    @Id
    @GeneratedValue
    private int id;

    private double mark;

    private String review;

    @OneToOne
    @JoinColumn (name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn (name = "good_id")
    private Good good;

    public Estimation (User user, Good good) {
        this.user = user;
        this.good = good;
    }

    public Estimation(){}

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

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
