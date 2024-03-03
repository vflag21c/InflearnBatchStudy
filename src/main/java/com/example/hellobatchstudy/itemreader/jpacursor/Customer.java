package com.example.hellobatchstudy.itemreader.jpacursor;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String birthdate;
}
