package men.brakh.oop1.model.figure.impl;

import men.brakh.oop1.model.Point;
import men.brakh.oop1.model.canvas.AbstractCanvas;
import men.brakh.oop1.model.figure.RectFigure;

public class Rectangle extends RectFigure {
    /**
     * Конструктор фигуры, которую можно вписать в прямоугольник
     *
     * @param canvas     Объект канваса
     * @param startPoint Начальные координаты
     */
    public Rectangle(AbstractCanvas canvas, Point startPoint) {
        super(canvas, startPoint);
    }

    @Override
    public void draw() {
        canvas.drawRectangle(getLeftTopPoint(), getRightBottomPoint());
    }
}
