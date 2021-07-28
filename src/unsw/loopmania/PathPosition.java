package unsw.loopmania;

import java.util.List;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.PathTile.Direction;

/**
 * objects of this class represents the position in the path.
 * it holds the current position in the path and a reference to the orderedPath internally.
 * It also holds SimpleIntegerProperties for x and y coordinates, which we have getter methods for,
 *     so we can return them and attach ChangeListers, to decouple the frontend and backend.
 * The SimpleIntegerProperties are updated automatically when we move through the path.
 */
public class PathPosition{

    private int currentPositionInPath;
    private List<Pair<Integer, Integer>> orderedPath;
    private SimpleIntegerProperty x;
    private SimpleIntegerProperty y;

    /*
     * the integerproperty internal int is the x or y coordinate - since want to use this class to bind with node positions
     * currentPositionInPath is an index of orderedPath
     * usingValue0 is true if using value0 in path, false if using value1
     * orderedPath is the list of path coordinates in pairs
     */
    public PathPosition(int currentPositionInPath, List<Pair<Integer, Integer>> orderedPath){
        this.currentPositionInPath = currentPositionInPath;
        this.orderedPath = orderedPath;
        x = new SimpleIntegerProperty();
        y = new SimpleIntegerProperty();
        // update internal property
        resetCoordinatesBasedOnPositionInPath();
    }

    public int getCurrentPositionInPath() {
        return currentPositionInPath;
    }



    public void setCurrentPositionInPath(int currentPositionInPath) {
        this.currentPositionInPath = currentPositionInPath;
    }



    public List<Pair<Integer, Integer>> getOrderedPath() {
        return orderedPath;
    }



    public void setOrderedPath(List<Pair<Integer, Integer>> orderedPath) {
        this.orderedPath = orderedPath;
    }

    /**
     * move forward through the path i.e. clockwise
     */
    public void moveDownPath(){
        currentPositionInPath = (currentPositionInPath + 1)%orderedPath.size();
        resetCoordinatesBasedOnPositionInPath();
    }

    /**
     * move backwards through the path, i.e. anticlockwise
     */
    public void moveUpPath(){
        currentPositionInPath = (currentPositionInPath - 1 + orderedPath.size())%orderedPath.size();
        resetCoordinatesBasedOnPositionInPath();
    }

    /**
     * change the x and y SimpleIntegerProperties to reflect the current values of
     * the current position in the path, and the ordered path.
     */
    private void resetCoordinatesBasedOnPositionInPath(){
        x.set(orderedPath.get(currentPositionInPath).getValue0());
        y.set(orderedPath.get(currentPositionInPath).getValue1());
    }

    public SimpleIntegerProperty getX(){
        return x;
    }

    public SimpleIntegerProperty getY(){
        return y;
    }

    /**
     * convert a complete path into JSONObject
     * @return
     */
    public JSONObject toJSON() {
        JSONObject path = new JSONObject();
        JSONArray pathTiles = new JSONArray();
        Pair<Integer, Integer> pt = new Pair<Integer,Integer>(0, 0);
        for (int i = 0; i < orderedPath.size() - 1; i++) {
            pt = orderedPath.get(i);
            Pair<Integer, Integer> nextPt = orderedPath.get(i + 1);
            Direction d = PathTile.Direction.getDirectionFromOffset(pt.getValue0(), pt.getValue1(), nextPt.getValue0(), nextPt.getValue1());
            pathTiles.put(d);
        }
        path.put("path", pathTiles);
        path.put("type", "path_tile");
        path.put("x", 0);
        path.put("y", 0);
        return path;
    }
}
