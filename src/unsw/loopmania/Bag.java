package unsw.loopmania;

import java.util.List;

public class Bag {
    private List<Item> items;
    private int limit;
    private Character ch;

    public bag() {

    }

    public add(Item item) {
        if (items.size() == limit) {
            Item popped = items.remove(0);
            items.add(item);
            ch.addGold(0.2*popped.getValue());
            ch.addExp(0.2*popped.getValue());
        }
        else {
            items.add(item);
        }
    }

    public drop(Item item) {
        items.remove(item);
    }
}
