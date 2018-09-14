package ConwayLife.util;

import java.awt.*;

import static ConwayLife.util.Constants.COLOR_CHANGE_RATE;
import static ConwayLife.util.RGB.ColorState.*;

public class RGB {
    private final static int MAX_VALUE = Integer.parseInt("F4", 16);
    private final static int MIN_VALUE = Integer.parseInt("42", 16);

    private static double red = MAX_VALUE;
    private static double green = MIN_VALUE;
    private static double blue = MIN_VALUE;

    public static ColorState state = GREEN_UP;

    public static Color chooseColor(double rate) {
        updateVar();
        return new Color((int) red,(int) green,(int) blue);
    }

    // @Todo: Make it rainbow

    /** Helper methods */
    private static void updateVar() {
        switch (state) {
            case GREEN_UP:
                if (castInt(red) == MAX_VALUE && castInt(green) == MAX_VALUE && castInt(blue) == MIN_VALUE) {
                    state = RED_DOWN;
                    break;
                }
                green += COLOR_CHANGE_RATE;
                break;
            case RED_DOWN:
                if (castInt(red) == MIN_VALUE && castInt(green) == MAX_VALUE && castInt(blue) >= MIN_VALUE) {
                    state = BLUE_UP;
                    break;
                }
                red -= COLOR_CHANGE_RATE;
                break;
            case BLUE_UP:
                if (castInt(red) == MIN_VALUE && castInt(green) == MAX_VALUE && castInt(blue) == MAX_VALUE) {
                    state = GREEN_DOWN;
                    break;
                }
                blue += COLOR_CHANGE_RATE;
                break;
            case GREEN_DOWN:
                if (castInt(red) == MIN_VALUE && castInt(green) == MIN_VALUE && castInt(blue) == MAX_VALUE) {
                    state = RED_UP;
                    break;
                }
                green -= COLOR_CHANGE_RATE;
                break;
            case RED_UP:
                if (castInt(red) == MAX_VALUE && castInt(green) == MIN_VALUE && castInt(blue) == MAX_VALUE) {
                    state = BLUE_DOWN;
                    break;
                }
                red += COLOR_CHANGE_RATE;
                break;
            case BLUE_DOWN:
                if (castInt(red) == MAX_VALUE && castInt(green) == MIN_VALUE && castInt(blue) == MIN_VALUE) {
                    state = GREEN_UP;
                    break;
                }
                blue -= COLOR_CHANGE_RATE;
                break;
        }
    }

    private static int castInt(double value) {
        return (int) value;
    }

    public static String printColors() {
        return "R: " + red + " G: " + green + " B: " + blue;
    }

    private static int verifyValue(int value) {
        if (value > MAX_VALUE) {
            return MIN_VALUE;
        }
        return value;
    }

    enum ColorState {
        GREEN_UP,
        RED_DOWN,
        BLUE_UP,
        GREEN_DOWN,
        RED_UP,
        BLUE_DOWN
    }
}
