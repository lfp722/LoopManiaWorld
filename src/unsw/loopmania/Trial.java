package unsw.loopmania;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleIntegerProperty;

import java.lang.Math;

import javax.swing.plaf.synth.SynthStyle;

public class Trial {
    private SimpleIntegerProperty level;
    private SimpleIntegerProperty experience;
    private SimpleIntegerProperty next_expr;
    private SimpleIntegerProperty shit;


    public Trial() {
        shit = new SimpleIntegerProperty();
        level = new SimpleIntegerProperty();
        level.set(0);
        experience = new SimpleIntegerProperty();
        experience.set(0);
        level.bind(Bindings.createDoubleBinding(()->Math.sqrt((double)experience.divide(1000).get()), experience));
        experience.set(5000);
        next_expr = new SimpleIntegerProperty();
        next_expr.bind(Bindings.createDoubleBinding(()->Math.pow(level.get()+1,2)*1000, level));
        shit.bind(Bindings.createIntegerBinding(()->level.get()*2+experience.get()/2000, level, experience));
    }

    public static void main(String args[]) {
        Trial a = new Trial();
        System.out.println(a.level.get());
        System.out.println(a.next_expr.get());
        System.out.println(a.shit.get());
    }
    
}
