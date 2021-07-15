package unsw.loopmania;

public class Barrack extends Building{

    public Barrack(SimpleIntegerProperty x, SimpleIntegerProperty y){
        super(x, y);
    }

    public void addSoldier(LoopManiaWorld world){
        if(world.getCharacter().getX().equals(this.getX()) && world.getCharacter().getY().equals(this.getY())){
            world.getCharacter().addSoldier();
        }
    }
}
