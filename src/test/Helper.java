package test;

import unsw.loopmania.Enemy;
import unsw.loopmania.Equipped;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.PathTile;
import unsw.loopmania.Slug;
import unsw.loopmania.Vampire;
import unsw.loopmania.Zombie;
import unsw.loopmania.Building;
import unsw.loopmania.Character;
import unsw.loopmania.goal.Goal;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;


public class Helper {
    private static final int MAP1 = 1;
    private static final int MAP2 = 2;


    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int RIGHT = 3;
    private static final int LEFT = 4;


    public LoopManiaWorld createWorld(int mapNo) {
        int height = 0;
        int width = 0;
        switch(mapNo) {
            case MAP1:
                width = 8;
                height = 14;
                break;
            case MAP2:
                width = 3;
                height = 3; 
                break; 
        }
        int start_posX = 0;
        int start_posY = 0;
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        JSONObject path = createJSONMap(start_posX, start_posY, mapNo);
        orderedPath = loadPathTiles(path, width, height);
        // Create a path position where the character is at the first part of the path
        LoopManiaWorld world = new LoopManiaWorld(width, height, orderedPath);

        return world;
    }

    public Character testCharacterSetup(int currentPositionInPath, int mapNo) {
        int height = 0;
        int width = 0;
        switch(mapNo) {
            case MAP1:
                width = 8;
                height = 14;
                break;
            case MAP2:
                width = 3;
                height = 3; 
                break; 
        }
        int start_posX = 0;
        int start_posY = 0;
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        JSONObject path = createJSONMap(start_posX, start_posY, mapNo);
        orderedPath = loadPathTiles(path, width, height);
        PathPosition pathPosition = new PathPosition(currentPositionInPath, orderedPath);
        LoopManiaWorld world = new LoopManiaWorld(width, height, orderedPath);
        Character c = new Character(pathPosition, world);
        return c;
    }


    public Character createCharacterSetup(int currentPositionInPath, LoopManiaWorld world) {
        List<Pair<Integer, Integer>> orderedPath = world.getOrderedPath();
        PathPosition pathPosition = new PathPosition(currentPositionInPath, orderedPath);
        Character c = new Character(pathPosition, world);
        world.setCharacter(c);
        return c;
    }

    public Building createBuildingSetup(String buiding, int currentPositionInPath, LoopManiaWorld world, int direction) {
    
        List<Pair<Integer, Integer>> orderedPath = world.getOrderedPath();
        PathPosition pathposition = new PathPosition(currentPositionInPath, orderedPath);
        int x = pathposition.getX().getValue();
        int y = pathposition.getY().getValue();
        switch (direction) {
            case UP:
                y -= 1;
                break;
            case DOWN:
                y += 1;
                break;
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
                x += 1;
        }
        System.out.println(x);
        System.out.println(y);
        Pair<Integer, Integer> pair = new Pair<>(x, y);
        boolean buildingInPath = world.isInPath(pair);
        boolean buildingNearPath = world.ifNearPathTile(pair);
        System.out.println("Building is in path: " + buildingInPath);
        System.out.println("Building is near pathtile: " + buildingNearPath);
        SimpleIntegerProperty xCoord = new SimpleIntegerProperty(x);
        SimpleIntegerProperty yCoord = new SimpleIntegerProperty(y);
        Building building = new Building(xCoord, yCoord);
        world.addEntity(building);
        return building;
    }
    
    
    


    public Slug testSlugSetup(int currentPositionInPath, int mapNo) {
        int height = 0;
        int width = 0;
        switch(mapNo) {
            case MAP1:
                width = 8;
                height = 14;
                break;
            case MAP2:
                width = 3;
                height = 3; 
                break; 
        }
        int start_posX = 0;
        int start_posY = 0;
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        JSONObject path = createJSONMap(start_posX, start_posY, mapNo);
        orderedPath = loadPathTiles(path, width, height);
        PathPosition pathPosition = new PathPosition(currentPositionInPath, orderedPath);
        LoopManiaWorld world = new LoopManiaWorld(width, height, orderedPath);
        int cycle = world.getCycle().getValue();
        Slug slug = new Slug(pathPosition,cycle);
        return slug;
    }


    public Zombie testZombieSetup(int currentPositionInPath, int mapNo) {
        int height = 0;
        int width = 0;
        switch(mapNo) {
            case MAP1:
                width = 8;
                height = 14;
                break;
            case MAP2:
                width = 3;
                height = 3; 
                break; 
        }
        int start_posX = 0;
        int start_posY = 0;
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        JSONObject path = createJSONMap(start_posX, start_posY, mapNo);
        orderedPath = loadPathTiles(path, width, height);
        PathPosition pathPosition = new PathPosition(currentPositionInPath, orderedPath);
        LoopManiaWorld world = new LoopManiaWorld(width, height, orderedPath);
        int cycle = world.getCycle().getValue();
        Zombie zombie = new Zombie(pathPosition, cycle);
        return zombie;
    }

