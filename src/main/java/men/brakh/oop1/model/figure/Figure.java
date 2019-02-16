package men.brakh.oop1.model.figure;

import men.brakh.oop1.model.PointType;

/**
 * Интерфейс фигуры
 */
public interface Figure {
    void resize(PointType pointType, double deltaX, double deltaY);
    void move(double deltaX, double deltaY);
    void select();
    void draw();
}
