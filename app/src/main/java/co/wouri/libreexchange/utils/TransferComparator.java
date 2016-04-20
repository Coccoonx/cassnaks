package co.wouri.libreexchange.utils;

import java.io.Serializable;
import java.util.Comparator;

import co.wouri.libreexchange.core.models.Transfer;

/**
 * Created by lyonnel on 19/04/16.
 */
public class TransferComparator implements Comparator<Transfer>, Serializable {
    @Override
    public int compare(Transfer lhs, Transfer rhs) {
        if (lhs.getCreationDate().after(rhs.getCreationDate())) {
            return -1;
        } else if (lhs.getCreationDate().before(rhs.getCreationDate())) {
            return 1;
        } else
            return 0;
    }
}
