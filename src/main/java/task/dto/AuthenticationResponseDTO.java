package task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponseDTO {
    @JsonProperty("token")
    private String token;

    public AuthenticationResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
