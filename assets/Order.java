package ConwayLife.assets;

public class Order {
    private int _x;
    private int _y;
    private boolean _isAlive;

    public Order(int x, int y, boolean alive) {
        this._x = x;
        this._y = y;
        this._isAlive = alive;
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }
    /** Getters and Setters */
    public boolean isAlive() {
        return _isAlive;
    }
}
