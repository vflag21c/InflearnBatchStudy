package com.example.hellobatchstudy.itemstream;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;

import java.util.List;

public class CustomItemStreamWriter implements ItemStreamWriter<String> {

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        System.out.println("open");
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {

        System.out.println("update");
    }

    @Override
    public void close() throws ItemStreamException {

        System.out.println("close");
    }

    @Override
    public void write(List<? extends String> list) throws Exception {
        list.forEach(i -> System.out.println("item = " + i));
    }
}
