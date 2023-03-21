package task.dto;

import java.util.Date;

public class DiscountDTO {
    private double percent;
    private Date start_date;
    private Date end_date;

    public DiscountDTO(double percent, Date start_date, Date end_date) {
        this.percent = percent;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public DiscountDTO() {
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
