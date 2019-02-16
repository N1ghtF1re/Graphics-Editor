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


}
