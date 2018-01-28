package com.epam.rd.november2017.vlasenko.service.order.view;

import com.epam.rd.november2017.vlasenko.entity.Order;

public class ViewBuilder {
    private Integer userId;
    private Integer orderId;
    private Integer bookId;
    private String bookName;
    private String author;
    private String publisher;
    private String publicationDate;
    private int totalBookCount;
    private int orderBookCount;
    private String received;
    private String planedReturn;
    private String returned;
    private Integer penalty;
    private Order.Status status;
    private String email;
    private String nickName;
    private String firstName;
    private String lastName;
    private String contact;

    public ViewBuilder buildUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public ViewBuilder buildOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public ViewBuilder buildBookId(Integer bookId) {
        this.bookId = bookId;
        return this;
    }

    public ViewBuilder buildBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public ViewBuilder buildAuthor(String author) {
        this.author = author;
        return this;
    }

    public ViewBuilder buildPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public ViewBuilder buildPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }

    public ViewBuilder buildTotalBookCount(int totalBookCount) {
        this.totalBookCount = totalBookCount;
        return this;
    }

    public ViewBuilder buildOrderBookCount(int orderBookCount) {
        this.orderBookCount = orderBookCount;
        return this;
    }

    public ViewBuilder buildReceived(String received) {
        this.received = received;
        return this;
    }

    public ViewBuilder buildPlanedReturn(String planedReturn) {
        this.planedReturn = planedReturn;
        return this;
    }

    public ViewBuilder buildReturned(String returned) {
        this.returned = returned;
        return this;
    }

    public ViewBuilder buildPenalty(Integer penalty) {
        this.penalty = penalty;
        return this;
    }

    public ViewBuilder buildStatus(Order.Status status) {
        this.status = status;
        return this;
    }

    public ViewBuilder buildEmail(String email) {
        this.email = email;
        return this;
    }

    public ViewBuilder buildNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public ViewBuilder buildFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ViewBuilder buildLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ViewBuilder buildContact(String contact) {
        this.contact = contact;
        return this;
    }
    public View build() {
        View view = new View();
        view.setOrderId(orderId);
        view.setBookId(bookId);
        view.setUserId(userId);
        view.setBookName(bookName);
        view.setAuthor(author);
        view.setPublisher(publisher);
        view.setPublicationDate(publicationDate);
        view.setTotalBookCount(totalBookCount);
        view.setOrderBookCount(orderBookCount);
        view.setReceived(received);
        view.setPlanedReturn(planedReturn);
        view.setReturned(returned);
        view.setPenalty(penalty);
        view.setStatus(status);
        view.setEmail(email);
        view.setNickName(nickName);
        view.setFirstName(firstName);
        view.setLastName(lastName);
        view.setContact(contact);
        return view;
    }
}
