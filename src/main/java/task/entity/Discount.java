package task.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "discounts")
public class Discount {
    @Id
    @GeneratedValue
    private int id;

    @OneToMany (mappedBy = "discount")
    private List<Good> goods;

    private double percent;
    private Date start_date;
    private Date end_date;

    public Discount(int id, List<Good> goods, double percent, Date start_date, Date end_date) {
        this.id = id;
        this.goods = goods;
        this.percent = percent;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Discount(List<Good> goods, double percent, Date start_date, Date end_date) {
        this.goods = goods;
        this.percent = percent;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Discount(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}
