package unsw.loopmania.items;
import unsw.loopmania.*;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;


public class Item extends StaticEntity {
    
    protected SimpleIntegerProperty ValueInGold;
    protected boolean isRare;
    protected int rareType;

    public static int HEALTH = 0;
    public static int ATTACK = 1;
    public static int DEFENCE = 2;
    public static int DEFAULT = -1;

    private SecondEffect se;
    private int secondValue = 0;

    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x, y);
        ValueInGold = new SimpleIntegerProperty();
        this.ValueInGold.set(0);
        isRare = false;
        rareType = DEFAULT;
    }

    /**
     * getter
     * @return the value of item in gold
     * i.e. how much gold will be obtained
     */
    public int getValueInGold() {
        return this.ValueInGold.get();
    }

    /**
     * setter
     * @param ValueInGold
     */
    public void setValueInGold(int ValueInGold) {
        this.ValueInGold.set(ValueInGold);
    }

    public JSONObject toJSON() {
        return null;
    }


    public void secondEffect(LoopManiaWorld world, Enemy e) {
        se.secondEffect(world, e);
    }

    public void setSe(SecondEffect se) {
        this.se = se;
    }

    public void setSecondValue(int value) {
        secondValue = value;
    }

    public int getSecondValue() {
        return secondValue;
    }

    public boolean isRare() {
        return isRare;
    }

    public int getSecondType() {
        return rareType;
    }

    public void setRareType(int type) {
        this.rareType = type;
    }
}