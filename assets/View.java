package ConwayLife.assets;

import ConwayLife.util.RGB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import static ConwayLife.util.Constants.*;

public class View extends JFrame implements MouseListener, MouseMotionListener {
    private static View view_instance = null;
    // Initialize private variables
    private JPanel _pane;
    private JPanel _footer;
    private JButton _button;
    private JButton _clearButton;
    private JLabel _generationText;

    private ArrayList<ArrayList<Boolean>> _board;

    private int _generationCount;


    public View(String title) {
        super.setTitle(title);
        init();
    }

    // Singleton
    public static View view(String title) {
        if (view_instance == null) {
            view_instance = new View(title);
        }
        return view_instance;
    }

    private void init() {
        // Set frame size to maximum dimension
        this.setPreferredSize(SCREEN_SIZE);
        // Set frame layout
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addMouseListener(this);
        this.setResizable(false);
        // Initialize generation count
        _generationCount = 0;
        // Initialize board pane
        _pane = createBoardPane();
        _pane.addMouseMotionListener(this);
        _footer = new JPanel();
        _footer.setPreferredSize(new Dimension(HEIGHT, FOOTER_SIZE));
        addFooterElements();
        this.pack();
    }

    /** Public methods */
    public void addGrid() {
        this.add(this._pane, BorderLayout.CENTER);
        this.add(this._footer, BorderLayout.PAGE_END);
    }

    public void setVisible() {
        this.setVisible(true);
    }

    public void updatePaint(ArrayList<Order> q) {
        // Execute the queue
        for (int i = 0; i < q.size(); i++) {
            Order order = q.get(i);
            setAlive(order.getX(), order.getY(), order.isAlive());
        }
        _pane.repaint();
        _generationText.setText(createGenerationText());
    }

    /** Helper methods */
    private String createGenerationText() {

        String count = "Generation: " + _generationCount;
        _generationCount++;
        return count;
    }

    private void mousePoint(int x, int y) {
        int newX = x / TILE_SIZE.width;
        int newY = (y - TITLE_BAR_SIZE) / TILE_SIZE.height;

        boolean cell = this._board.get(newY).get(newX);

        if (!cell) {
            _board.get(newY).set(newX, true);
            _pane.repaint();
        } else if (cell) {
            _board.get(newY).set(newX, false);
            _pane.repaint();
        }
        _generationCount++;
    }

    private JPanel createBoardPane() {
        return new JPanel() {
            public void paintComponent(Graphics g) {
                for (int i = 0; i < _board.size(); i++) {
                    for (int j = 0; j < _board.get(i).size(); j++) {
                        boolean alive = _board.get(i).get(j);
                        int x = j * TILE_SIZE.width;
                        int y = i * TILE_SIZE.height;

                        if (alive) {
                            // Alive
                            g.setColor(RGB.chooseColor(COLOR_CHANGE_RATE));
                            g.fillRect(x, y, TILE_SIZE.width, TILE_SIZE.height);
                        } else {
                            // Dead
                            g.setColor(DEAD_COLOR);
                            g.fillRect(x, y, TILE_SIZE.width, TILE_SIZE.height);
                            g.setColor(GRID_LINES);
                            g.drawRect(x, y, TILE_SIZE.width, TILE_SIZE.height);
                        }
                    }
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return BOARD_DI;
            }
        };
    }

    private void addFooterElements() {
        _footer.add(this._button = new JButton(PLAY_BUTTON));
        _footer.add(this._generationText = new JLabel(createGenerationText()));
        _footer.add(this._clearButton = new JButton(CLEAR_BUTTON));
    }

    /** Getters and Setters */
    public void setBoard(Board b) {
        this._board = b.getBoard();
    }
    public JButton getButton() {
        return this._button;
    }
    public void setAlive(int x, int y, boolean alive) {
        _board.get(y).set(x, alive);
    }
    public JButton getClearButton() {
        return this._clearButton;
    }
    public void setGenerationCount(int count) {
        this._generationCount = count;
    }

    /** Mouse Listener methods */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    /** Mouse Motion Listener methods */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mousePoint(mouseEvent.getX(), mouseEvent.getY());
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
