package com.epam.rd.november2017.vlasenko.entity;

public class Order {
    private Integer orderId;
    private Integer userId;
    private Integer bookId;
    private String received;
    private String planedReturn;
    private String returned;
    private Integer penalty;
    private Status status;
    private Integer bookCount;

    public enum Status {
        NEW,
        CANCELED,
        RECEIVED,
        RETURNED
    }

    public Order(Integer userId, Integer bookId, String received, String planedReturn,
                 String returned, Integer penalty, Status status, Integer bookCount) {

        this(userId, bookId, status, bookCount);
        this.received = received;
        this.planedReturn = planedReturn;
        this.returned = returned;
        this.penalty = penalty;
    }

    public Order(Integer userId, Integer bookId, Status status, Integer bookCount) {
        this.userId = userId;
        this.bookId = bookId;
        this.status = status;
        this.bookCount = bookCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        return orderId.equals(order.orderId);
    }

    @Override
    public int hashCode() {
        return orderId.hashCode();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getPlanedReturn() {
        return planedReturn;
    }

    public void setPlanedReturn(String planedReturn) {
        this.planedReturn = planedReturn;
    }

    public String getReturned() {
        return returned;
    }

    public void setReturned(String returned) {
        this.returned = returned;
    }

    public Integer getPenalty() {
        return penalty;
    }

    public void setPenalty(Integer penalty) {
        this.penalty = penalty;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }
}
