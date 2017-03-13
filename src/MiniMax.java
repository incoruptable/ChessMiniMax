/**
 * Created by Incoruptable on 3/13/2017.
 */
public class MiniMax {

    Node rootOfTree;
    int depthOfTree;

    public MiniMax(int depthPlayer1, int depthPlayer2) {

        System.out.print("Hello! Welcome to chess! Good luck! ;)\n");
        rootOfTree = generatePartialTree(depthPlayer1);
        while (true) {


        }


    }

    public static void main(String args[]) {

        int depthPlayer1 = Integer.parseInt(args[0]);
        int depthPlayer2 = Integer.parseInt(args[1]);

        MiniMax game = new MiniMax(depthPlayer1, depthPlayer2);
    }

    public Node generatePartialTree(int depth, Node currentState) {
        depthOfTree = depth;
        rootOfTree = generateChildren(currentState);
    }

    public Node generateChildren(Node currentState) {
        Node currentNode = new Node(null);
        currentNode.setCurrentState(currentState.getCurrentState());

        for (int i = 0; i < depthOfTree; i++) {

        }

    }

    public Node findBestMove(Node currentState) {
        int bestValue = -20000000;
        Node bestMove = currentState;
        for (Node child : currentState.children) {
            int currentValue = alphaBetaPruning(currentState, 0, -200000, 200000, true);
            if (currentValue > bestValue) {
                bestMove = child;
                bestValue = currentValue;
            }
        }

        return bestMove;
    }

    public int alphaBetaPruning(Node node, int depth, int alpha, int beta, boolean maximizingPlayer) {


        return 1;
    }

}
