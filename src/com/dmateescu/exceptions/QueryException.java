package com.dmateescu.exceptions;

public class QueryException extends Throwable {
    private String query;

    public QueryException(String query){
        this.query = query;
    }

    @Override
    public String getMessage() {
        return "Query "+query+" failed!";
    }
}
