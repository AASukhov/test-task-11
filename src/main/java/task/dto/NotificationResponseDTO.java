package task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationResponseDTO {
    @JsonProperty("header")
    private String header;

    @JsonProperty("text")
    private String text;

    public NotificationResponseDTO(String header, String text) {
        this.header = header;
        this.text = text;
    }

    public NotificationResponseDTO(){}

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

}
