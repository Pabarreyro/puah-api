package models;

import java.util.Objects;

public class Community {
    private String name;
    private String type;
    private int id;

    public Community(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Community community = (Community) o;
        return id == community.id &&
                Objects.equals(name, community.name) &&
                Objects.equals(type, community.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, id);
    }
}
