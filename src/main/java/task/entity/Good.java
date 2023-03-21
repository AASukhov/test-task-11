package task.entity;

import javax.persistence.*;

@Entity
@Table(name = "goods")
public class Good {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "organization_id")
    private Organization organization;

    private double price;
    private int amount;

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "discount_id")
    private Discount discount;
    private String keywords;
    private String features;
    private double estimation;

    public Good(int id, String name, String description, Organization organization, double price, int amount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.organization = organization;
        this.price = price;
        this.amount = amount;
    }

    public Good(String name, String description, Organization organization, double price, int amount) {
        this.name = name;
        this.description = description;
        this.organization = organization;
        this.price = price;
        this.amount = amount;
    }

    public Good(String name, Organization organization, double price, int amount) {
        this.name = name;
        this.organization = organization;
        this.price = price;
        this.amount = amount;
    }

    public Good (){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
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

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public double getEstimation() {
        return estimation;
    }

    public void setEstimation(double estimation) {
        this.estimation = estimation;
    }
}
