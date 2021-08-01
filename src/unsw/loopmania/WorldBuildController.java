package unsw.loopmania;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;

import org.javatuples.Pair;

public class WorldBuildController {
    public final static int NULL = -1;
    public final static int RIGHT = 0;
    public final static int DOWN = 1;
    public final static int UP = 2;
    public final static int LEFT = 3;

    public static int minWidth = 6;
    public static int minHeight = 6;
    private JSONObject config = new JSONObject();

    private int width;
    private int height;
    private List<String> path;
    private Pair<Integer, Integer> start;
    private List<Pair<Integer, Integer>> generated;

    private Graph<Pair<Integer, Integer>> graph;

    private MenuSwitcher menuSwitcher;

    public void setMenuSwitcher(MenuSwitcher switcher) {
        menuSwitcher = switcher;
    }

    @FXML
    public void switchToMenu() {
        menuSwitcher.switchMenu();
    }


    public void init() {
        width = new Random().nextInt(10)+minWidth;
        height = new Random().nextInt(10)+minHeight;
        path = new ArrayList<>();

        start = new Pair(0,0);
    }





    public boolean withinG(Pair<Integer, Integer> a) {
        return a.getValue0() >= 0 && a.getValue0() < width && a.getValue1() >= 0 && a.getValue1() < height;
    }

    public void graph() {
        init();
        graph = new Graph<Pair<Integer, Integer>> ();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Pair<Integer, Integer> node = new Pair(i,j);
                graph.addNode(node);
                Pair<Integer, Integer> neigh1 = new Pair(i+1,j);
                if (withinG(neigh1)) {
                    graph.addConnection(node, neigh1);
                }
                Pair<Integer, Integer> neigh2 = new Pair(i-1,j);
                if (withinG(neigh2)) {
                    graph.addConnection(node, neigh2);
                }
                Pair<Integer, Integer> neigh3 = new Pair(i,j+1);
                if (withinG(neigh3)) {
                    graph.addConnection(node, neigh3);
                }
                Pair<Integer, Integer> neigh4 = new Pair(i,j-1);
                if (withinG(neigh4)) {
                    graph.addConnection(node, neigh4);
                }
            }
        }
    }

    @FXML
    public void generatePath() {
        generated = null;
        while (generated == null || generated.isEmpty()) {
            graph();
            DFS<Pair<Integer, Integer>> dfs = graph.depthFirstIterator(start);
            generated =  dfs.findPath();
        }

        for (int i = 0; i < generated.size()-1; i++) {
            Pair<Integer, Integer> curp = generated.get(i);
            Pair<Integer, Integer> nextp = generated.get(i+1);
            if (nextp.getValue0() > curp.getValue0()) {
                path.add("RIGHT");
            }
            else if (nextp.getValue0() < curp.getValue0()) {
                path.add("LEFT");
            }
            else if (nextp.getValue1() < curp.getValue1()) {
                path.add("UP");
            }
            else if (nextp.getValue1() > curp.getValue1()) {
                path.add("DOWN");
            }
        }

        config.put("width", width);
        config.put("height", height);

        JSONArray ri = new JSONArray();
        config.put("rare_items", ri);

        JSONObject goal = new JSONObject();
        goal.put("goal", "gold");
        goal.put("quantity", 10000);
        config.put("goal-condition", goal);

        JSONArray en = new JSONArray();
        JSONObject hc = new JSONObject();
        hc.put("x", 0);
        hc.put("y", 0);
        hc.put("type", "hero_castle");
        en.put(hc);
        config.put("entities", en);

        JSONObject ppa = new JSONObject();
        ppa.put("type", "path_tile");
        ppa.put("x", 0);
        ppa.put("y", 0);
        JSONArray pathA = new JSONArray();
        for (String d: path) {
            pathA.put(d);
        }
        ppa.put("path", pathA);
        config.put("path", ppa);

        try {
            FileWriter writer = new FileWriter("worlds/" + LocalDateTime.now().toString() + ".json");
            writer.write(config.toString());
            writer.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    
}
