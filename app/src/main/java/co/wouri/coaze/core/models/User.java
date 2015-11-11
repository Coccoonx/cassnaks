package co.wouri.coaze.core.models;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;


@Data
public class User implements Serializable {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String sex;
    private String phoneNumber;
    private Byte[] userPicture;
    private String userName;
    private String password;
    private String token;
    private String currency;
    private String country;

    public User() {
        userId = UUID.randomUUID().toString();
    }

}
