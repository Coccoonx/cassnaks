package co.wouri.coaze.core.models;

import java.util.UUID;

import lombok.Data;

@Data
public class Recipient {
    private String recipientId;
    private String email;
    private String city;
    private String state;
    private String country;
    private String address;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String accountId;
    private byte[] userPicture;
    private String currency;
    private int image;


    public Recipient(String fName, String lName, String adress) {
        firstName = fName;
        lastName = lName;
        address = adress;
        recipientId = UUID.randomUUID().toString();

    }

    public Recipient(int image, String lName, String adress) {
        this.image = image;
        lastName = lName;
        address = adress;
        recipientId = UUID.randomUUID().toString();

    }

    public Recipient() {
        recipientId = UUID.randomUUID().toString();
    }
}
