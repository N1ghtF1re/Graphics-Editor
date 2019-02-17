package men.brakh.oop1.model.figure;

import men.brakh.oop1.model.Point;
import men.brakh.oop1.model.PointType;

/**
 * Интерфейс фигуры
 */
public interface Figure {

    PointType checkPoint(Point point);
    void resize(PointType pointType, Point deltaPoint);
    void move(int deltaX, int deltaY);
    void select();
    void draw();
}
