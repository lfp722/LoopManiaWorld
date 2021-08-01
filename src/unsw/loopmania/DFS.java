package unsw.loopmania;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;

public class DFS<N> implements Iterator<N> {
    private Stack<Iterator<N>> waitList = new Stack<>();
    private Set<N> visited = new HashSet<>();
    private Graph<N> g;
    private N curNode;
    private N start;

    private List<N> path = new ArrayList<>();

    public DFS(Graph<N> g, N start) {
        this.g = g;
        waitList.push(g.getAdjacentNodes(start).iterator());
        path.add(start);
        curNode = start;
        this.start = start;
    }

    @Override
    public boolean hasNext() {
        return curNode != null;
    }

    @Override
    public N next() {
        if (!hasNext()) {
            throw new NoSuchElementException("The graph has been already traversed!");
        }
        visited.add(curNode);
        N nextNode = null;

        boolean found = g.getAdjacentNodes(curNode).isEmpty();
        boolean isBottom = false;
        Iterator<N> candidates = waitList.peek();

        while (!found && !isBottom) {
            while (!candidates.hasNext()) {
                waitList.pop();
                if (waitList.isEmpty()) {
                    nextNode = null;
                    isBottom = true;
                    break;
                }
                candidates = waitList.peek();
            }
            if (!isBottom) {
                nextNode = candidates.next();
                if (!visited.contains(nextNode)) {
                    waitList.push(g.getAdjacentNodes(nextNode).iterator());
                    found = true;
                }
            }            
        }

        N temp = curNode;
        curNode = nextNode;
        return temp;

    }

    public List<N> findPath() {
        while (path.size() < 40 || !path.get(path.size()-1).equals(path.get(0))) {
            if (!hasNext()) {
                break;
            }
            visited.add(curNode);
            N nextNode = null;
    
            boolean found = g.getAdjacentNodes(curNode).isEmpty();
            boolean isBottom = false;
            Iterator<N> candidates = waitList.peek();
    
            while (!found && !isBottom) {
                while (!candidates.hasNext()) {
                    waitList.pop();
                    path.remove(path.size() - 1);

                    if (waitList.isEmpty()) {
                        nextNode = null;
                        isBottom = true;
                        break;
                    }
                    candidates = waitList.peek();

                }
                if (!isBottom) {
                    nextNode = candidates.next();
                    if (path.size() > 38) {
                        visited.remove(start);
                    }
                    if (!visited.contains(nextNode)) {
                        path.add(nextNode);
                        waitList.push(g.getAdjacentNodes(nextNode).iterator());
                        found = true;
                    }
                }            
            }
            curNode = nextNode;

        }
        return path;

    }
}

