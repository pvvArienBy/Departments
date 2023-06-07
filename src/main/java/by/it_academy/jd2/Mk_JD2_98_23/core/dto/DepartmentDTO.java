package by.it_academy.jd2.Mk_JD2_98_23.core.dto;

import java.util.List;

public class DepartmentDTO {
    private long id;
    private String name;
    private DepartmentShortDTO parent;
    private List<DepartmentShortDTO> children;
    private String phoneNumber;
    private LocationDTO location;

    public DepartmentDTO() {
    }

    public DepartmentDTO(long id, String name, DepartmentShortDTO parent, List<DepartmentShortDTO> children, String phoneNumber, LocationDTO location) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.children = children;
        this.phoneNumber = phoneNumber;
        this.location = location;
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

    public DepartmentShortDTO getParent() {
        return parent;
    }

    public void setParent(DepartmentShortDTO parent) {
        this.parent = parent;
    }

    public List<DepartmentShortDTO> getChildren() {
        return children;
    }

    public void setChildren(List<DepartmentShortDTO> children) {
        this.children = children;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }
}
