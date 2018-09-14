package ConwayLife;

import ConwayLife.assets.Board;
import ConwayLife.assets.View;

import javax.swing.*;

import static ConwayLife.util.Constants.*;

/**
 * Created by: Elbert Cheng
 * Note: Board size can be changed in Constants.java BUT KEEP IT AT LEAST 57x57
 * or else an error of the preset points will occur. I have not written a method
 * to dynamically create the starting points.
 */

public class Main {
    private static View _view;
    private static Board _board;
    private static JButton _button;
    private static JButton _clear;
    private static JButton _setSize;

    private static boolean paused = true;

    private static Thread boardThread;

    public static void main(String[] args) {
        // Initialize board
        _board = Board.board(BOARD_SIZE, BOARD_SIZE);
        int[][] points = { {50, 50}, {51, 50}, {54, 50}, {55, 50}, {56, 50}, {51, 48}, {53, 49}};
        _board.setStartingPoints(points);
        // Create JFrame view with Swing Thread
        _view = createView("Conway's Game of Life", _board);
        // Get the Play/Pause Button
        _button = _view.getButton();
        _clear = _view.getClearButton();

        // Initialize Board Thread
        boardThread = boardThread(_view, _board);
        // Create board Thread
        _button.addActionListener(e -> {
            paused = !paused;
            try {
                if (!paused) {
                    _button.setText(PAUSE_BUTTON);
                    // Start Thread cycle
                    boardThread.start();
                } else if (boardThread != null && paused) {
                    _button.setText(PLAY_BUTTON);
                    // Stop Thread when run complete
                    boardThread.join();
                    // Reset the Thread
                    boardThread = boardThread(_view, _board);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        // Set clear button
        _clear.addActionListener(e -> {
            _board.resetBoard();
            _view.setGenerationCount(0);
            updateBoard(_board, _view);
            _board.setStartingPoints(points);
        });

    }

    private static Thread boardThread(View view, Board board) {
        // Return new Thread with the while loop
        Thread thread = new Thread(() -> {
            while (!paused) {
                try {
                    updateBoard(board, view);
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return thread;
    }

    private static void updateBoard(Board b, View v) {
        // Update board and repaint
        b.updateTick();
        v.updatePaint(b.getQueue());
    }

    private static View createView(String title, Board board) {
        // Initialize the Swing view
        View view = View.view(title);
        SwingUtilities.invokeLater(() -> {
            view.setBoard(board);
            view.addGrid();
            view.setVisible();
        });
        return view;
    }
}
