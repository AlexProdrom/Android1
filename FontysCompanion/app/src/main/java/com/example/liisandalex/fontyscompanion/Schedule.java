package com.example.liisandalex.fontyscompanion;

/**
 * Created by prodromalex on 3/30/2017.
 */

public class Schedule {

    private String subject;
    private String teacher;
    private String start;
    private String end;

    public Schedule(String room, String subject, String teacher, String start, String end) {
        this.room = room;
        this.subject = subject;
        this.teacher = teacher;
        this.start = start;
        this.end = end;
    }

    private String room;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

}
