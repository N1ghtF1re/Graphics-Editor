package men.brakh.graphicseditor.model.figure;

import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.PointType;

/**
 * Интерфейс фигуры
 */
public interface Figure {
    PointType checkPoint(Point point);
    void select();
    void draw();
    void moveStartPoint(PointType pointType, Point fromPoint, Point toPoint);

    boolean isInside(Figure figure);

    String getPenColor();
    String getBushColor();
    int getPenWidth();

    void setPenColor(String color);
    void setBrushColor(String color);
    void setPenWidth(int width);

    void assign(Figure figure);
    Figure copy();
}
