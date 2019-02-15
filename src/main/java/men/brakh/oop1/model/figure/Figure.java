package men.brakh.oop1.model.figure;

import men.brakh.oop1.model.ResizeType;

/**
 * Интерфейс фигуры
 */
public interface Figure {
    void move(double deltaX, double deltaY);
    void select();
    void draw();
}
