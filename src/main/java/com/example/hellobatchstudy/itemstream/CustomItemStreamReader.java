package com.example.hellobatchstudy.itemstream;

import org.springframework.batch.item.*;

import java.util.List;

public class CustomItemStreamReader implements ItemStreamReader<String> {

    private final List<String> items;
    private int index = -1;
    private boolean restart = false;

    public CustomItemStreamReader(List<String> items) {
        this.items = items;
        this.index = 0;
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String item = null;

        if (index < items.size()) {
            item = items.get(index);
            this.index++;
        }

        if(this.index == 6 && !restart) {
            throw new RuntimeException("Restart is required");
        }

        return item;
    }

    @Override
    //최초 1회 호출
    public void open(ExecutionContext executionContext) throws ItemStreamException {

        if(executionContext.containsKey("index")) {
            index = executionContext.getInt("index");
            restart = true;
        } else {
            index = 0;
            executionContext.put("index", index);
        }

    }

    @Override
    // 현재 상태 저장 - chunk size 만큼 반복
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.put("index", index);
    }

    @Override
    //종료 시 1회
    public void close() throws ItemStreamException {
        System.out.println("close");
    }
}
