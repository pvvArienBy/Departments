package by.it_academy.jd2.Mk_JD2_98_23.core.dto;

public class DepartmentShortDTO {
    private long id;
    private String name;

    public DepartmentShortDTO() {
    }

    public DepartmentShortDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
