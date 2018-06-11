package models;

import java.util.Objects;

public class Region {

    private String name;
    private int id;

    public Region(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        Region region = (Region) o;
        return id == region.id &&
                Objects.equals(name, region.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
