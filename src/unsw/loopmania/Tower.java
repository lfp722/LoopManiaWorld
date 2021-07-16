package unsw.loopmania;
import java.util.List;

import unsw.loopmania.BasicEnemy;
import unsw.loopmania.StaticEntity;

import java.util.ArrayList;

public class Tower extends Building{
    private LoopManiaWorld world;
    private ArrayList<StaticEntity> entities;

    public Tower(SimpleIntegerProperty x, SimpleIntegerProperty y, int damage, int radius){
        super(x, y);
        this.damage = damage;
        this.radius = radius;
        this.entites = setEntities(x,y);
    }

    public void setEntities(SimpleIntegerProperty x, SimpleIntegerProperty y){
        ArrayList<StaticEntity> a = new ArrayList<>();
        int r = this.getRadius();
        for(int x = this.getX() - r;x <= this.getX() + r; x++){
            for(int y = this.getY() - r;y <= this.getY() + r; y++){
                if(x*x + y*y <= r*r){
                    StaticEntity b = new StaticEntity(x,y);
                    a.add(b);
                }
            }
        }
        this.entities = b;
    }

    public int getRadius(){
        return this.radius;
    }

    public int getDamage(){
        return this.damage;
    }

    public ArrayList<StaticEntity> getEntities(){
        return this.entities;
    }

    public void attackIfInRadius(LoopManiaWorld world){
        for(BasicEnemy a: world.getEnemies()){
            StaticEntity b = new StaticEntity(a.getX(), a.getY());
            if(this.getEntities().contains(b)){
                b.setHealth(b.underAttack(this.getDamage()));
            }
        }
    }

}
