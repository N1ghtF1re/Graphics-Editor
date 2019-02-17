package men.brakh.graphicseditor.model.figure;

import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.PointType;

/**
 * Интерфейс фигуры
 */
public interface Figure {

    PointType checkPoint(Point point);
    void resize(PointType pointType, Point toPoint);
    void move(Point deltaPoint);
    void select();
    void draw();

    String getPenColor();
    String getBushColor();
    int getPenWidth();

    void setPenColor(String color);
    void setBrushColor(String color);
    void setPenWidth(int width);

}
