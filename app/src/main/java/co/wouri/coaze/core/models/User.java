package co.wouri.coaze.core.models;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;


@Data
public class User implements Serializable {
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String token;
    private String country;
    private String address;
    private String city;
    private double balance;

    public User() {
        userId = UUID.randomUUID().toString();
    }

}
