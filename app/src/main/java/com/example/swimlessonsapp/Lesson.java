package com.example.swimlessonsapp;

public class Lesson {
    private long id;
    private String date;
    private String time;
    private String coach;
    private String child;

    public Lesson(long id, String date, String time, String coach, String child) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.coach = coach;
        this.child = child;
    }

    // Геттеры
    public long getId() { return id; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getCoach() { return coach; }
    public String getChild() { return child; }
}