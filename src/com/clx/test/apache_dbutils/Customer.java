package com.clx.test.apache_dbutils;

import java.sql.Blob;
import java.util.Date;

public class Customer {
    private int id;
    private String name;
    private String emails;
    private Date birth;
    private Blob pirture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Blob getPirture() {
        return pirture;
    }

    public void setPirture(Blob pirture) {
        this.pirture = pirture;
    }

    public Customer() {

    }

    public Customer(int id, String name, String emails, Date birth, Blob pirture) {
        this.id = id;
        this.name = name;
        this.emails = emails;
        this.birth = birth;
        this.pirture = pirture;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", emails='" + emails + '\'' +
                ", birth=" + birth +
                ", pirture=" + pirture +
                '}';
    }
}
