package unsw.loopmania;

import org.json.JSONObject;


public class Store {
    private JSONObject Inventory = new JSONObject();
    private Character character;
    private Item item;
    private LoopManiaWorld world;

    public Store(LoopManiaWorld world){
        this.Inventory = setInventory(world);
    }

    public void setInventory(LoopManiaWorld world){
        world.getStoreItems();
    }

    public void sell(Character character, Item item, LoopManiaWorld world){
        world.getCharacter().getItems(item);
    }  

    public void upgrade(Character character, Item item){
        item.increaseLevel();
    }
}
