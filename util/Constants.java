package ConwayLife.util;

import java.awt.*;

public class Constants {
    // Target FPS
    public static final int HERTZ = 60;
    public static final int TARGET_FPS = 60;

    // Rules
    public static final int UNDERPOPULATED = 2;
    public static final int LIVE = 3;

    // Dimensions
    public static final int BOARD_SIZE = 200;
    public static final int TITLE_BAR_SIZE = 30;
    public static final int FOOTER_SIZE = 50;

    // Calculated Dimensions
    public static final int HEIGHT = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    // Note: +8 to account for the window border and awkward casting to adjust the size so all of the cells can fit.
    public static final Dimension SCREEN_SIZE = new Dimension(((int)((double) HEIGHT / BOARD_SIZE)) * BOARD_SIZE + 8, HEIGHT + 8);
    public static final Dimension BOARD_DI = new Dimension(SCREEN_SIZE.width, SCREEN_SIZE.height - TITLE_BAR_SIZE - FOOTER_SIZE);
    public static final Dimension TILE_SIZE = new Dimension(BOARD_DI.width / BOARD_SIZE, BOARD_DI.height / BOARD_SIZE);
//    public static final Dimension TILE_SIZE = new Dimension(1, 1);

    // Element text
    public static final String PLAY_BUTTON = "Play";
    public static final String PAUSE_BUTTON = "Pause";
    public static final String SIZE_BUTTON = "Set Size";
    public static final String CLEAR_BUTTON = "Reset";

    // Colors
    public static final double COLOR_CHANGE_RATE = 0.1;

    public static final Color LIVE_COLOR = new Color(16777215);
    public static final Color DEAD_COLOR = Color.BLACK;
    public static final Color GRID_LINES = Color.DARK_GRAY;
}
