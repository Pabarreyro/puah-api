package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private String name;
    private String address;
    private String zip;
    private String phone;
    private String website;
    private String email;
    private List<Service> services;
    private List<Community> communities;
    private List<Region> regions;
    private int id;

    public Organization(String name, String address, String zip, String phone) {
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.website = "website unavailable";
        this.email = "email unavailable";
        services = new ArrayList<>();
        communities = new ArrayList<>();
        regions = new ArrayList<>();
    }

    public Organization(String name, String address, String zip, String phone, String website, String email) {
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.website = website;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(List<Community> communities) {
        this.communities = communities;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(zip, that.zip) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(website, that.website) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, zip, phone, website, email, id);
    }
}
