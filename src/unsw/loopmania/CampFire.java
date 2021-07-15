package unsw.loopmania;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.StaticEntity;

import java.util.ArrayList;

public class CampFire extends Building{
    private Character character;
    private LoopManiaWorld world;
    private ArrayList<StaticEntity> entities;
    private int radius;

    public CampFire(SimpleIntegerProperty x, SimpleIntegerProperty y, int radius){
        super(x, y);
        this.radius = radius;
        this.entities = setEntities(x, y);
    }

    public int getRadius(){
        return this.getRadius();
    }

    public void setEntities(SimpleIntegerProperty x, SimpleIntegerProperty y){
        ArrayList<StaticEntity> a = new ArrayList<>();
        int r = this.getRadius();
        for(int x = this.getX().get() - r;x <= this.getX().get() + r; x++){
            for(int y = this.getY().get() - r;y <= this.getY().get() + r; y++){
                if(x*x + y*y <= r*r){
                    StaticEntity b = new StaticEntity(x,y);
                    a.add(b);
                }
            }
        }
        this.entities = b;
    }

    public ArrayList<StaticEntity> getEntities(){
        return this.entities;
    }

    public void buffCharacter(LoopManiaWorld world){
        StaticEntity c = new StaticEntity(world.getCharacter().getX(), world.getCharacter().getY());
        if(this.getEntities.contains(c){
            world.getCharacter().setDamage(world.getCharacter().getDamage()*2);
        }
    }
}
