package com.luv2code.springboot.thymeleafdemo.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Details {

    private String uuid;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String first_name;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String last_name;

    private String street = "";

    private String address = "";

    private String city = "";

    private String state = "";

    private String email = "";

    private String phone = "";

    public Details(String first_name, String last_name, String street, String address, String city, String state,
            String email, String phone) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.street = street;
        this.address = address;
        this.city = city;
        this.state = state;
        this.email = email;
        this.phone = phone;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getStreet() {
        return street;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

}
