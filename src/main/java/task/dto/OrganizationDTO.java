package task.dto;

public class OrganizationDTO {
    private String name;
    private String description;
    private String logo;

    public OrganizationDTO (String name, String description, String logo) {
        this.name = name;
        this.description = description;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLogo() {
        return logo;
    }
}
