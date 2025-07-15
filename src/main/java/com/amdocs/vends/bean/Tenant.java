package com.amdocs.vends.bean;

public class Tenant {
    private Integer userId;
    private Integer propertyId;
    private Float rent;
    private Boolean isCurrentlyLivingThere;

    public Tenant() {}

    public Tenant(Integer userId, Integer propertyId, Float rent, Boolean isCurrentlyLivingThere) {
        this.userId = userId;
        this.propertyId = propertyId;
        this.rent = rent;
        this.isCurrentlyLivingThere = isCurrentlyLivingThere;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public Float getRent() {
        return rent;
    }

    public void setRent(Float rent) {
        this.rent = rent;
    }

    public Boolean getCurrentlyLivingThere() {
        return isCurrentlyLivingThere;
    }

    public void setCurrentlyLivingThere(Boolean currentlyLivingThere) {
        isCurrentlyLivingThere = currentlyLivingThere;
    }
}
