package co.wouri.coaze.core.models;

import lombok.Data;

/**
 * Created by prisca on 10/11/15.
 */
@Data
public class Recipient {
    private String recipientId;
    private String  email, city, state, country, address, phoneNumber;
    private String firstName;
    private String lastName;
    private String accountId;
    private Byte [] userPicture;
    private String currency;
}
