package ConwayLife.assets;

import java.util.ArrayList;
import java.util.Random;

import static ConwayLife.util.Constants.*;

public class Board {
    private static Board board_instance = null;
    private static int[][] boundaries = {{-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {1, 0}, {-1, 1}, {0, 1}, {1, 1}};
    private ArrayList<ArrayList<Boolean>> _board;
    private ArrayList<Order> _queue;
    private int _row;
    private int _col;

    public Board(int row, int col) {
        this._board = createGrid(row, col);
        this._queue = new ArrayList<>();
        this._row = row;
        this._col = col;
    }

    // Static method to create singleton instance
    public static Board board(int row, int col) {
        if (board_instance == null) {
            board_instance = new Board(row, col);
        }
        return board_instance;
    }

    /** Public Methods */
    public void resetBoard() {
        for(int i = 0; i < _board.size(); i++) {
            for (int j = 0; j < _board.get(i).size(); j++) {
                _board.get(j).set(i, false);
                if (!_queue.isEmpty()) {
                    _queue.clear();
                }
            }
        }
    }

    /** Update Actions */
    public void updateTick() {
        updateBoard();
    }

    public void updateBoard() {
        // Reset the queue
        if (!(_queue.isEmpty())) {
            _queue.clear();
        }

        // Set the queue
        for (int y = 0; y < _board.size(); y++) {
            for (int x = 0; x < _board.get(y).size(); x++) {
                Order order = checkRules(x, y, getAlive(x, y));
                if (order != null) {
                    _queue.add(order);
                }
            }
        }

        // Execute the queue
        for (int i = 0; i < _queue.size(); i++) {
            Order order = _queue.get(i);
            setAlive(order.getX(), order.getY(), order.isAlive());
        }
    }

    /** Check Actions */
    public Order checkRules(int x, int y, boolean c) {
        int neighbors = 0;

        for (int i = 0; i < boundaries.length; i++) {
            int xBound = checkPoints(x + boundaries[i][0]);
            int yBound = checkPoints(y + boundaries[i][1]);

            // Iterate through the boundaries array and check if there are alive neighbors
            if (xBound > -1 && yBound > -1  && xBound < _col && yBound < _row) {
                boolean neighborState = getAlive(xBound, yBound);
                if (neighborState) {
                    neighbors++;
                }
            }
        }
        if (!c) {
            //  Any DEAD cell with exactly three LIVE neighbors becomes a LIVE cell.
            if (neighbors == LIVE) {
                return new Order(x, y, true);
            }
        } else if (c){
            // Any LIVE cell with fewer than two live neighbors DIES, as if caused by underpopulation.
            if (neighbors < UNDERPOPULATED) {
                return new Order(x, y, false);
            }
            // Any LIVE cell with more than three live neighbors DIES, as if by overcrowding.
            else if (neighbors > LIVE) {
                return new Order(x, y, false);
            }
            else {
                // Any LIVE cell with two or three live neighbors LIVES on to the next generation.
                // Any DEAD cells which doesn't meet requirements stay DEAD.
            }
        }
        return null;
    }

    /** Initialization methods */
    private ArrayList<ArrayList<Boolean>> createGrid(int row, int col) {
        // With the parameter dimensions, create a board with all DEAD cells
        ArrayList<ArrayList<Boolean>> temp = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            temp.add(new ArrayList<>());
            for (int j = 0; j < col; j++) {
                temp.get(i).add(false);
            }
        }
        return temp;
    }

    public void setStartingPoints(int[][] points) {
        for (int i = 0; i < points.length; i++) {
            setAlive(points[i][0], points[i][1], true);
        }
    }

    /** Helper Methods */
    private int checkPoints(int value) {
        if (value < 0) {
            return value + BOARD_SIZE;
        } else if (value >= BOARD_SIZE) {
            return value - BOARD_SIZE;
        }
        return value;
    }

    public String toString() {
        String temp = "";
        for (int i = 0; i < _board.size(); i++) {
            for (int j = 0; j < _board.get(i).size(); j++) {
                boolean b = getAlive(j, i);

                if (b) {
                    temp += "+ ";
                } else {
                    temp += "_ ";
                }
            }
            temp += "\n";
        }
        return temp;
    }

    /** Getters and Setters */
    public ArrayList<ArrayList<Boolean>> getBoard() {
        return this._board;
    }
    public void setAlive(int x, int y, boolean alive) {
        _board.get(y).set(x, alive);
    }
    public boolean getAlive(int x, int y) {
        return _board.get(y).get(x);
    }
    public ArrayList<Order> getQueue() {
        return this._queue;
    }
}
