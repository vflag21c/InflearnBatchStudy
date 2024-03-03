package com.example.hellobatchstudy.itemreader_itemprocessor_itemwriter;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class CustomItemWriter implements ItemWriter<String> {
    @Override
    public void write(List<? extends String> items) throws Exception {
        items.forEach(item -> System.out.println("item = " + item));
    }
}
