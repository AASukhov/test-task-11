package task.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewResponseDTO {

    @JsonProperty("review")
    private String review;

    public ReviewResponseDTO (String review) {
        this.review = review;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
