package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.items.*;

public class Bag {
    private List<Item> items;
    private int limit;
    private Character ch;
    private int discount = 5;

    public Bag() {
        this.items = new ArrayList<Item>();
        this.limit = 16;
    }

    /**
     * add item into bag, remove oldest and ge refund when full
     * @param item
     */
    public void add(Item item) {
        if (items.size() == limit) {
            Item popped = items.remove(0);
            items.add(item);
            ch.addGold(popped.getValueInGold()/discount);
            ch.addExp(popped.getValueInGold()/discount);
        }
        else {
            items.add(item);
        }
    }

    /**
     * remove an item from the list
     * @param item
     */
    public void remove(Item item) {
        items.remove(item);
    }

    /**
     * get item list
     * @return List<Item>
     */
    public List<Item> getItems() {
        return this.items;
    }

    /**
     * get particular item in a list
     * @param index
     * @return
     */
    public Item get(int index) {
        return items.get(index);
    }
}
