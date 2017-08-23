package com.example.liisandalex.fontyscompanion.models;

import lombok.Data;

/**
 * Created by prodromalex on 3/30/2017.
 */

@Data public class Schedule {

    private String room;
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
}
