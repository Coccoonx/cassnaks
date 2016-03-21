package co.wouri.libreexchange.core.models;


import lombok.Data;

@Data
public class Reference {
    private String id;
    private String phoneNumber, email;
    private String firstName;
    private String lastName;
    private String accountId;
}
