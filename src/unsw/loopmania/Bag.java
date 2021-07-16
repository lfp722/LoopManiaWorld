package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.items.*;

public class Bag {
    private List<Entity> items;
    private int limit;
    private Character ch;
    private int discount = 5;

    public Bag() {
        this.items = new ArrayList<Entity>();
        this.limit = 16;
    }

    public void add(Entity item) {
        if (items.size() == limit) {
            Entity popped = items.remove(0);
            items.add(item);
            ch.addGold(popped.getValueInGold()/discount);
            ch.addExp(popped.getValueInGold()/discount);
        }
        else {
            items.add(item);
        }
    }

    public void remove(Entity item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return this.items;
    }

    public Item get(int index) {
        return items.get(index);
    }
}
