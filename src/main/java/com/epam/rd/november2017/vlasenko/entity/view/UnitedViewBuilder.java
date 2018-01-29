package com.epam.rd.november2017.vlasenko.entity.view;

import com.epam.rd.november2017.vlasenko.entity.Order;

public class UnitedViewBuilder {
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

    public UnitedViewBuilder buildUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public UnitedViewBuilder buildOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public UnitedViewBuilder buildBookId(Integer bookId) {
        this.bookId = bookId;
        return this;
    }

    public UnitedViewBuilder buildBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public UnitedViewBuilder buildAuthor(String author) {
        this.author = author;
        return this;
    }

    public UnitedViewBuilder buildPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public UnitedViewBuilder buildPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }

    public UnitedViewBuilder buildTotalBookCount(int totalBookCount) {
        this.totalBookCount = totalBookCount;
        return this;
    }

    public UnitedViewBuilder buildOrderBookCount(int orderBookCount) {
        this.orderBookCount = orderBookCount;
        return this;
    }

    public UnitedViewBuilder buildReceived(String received) {
        this.received = received;
        return this;
    }

    public UnitedViewBuilder buildPlanedReturn(String planedReturn) {
        this.planedReturn = planedReturn;
        return this;
    }

    public UnitedViewBuilder buildReturned(String returned) {
        this.returned = returned;
        return this;
    }

    public UnitedViewBuilder buildPenalty(Integer penalty) {
        this.penalty = penalty;
        return this;
    }

    public UnitedViewBuilder buildStatus(Order.Status status) {
        this.status = status;
        return this;
    }

    public UnitedViewBuilder buildEmail(String email) {
        this.email = email;
        return this;
    }

    public UnitedViewBuilder buildNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public UnitedViewBuilder buildFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UnitedViewBuilder buildLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UnitedViewBuilder buildContact(String contact) {
        this.contact = contact;
        return this;
    }
    public UnitedView build() {
        UnitedView view = new UnitedView();
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
