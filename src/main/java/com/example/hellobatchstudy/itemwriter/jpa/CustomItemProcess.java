package com.example.hellobatchstudy.itemwriter.jpa;

import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcess implements ItemProcessor<Customer, Customer2> {

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Customer2 process(Customer customer) throws Exception {

        Customer2 customer2 = new Customer2();
        customer2.setId(customer.getId());
        customer2.setAge(customer.getAge());
        customer2.setUsername(customer.getName());

        return customer2;

//        return modelMapper.map(customer, Customer2.class);
    }
}
