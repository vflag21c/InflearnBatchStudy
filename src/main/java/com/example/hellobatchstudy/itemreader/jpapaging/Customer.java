package com.example.hellobatchstudy.itemreader.jpapaging;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
@ToString
public class Customer {

	@Id
	@GeneratedValue
	private Long Id;
	private String username;
	private int age;

	@OneToOne(mappedBy = "customer")
	private Address address;
}