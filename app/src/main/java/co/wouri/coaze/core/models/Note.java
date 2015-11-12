package co.wouri.coaze.core.models;

import java.util.Date;

import lombok.Data;

@Data
public class Note {
    private Date date;
    private String message;
    private String from, to;
}
