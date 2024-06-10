package com.example.hellobatchstudy.itemreader.composite;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import java.util.Iterator;
import java.util.List;

public class CompositeItemReader<T> implements ItemStreamReader<T> {

    private final List<ItemStreamReader<T>> delegates;

    private final Iterator<ItemStreamReader<T>> delegatesIterator;

    private ItemStreamReader<T> currentDelegate;

    public CompositeItemReader(List<ItemStreamReader<T>> delegates) {
        this.delegates = delegates;
        this.delegatesIterator = this.delegates.iterator();
        this.currentDelegate = this.delegatesIterator.hasNext() ? this.delegatesIterator.next() : null;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        for (ItemStreamReader<T> delegate : delegates) {
            delegate.open(executionContext);
        }
    }

    @Override
    public T read() throws Exception {
        if (this.currentDelegate == null) {
            return null;
        }
        T item = currentDelegate.read();
        if (item == null) {
            currentDelegate = this.delegatesIterator.hasNext() ? this.delegatesIterator.next() : null;
            return read();
        }
        return item;
    }

    @Override
    public void close() throws ItemStreamException {
        for (ItemStreamReader<T> delegate : delegates) {
            delegate.close();
        }
    }
}