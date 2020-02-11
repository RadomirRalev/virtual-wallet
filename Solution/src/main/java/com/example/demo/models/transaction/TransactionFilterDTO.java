package com.example.demo.models.transaction;

public class TransactionFilterDTO {
    private String startDate;
    private String endDate;
    private String searchRecipient;
    private String direction;

    public TransactionFilterDTO() {
    }

    public TransactionFilterDTO(String startDate, String endDate, String searchRecipient, String direction) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.searchRecipient = searchRecipient;
        this.direction = direction;
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

    public String getSearchRecipient() {
        return searchRecipient;
    }

    public void setSearchRecipient(String searchRecipient) {
        this.searchRecipient = searchRecipient;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
