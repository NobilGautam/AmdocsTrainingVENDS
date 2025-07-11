package com.amdocs.vends.bean;

import java.sql.Date;

public class LeaveRequest {
    Integer userId;
    Date requestDate;
    String status;
    Date approvedOn;

    public LeaveRequest() {}

    public LeaveRequest(Integer userId, Date requestDate, String status, Date approvedOn) {
        this.userId = userId;
        this.requestDate = requestDate;
        this.status = status;
        this.approvedOn = approvedOn;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Date getRequestDate() {
        return requestDate;
    }
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getApprovedOn() {
        return approvedOn;
    }
    public void setApprovedOn(Date approvedOn) {
        this.approvedOn = approvedOn;
    }
}
