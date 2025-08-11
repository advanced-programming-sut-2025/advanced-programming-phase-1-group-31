package io.github.some_example_name.model;

public class Reactions {
    private final String path;
    private final Integer number;
    private long time;

    public Reactions(String path, Integer number){
        this.path = path;
        this.number = number;
        this.time = System.currentTimeMillis();
    }

    public String getPath() {
        return path;
    }

    public Integer getNumber() {
        return number;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time){
        this.time = time;
    }
}
