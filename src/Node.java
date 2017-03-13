import java.util.ArrayList;
import java.util.List;

/**
 * Created by Incoruptable on 3/13/2017.
 */
public class Node {
    public List<Node> children;
    private Node parent;
    private ChessState currentState;
    private int nodeValue;

    public Node(Node parent) {
        this.parent = parent;
        currentState = new ChessState();
        children = new ArrayList<>();
    }

    public ChessState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(ChessState currentState) {
        this.currentState = currentState;
    }
}
