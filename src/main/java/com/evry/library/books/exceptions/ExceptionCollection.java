package com.evry.library.books.exceptions;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExceptionCollection {
    private boolean expectException;
    private List<RuntimeException> exceptions = new ArrayList<>();

    public void expectException() {
        expectException = true;
    }

    public void add(RuntimeException e) {
        if (!expectException) {
            throw e;
        }
        exceptions.add(e);
    }

    public List<RuntimeException> getExceptions() {
        return exceptions;
    }

    public void clearExceptions() {
        exceptions.clear();
    }

}
