package co.wouri.coaze.core.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data
public class Account implements Serializable {
    private User user = new User();
    private List<Transfer> transfers = new ArrayList<>();
    private List<Recipient> recipients = new ArrayList<>();

}
