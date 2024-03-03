package com.example.hellobatchstudy.itemwriter.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Customer2 {
    @Id
    private long id;
    private int age;
    private String username;
}
