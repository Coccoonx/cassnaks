package co.wouri.coaze.core.models;

import lombok.Data;

/**
 * Created by prisca on 10/11/15.
 */
@Data
public class User {
    private String userid;
    private String firstName;
    private String lastName;
    private String email;
    private String sex;
    private String phoneNumber;
    private Byte [] userPicture;
    private String userName;
    private String password;
    private String token;
    private String currency;
    private String country;

}
