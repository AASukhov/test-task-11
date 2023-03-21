package task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoodResponseDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private double price;

    @JsonProperty("amount")
    private int amount;

    public GoodResponseDTO(String name, double price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public GoodResponseDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
