package by.it_academy.jd2.Mk_JD2_98_23.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DepartmentCreateDTO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("parent")
    private long parent;
    @JsonProperty("children")
    private List<Long> children;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("location")
    private Long location;


    public DepartmentCreateDTO() {
    }

    public DepartmentCreateDTO(String name, long parent, List<Long> children, String phoneNumber, Long location) {
        this.name = name;
        this.parent = parent;
        this.children = children;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParent() {
        return parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }

    public List<Long> getChildren() {
        return children;
    }

    public void setChildren(List<Long> children) {
        this.children = children;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }
}
