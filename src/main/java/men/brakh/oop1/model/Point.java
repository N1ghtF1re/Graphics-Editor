package men.brakh.oop1.model;

import men.brakh.oop1.config.GraphicEditorConfig;


public class Point {
    /* КОНФИГ */
    private GraphicEditorConfig config = GraphicEditorConfig.getInstance();

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;
        return xEquals(point.x) &&
                yEquals(point.y);
    }

    /**
     * Возвращает true если x объекта равен (с учетом погрешности {@link GraphicEditorConfig#pointAreaSize}) передаваемому значению
     * @param x Х, с которым необходимо сравнить
     * @return true if this.x ~ x
     */
    public boolean xEquals(double x) {
        return Math.abs(this.x - x) <= config.getPointAreaSize();
    }

    /**
     * Возвращает true если y объекта равен (с учетом погрешности {@link GraphicEditorConfig#pointAreaSize}) передаваемому значению
     * @param y Y, с которым необходимо сравнить
     * @return true if this.y ~ y
     */
    public boolean yEquals(double y) {
        return Math.abs(this.y - y) <= config.getPointAreaSize();
    }

    /**
     * Возвращает true если y находятся в диапазоне от y1 до y2
     * @param y1 нижняя граница диапазона
     * @param y2 верхняя граница диапазона
     * @return true if y1 < y < y2
     */
    public boolean yInRange(double y1, double y2) {
        return (y > y1 - config.getPointAreaSize()) && (y < y2 + config.getPointAreaSize());
    }

    /**
     * Возвращает true если x находятся в диапазоне от x1 до x2
     * @param x1 нижняя граница диапазона
     * @param x2 верхняя граница диапазона
     * @return true if x1 < x < x2
     */
    public boolean xInRange(double x1, double x2) {
        return (x > x1 - config.getPointAreaSize()) && (x < x2 + config.getPointAreaSize());
    }


}
