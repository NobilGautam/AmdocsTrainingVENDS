package com.amdocs.vends.bean;

public class Property {
    Integer propertyId;
    Integer userId;
    String propertyType;
    String address;
    String city;
    String state;
    String status;
    Float rent;

    public Property() {}
    public Property(Integer userId, String propertyType, String address, String city, String state, String status) {
        this.userId = userId;
        this.propertyType = propertyType;
        this.address = address;
        this.city = city;
        this.state = state;
        this.status = status;
    }
    public Integer getPropertyId() {
        return propertyId;
    }
    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getPropertyType() {
        return propertyType;
    }
    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Float getRent() {
        return rent;
    }
    public void setRent(Float rent) {
        this.rent = rent;
    }
}
