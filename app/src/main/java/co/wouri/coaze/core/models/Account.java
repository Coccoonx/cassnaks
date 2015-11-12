package co.wouri.coaze.core.models;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;


@Data
public class Account implements Serializable {
    private String name;
    private String token;
    private String address;
    private double balance;


    private String id;
    private
    @NotEmpty
    String deviceId;
    private
    @NotEmpty
    String firstName;
    private
    @NotEmpty
    String lastName;
    private
    @NotEmpty
    String password;
    private
    @NotEmpty
    String email;
    private
    @NotEmpty
    String phoneNumber;
    private String state;
    private AccountStatus status;
    private String socialSecurityNumber;
    private
    @NotEmpty
    String city, country;
    private Float loanLimit = 200.0f;
    private String preferedSourceCurrency;
    private String preferedTargetCurrency;


    public Account() {
        id = UUID.randomUUID().toString();
    }

}
