package com.example.salildas.vorti;

import java.util.Date;

public class ReceiveRequest {

    private int phone;
    private String startDate;
    private String endDate;
    private double totalPrice;
    private int dayCount;
    private String regularFullName;
    private String reqDate;

    public ReceiveRequest(){}

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getDayCount() {
        return dayCount;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }

    public String getRegularFullName() {
        return regularFullName;
    }

    public void setRegularFullName(String regularFullName) {
        this.regularFullName = regularFullName;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }
}
