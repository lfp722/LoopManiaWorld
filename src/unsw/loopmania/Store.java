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
        int cost = equipment.getValueInGold();
        int gold = world.getCharacter().getGold();
        if(gold > cost){
            world.getCharacter().setGold(gold - cost);
            world.getCharacter().getItems(item);
        }
        else if(gold < cost){
            throw new Error("Not Enough Gold")
        }
    }  

    public void upgrade(Character character, Equipment equipment, LoopManiaWorld world){
        int cost = equipment.getLevelUpPrice();
        int gold = world.getCharacter().getGold();
        if(gold > cost){
            world.getCharacter().setGold(gold - cost);
            equipment.setLevel();
        }
        else if(gold < cost){
            throw new Error("Not Enough Gold")
        }
    }
}
