package com.practice.phonepe.questions.vendingmachine.entities;

import com.practice.phonepe.questions.vendingmachine.exception.ChangeNotAvailableException;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * The machine's physical cash float. Coins/notes a user inserts are deposited here and become
 * available to hand back out as change to the next customer.
 */
public class CashBox {

    private final Map<Denomination, Integer> countByDenomination = new EnumMap<>(Denomination.class);

    public void deposit(Denomination denomination) {
        deposit(denomination, 1);
    }

    public void deposit(Denomination denomination, int count) {
        countByDenomination.merge(denomination, count, Integer::sum);
    }

    /** Greedily breaks {@code amount} into the largest denominations currently on hand. */
    public List<Denomination> makeChange(int amount) {
        List<Denomination> change = new ArrayList<>();
        int remaining = amount;
        Denomination[] values = Denomination.values();
        for (int i = values.length - 1; i >= 0 && remaining > 0; i--) {
            Denomination denomination = values[i];
            int available = countByDenomination.getOrDefault(denomination, 0);
            int used = Math.min(available, remaining / denomination.getValue());
            for (int j = 0; j < used; j++) {
                change.add(denomination);
            }
            remaining -= used * denomination.getValue();
        }
        if (remaining > 0) {
            throw new ChangeNotAvailableException("Cannot make change for " + amount + " with the cash currently in the machine");
        }
        change.forEach(denomination -> countByDenomination.merge(denomination, -1, Integer::sum));
        return change;
    }

    public Map<Denomination, Integer> collectAll() {
        Map<Denomination, Integer> snapshot = new EnumMap<>(countByDenomination);
        countByDenomination.clear();
        return snapshot;
    }

    public int getTotalValue() {
        int total = 0;
        for (Map.Entry<Denomination, Integer> entry : countByDenomination.entrySet()) {
            total += entry.getKey().getValue() * entry.getValue();
        }
        return total;
    }
}
