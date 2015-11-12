package co.wouri.coaze.core.models;


import lombok.Data;

@Data
public class Document {
    private String id;
    private String documentName, base64Content, accountId;
}