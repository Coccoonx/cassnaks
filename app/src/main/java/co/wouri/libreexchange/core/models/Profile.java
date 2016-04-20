package co.wouri.libreexchange.core.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import co.wouri.libreexchange.utils.TransferComparator;
import lombok.Data;


@Data
public class Profile implements Serializable {
    private Account account = new Account();
    private TreeSet<Transfer> transfers = new TreeSet<>(new TransferComparator()) ;
    private List<Recipient> recipients = new ArrayList<>();

}
