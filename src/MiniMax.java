import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created by Incoruptable on 3/13/2017.
 */
public class MiniMax {

    Node rootOfTree;
    int depthOfTree;
    Random rand;

    public MiniMax(int depthPlayer1, int depthPlayer2) {

        System.out.print("Hello! Welcome to chess! Good luck! ;)\n");
        ChessState startingState = new ChessState();
        startingState.resetBoard();
        Node currentNode = new Node(null);
        currentNode.setCurrentState(startingState);
        Scanner scanner = new Scanner(System.in);
        currentNode.getCurrentState().printBoard(System.out);
        rand = new Random();
        while (true) {
            //try {
            if (depthPlayer1 == 0) {
                System.out.print("Your move?\n");
                String move = scanner.next();

                if (move.charAt(0) == 'q') {
                    return;
                }

                int xSource = (int) move.charAt(0) - 97;
                int ySource = Character.getNumericValue(move.charAt(1)) - 1;
                int xDest = (int) move.charAt(2) - 97;
                int yDest = Character.getNumericValue(move.charAt(3)) - 1;

                //System.out.print(xSource +"help"+ ySource +"help"+ xDest + "help" + yDest);
                if ((int) move.charAt(0) - 97 < 0 || (int) move.charAt(0) - 97 > 7 || Character.getNumericValue(move.charAt(1)) <= 0 || Character.getNumericValue(move.charAt(1)) >= 8 || (int) move.charAt(2) - 97 < 0 || (int) move.charAt(2) - 97 > 7 || Character.getNumericValue(move.charAt(3)) <= 0 || Character.getNumericValue(move.charAt(3)) >= 8) {
                    System.out.print("This input is not formatted correctly or inputs are out of bounds.\n");
                    continue;
                }

                if (!currentNode.getCurrentState().isValidMove(xSource, ySource, xDest, yDest)) {
                    System.out.print("This move is invalid. Please try again.");
                    continue;
                }
                try {
                    ChessState temp = currentNode.getCurrentState();
                    temp.move(xSource, ySource, xDest, yDest);
                    currentNode.setCurrentState(temp);
                    currentNode.getCurrentState().printBoard(System.out);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
                currentNode = generatePartialTree(depthPlayer1, currentNode, true);
                if (currentNode.children.size() <= 0) {
                    System.out.print("Dark Wins!\n");
                    return;
                }
                currentNode = findBestMove(currentNode, depthPlayer1, true);
                currentNode.getCurrentState().printBoard(System.out);
            }

            //Thread.sleep(1000);
            if (depthPlayer2 == 0) {
                System.out.print("Your move?\n");
                String move = scanner.next();

                if (move.charAt(0) == 'q') {
                    return;
                }

                int xSource = (int) move.charAt(0) - 97;
                int ySource = Character.getNumericValue(move.charAt(1)) - 1;
                int xDest = (int) move.charAt(2) - 97;
                int yDest = Character.getNumericValue(move.charAt(3)) - 1;

                System.out.print(xSource + ySource + xDest + yDest);
                if ((int) move.charAt(0) - 97 < 0 || (int) move.charAt(0) - 97 > 7 || Character.getNumericValue(move.charAt(1)) <= 0 || Character.getNumericValue(move.charAt(1)) >= 8 || (int) move.charAt(2) < 0 || (int) move.charAt(2) > 7 || Character.getNumericValue(move.charAt(3)) <= 0 || Character.getNumericValue(move.charAt(3)) >= 8) {
                    System.out.print("This input is not formatted correctly or inputs are out of bounds.\n");
                    continue;
                }


                if (!currentNode.getCurrentState().isValidMove(xSource, ySource, xDest, yDest)) {
                    System.out.print("This move is invalid. Please try again.");
                    continue;
                }
                try {
                    currentNode.getCurrentState().move(xSource, ySource, xDest, yDest);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
                currentNode = generatePartialTree(depthPlayer2, currentNode, false);
                if (currentNode.children.size() <= 0) {
                    System.out.print("Light Wins!\n");
                    return;
                }
                currentNode = findBestMove(currentNode, depthPlayer2, false);
                currentNode.getCurrentState().printBoard(System.out);
                //Thread.sleep(1000);
                //}//catch (InterruptedException ex){
                //    ex.printStackTrace();
                //}
            }
        }

    }

    public static void main(String args[]) {

        int depthPlayer1 = Integer.parseInt(args[0]);
        int depthPlayer2 = Integer.parseInt(args[1]);

        MiniMax game = new MiniMax(depthPlayer1, depthPlayer2);
    }

    public Node generatePartialTree(int depth, Node currentState, boolean white) {
        depthOfTree = depth;
        return generateChildren(currentState, 0, white, null);
    }

    public Node generateChildren(Node parent, int depth, boolean white, ChessState.ChessMove move) {
        Node currentNode;
        if (move == null) {
            currentNode = new Node();
            currentNode.setCurrentState(parent.getCurrentState());
            if (currentNode.getCurrentState() == null) {
                currentNode = new Node(null);
                currentNode.setCurrentState(new ChessState());
                currentNode.getCurrentState().resetBoard();
            }
        } else {
            currentNode = new Node(parent);
            currentNode.setCurrentState(parent.getCurrentState());
            try {
                currentNode.getCurrentState().move(move.xSource, move.ySource, move.xDest, move.yDest);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (depth >= depthOfTree) {
            return currentNode;
        } else {
            ChessState.ChessMoveIterator it = currentNode.getCurrentState().iterator(white);
            currentNode.children = new ArrayList<>();
            while (it.hasNext()) {
                currentNode.children.add(generateChildren(currentNode, depth + 1, switchPlayers(white), it.next()));
            }
        }

        return currentNode;
    }

    public Node findBestMove(Node currentNode, int depth, boolean white) {

        int bestValue = -20000000;
        if (!white) {
            bestValue = 20000000;
        }
        Node bestMove = currentNode;
        for (Node child : currentNode.children) {
            int currentValue = alphaBetaPruning(child, depth, -200000, 200000, !white);
            if (white) {
                if (currentValue > bestValue) {
                    bestMove = child;
                    bestValue = currentValue;
                }
            } else {
                if (currentValue < bestValue) {
                    bestMove = child;
                    bestValue = currentValue;
                }
            }
        }

        return bestMove;
    }

    public int alphaBetaPruning(Node node, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (node.children == null | depth == 0 || node.children.size() == 0) {
            return node.getCurrentState().heuristic(rand);
        }
        if (maximizingPlayer) {
            int bestValue = -2000000;
            for (Node child : node.children) {
                bestValue = max(bestValue, alphaBetaPruning(child, depth - 1, alpha, beta, false));
                alpha = max(alpha, bestValue);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestValue;
        } else {
            int bestValue = 20000000;
            for (Node child : node.children) {
                bestValue = min(bestValue, alphaBetaPruning(child, depth - 1, alpha, beta, true));
                beta = min(beta, bestValue);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestValue;
        }
    }


    public boolean switchPlayers(boolean white) {
        return !white;
    }
}