    public Vampire testVampireSetup(int currentPositionInPath, int mapNo) {
        int height = 0;
        int width = 0;
        switch(mapNo) {
            case MAP1:
                width = 8;
                height = 14;
                break;
            case MAP2:
                width = 3;
                height = 3; 
                break; 
        }
        int start_posX = 0;
        int start_posY = 0;
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        JSONObject path = createJSONMap(start_posX, start_posY, mapNo);
        orderedPath = loadPathTiles(path, width, height);
        PathPosition pathPosition = new PathPosition(currentPositionInPath, orderedPath);
        LoopManiaWorld world = new LoopManiaWorld(width, height, orderedPath);
        int cycle = world.getCycle().getValue();
        Vampire vampire = new Vampire(pathPosition, cycle);
        return vampire;
    }

    public JSONObject createJSONMap(int start_posX, int start_posY, int pathNo) {
        JSONObject world = new JSONObject();
        world.put("type", "path_tile");
        world.put("x", 0);
        world.put("y", 0);
        JSONArray jsonPath = new JSONArray();
        switch(pathNo) {
            case 1:
                jsonPath = Path1();
                break;
            case 2:
                jsonPath = Path2();
                break;
        }
        world.put("path", jsonPath);
        String message = world.toString();
        System.out.println(message);
        return world;
    }

    
    public List<Pair<Integer, Integer>> loadPathTiles(JSONObject path, int width, int height) {
        if (!path.getString("type").equals("path_tile")) {
            // ... possible extension
            throw new RuntimeException(
                    "Path object requires path_tile type.");
        }
        PathTile starting = new PathTile(new SimpleIntegerProperty(path.getInt("x")), new SimpleIntegerProperty(path.getInt("y")));
        if (starting.getY() >= height || starting.getY() < 0 || starting.getX() >= width || starting.getX() < 0) {
            throw new IllegalArgumentException("Starting point of path is out of bounds");
        }
        // load connected path tiles
        List<PathTile.Direction> connections = new ArrayList<>();
        for (Object dir: path.getJSONArray("path").toList()){
            connections.add(Enum.valueOf(PathTile.Direction.class, dir.toString()));
        }

        if (connections.size() == 0) {
            throw new IllegalArgumentException(
                "This path needs to consist of multiple to form a loop.");
        }

        PathTile.Direction first = connections.get(0);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(Pair.with(starting.getX(), starting.getY()));

        int x = starting.getX() + first.getXOffset();
        int y = starting.getY() + first.getYOffset();

        for (int i = 1; i < connections.size(); i++) {
            orderedPath.add(Pair.with(x, y));
            
            if (y >= height || y < 0 || x >= width || x < 0) {
                throw new IllegalArgumentException("Path goes out of bounds at direction index " + (i - 1) + " (" + connections.get(i - 1) + ")");
            }
            
            PathTile.Direction dir = connections.get(i);
            x += dir.getXOffset();
            y += dir.getYOffset();
            if (orderedPath.contains(Pair.with(x, y)) && !(x == starting.getX() && y == starting.getY())) {
                throw new IllegalArgumentException("Path crosses itself at direction index " + i + " (" + dir + ")");
            }
        }
        if (x != starting.getX() || y != starting.getY()) {
            throw new IllegalArgumentException(String.format(
                    "Path must loop back around on itself, this path doesn't finish where it began, it finishes at %d, %d.",
                    x, y));
        }
        return orderedPath;
    }

    public JSONArray Path1() {
        String[] path = {"RIGHT", "RIGHT", "DOWN", "RIGHT", "RIGHT", "UP", "RIGHT", "RIGHT", "RIGHT",
        "DOWN", "DOWN", "DOWN", "DOWN", "DOWN", "DOWN", "DOWN", "DOWN", "DOWN",
        "LEFT", "LEFT", "LEFT", "UP", "UP", "RIGHT",
        "UP", "UP", "UP", "UP", "LEFT", "LEFT", "LEFT",
        "DOWN", "DOWN", "DOWN", "DOWN", "DOWN", "DOWN", "DOWN", "DOWN",
        "RIGHT", "RIGHT", "RIGHT", "RIGHT", "RIGHT", "DOWN", "DOWN",
        "LEFT", "LEFT", "LEFT", "LEFT", "LEFT", "LEFT", "LEFT",
        "UP", "UP", "UP", "UP", "UP", "UP", "UP", "UP", "UP", "UP", "UP", "UP", "UP"};
        JSONArray jsonPath = new JSONArray();
        for (String direction: path) {
            jsonPath.put(direction);
        }
        return jsonPath;
    }

    public JSONArray Path2() {
        String[] path = {"RIGHT", "RIGHT", "DOWN", "DOWN", "LEFT", "LEFT", "UP", "UP"};
        JSONArray jsonPath = new JSONArray();
        for (String direction: path) {
            jsonPath.put(direction);
        }
        return jsonPath;
    }



}