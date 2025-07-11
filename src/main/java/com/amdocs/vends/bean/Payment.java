package com.amdocs.vends.bean;

import java.sql.Date;

public class Payment {
    Integer userId;
    Float rentPaid;
    Date dateOfPayment;
    String rentForMonth;
    Boolean approvedByAdmin;

    public Payment() {}

    public Payment(Integer userId, Float rentPaid, Date dateOfPayment, String rentForMonth, Boolean approvedByAdmin) {
        this.userId = userId;
        this.rentPaid = rentPaid;
        this.dateOfPayment = dateOfPayment;
        this.rentForMonth = rentForMonth;
        this.approvedByAdmin = approvedByAdmin;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Float getRentPaid() {
        return rentPaid;
    }
    public void setRentPaid(Float rentPaid) {
        this.rentPaid = rentPaid;
    }
    public Date getDateOfPayment() {
        return dateOfPayment;
    }
    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }
    public String getRentForMonth() {
        return rentForMonth;
    }
    public void setRentForMonth(String rentForMonth) {
        this.rentForMonth = rentForMonth;
    }
    public Boolean getApprovedByAdmin() {
        return approvedByAdmin;
    }
    public void setApprovedByAdmin(Boolean approvedByAdmin) {
        this.approvedByAdmin = approvedByAdmin;
    }
}
