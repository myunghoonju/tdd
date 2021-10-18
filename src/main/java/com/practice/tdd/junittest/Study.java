package com.practice.tdd.junittest;

public class Study {
    private int limit;
    private String name;

    public Study(int limit) {
        this.limit = limit;
    }

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Study{" +
                "limit=" + limit +
                ", name='" + name + '\'' +
                '}';
    }
}
