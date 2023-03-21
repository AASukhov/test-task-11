package task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class StoryResponseDTO {

    @JsonProperty("user_id")
    private int user_id;

    @JsonProperty("good_id")
    private int good_id;

    @JsonProperty("date")
    private Date date;

    public StoryResponseDTO(int user_id, int good_id, Date date) {
        this.user_id = user_id;
        this.good_id = good_id;
        this.date = date;
    }

    public StoryResponseDTO(){};

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
