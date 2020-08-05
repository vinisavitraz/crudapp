package com.example.crudapp.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String fullName;

    private Integer age;

    private String phone;

    private String cep;

    private String street;

    private Integer streetNumber;

    private String city;

    private String state;

    public Contact() {
    }

    public Contact(Integer id, String fullName, Integer age, String phone, String cep, String street, Integer streetNumber, String city, String state) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.phone = phone;
        this.cep = cep;
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
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

    @Override
    public String toString() {
        return fullName + " - " + phone;
    }
}
